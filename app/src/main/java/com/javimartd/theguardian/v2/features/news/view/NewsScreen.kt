package com.javimartd.theguardian.v2.features.news.view

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.javimartd.theguardian.v2.BuildConfig
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.data.repository.news.model.SectionData
import com.javimartd.theguardian.v2.features.news.NewsUiContract
import com.javimartd.theguardian.v2.features.news.components.NewsItemLandscape
import com.javimartd.theguardian.v2.features.news.components.NewsItemPortrait
import com.javimartd.theguardian.v2.features.news.components.Tags
import com.javimartd.theguardian.v2.features.news.model.NewsItemUiState
import com.javimartd.theguardian.v2.features.news.model.NewsViewModel
import com.javimartd.theguardian.v2.ui.components.LoadingDialog
import com.javimartd.theguardian.v2.ui.components.SettingsButton
import com.javimartd.theguardian.v2.ui.components.TheGuardianSnackBarHost
import com.javimartd.theguardian.v2.ui.navigation.TheGuardianDestinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    navController: NavHostController,
    viewModel: NewsViewModel
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState = viewModel.uiState

    Scaffold(
        snackbarHost = { TheGuardianSnackBarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .background(color = Color.Blue)
                    .testTag(Tags.TAG_NEWS_SCREEN_TOOLBAR),
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.app_name),
                        textAlign = TextAlign.Center
                    )
                },
                actions = {
                    SettingsButton { navController.navigate(TheGuardianDestinations.SETTINGS_ROUTE) }
                }
            )
        },
    ) { contentPadding ->
        Column(Modifier.padding(contentPadding)) {
            SearchScreen(viewModel = viewModel)
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.medium_margin))
            )
            Toast.makeText(LocalContext.current, BuildConfig.THE_GUARDIAN_API_KEY, Toast.LENGTH_LONG).show()
            when (uiState) {
                is NewsUiContract.State.Loading -> LoadingDialog { /* nothing to do */ }
                is NewsUiContract.State.News -> {
                    NewsListScreen(uiState.news) {
                        viewModel.onIntent(NewsUiContract.Intent.OnRefresh)
                    }
                }
                is NewsUiContract.State.Error -> {
                    ErrorMessageContent(uiState.errorMessage, snackBarHostState)
                }
                is NewsUiContract.State.NoNews -> NoNewsContent()
            }
        }
    }
}

@Composable
fun NewsListScreen(
    news: List<NewsItemUiState>,
    onRefresh: () -> Unit
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = onRefresh,
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                scale = true,
                contentColor = Color.Black
            )
        }
    ) {
        NewsContent(news = news)
    }
}

@Composable
fun SearchScreen(viewModel: NewsViewModel) {
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    SearchContent(
        searchQuery = viewModel.searchQuery,
        searchResults = searchResults,
        onSearchQueryChange = { viewModel.onIntent(NewsUiContract.Intent.SearchQueryChange(it)) },
        onSearchQueryClick = { viewModel.onIntent(NewsUiContract.Intent.SearchQueryClick(it)) }
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(
    searchQuery: String,
    searchResults: List<SectionData>,
    onSearchQueryChange: (String) -> Unit,
    onSearchQueryClick: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var active by remember { mutableStateOf(false) }

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = searchQuery,
        onQueryChange = { onSearchQueryChange(it) },
        placeholder = { Text(text = "Search for any section") },
        onSearch = {
            keyboardController?.hide()
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
            if (active) {
                onSearchQueryChange("")
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search_icon_content_description)
            )
        },
        tonalElevation = 0.dp,
        trailingIcon = {
            if (active) {
                IconButton(
                    onClick = {
                        if (searchQuery.isNotEmpty()) {
                            onSearchQueryChange("")
                        } else {
                            active = false
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close_icon_content_description)
                    )
                }
            }
        },
        content = {
            if (searchResults.isEmpty()) {
                SearchEmptyContent()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.large_margin)),
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.medium_margin)),
                    modifier = Modifier
                ) {
                    items(
                        count = searchResults.size,
                        key = { index -> searchResults[index].id },
                        itemContent = { index ->
                            val item = searchResults[index]
                            ClickableText(
                                text = AnnotatedString(item.webTitle),
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    onSearchQueryChange(item.webTitle)
                                    active = false
                                    onSearchQueryClick(item.id)
                                }
                            )
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun SearchEmptyContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No content found",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Try adjusting your search",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun NewsContent(news: List<NewsItemUiState>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.medium_margin)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_margin)),
    ) {
        items(
            items = news,
            key = { news -> news.id }
        ) {
            when (LocalConfiguration.current.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> NewsItemPortrait(uiState = it)
                else -> NewsItemLandscape(uiState = it)
            }
        }
    }
}

@Composable
fun NoNewsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.medium_margin)),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.testTag(Tags.TAG_NEWS_SCREEN_NO_NEWS_MESSAGE),
            text = stringResource(id = R.string.news_screen_no_news_message),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ErrorMessageContent(
    message: Int,
    snackBarHostState: SnackbarHostState
) {
    val errorMessage = stringResource(id = message)
    val actionLabel = stringResource(id = R.string.snackBar_button)
    LaunchedEffect(message) {
        val result = snackBarHostState.showSnackbar(
            message = errorMessage,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Short
        )
        if (result == SnackbarResult.ActionPerformed) {
            snackBarHostState.currentSnackbarData?.dismiss()
        }
    }
}
