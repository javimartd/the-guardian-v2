package com.javimartd.theguardian.v2.features.news.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.features.news.NewsUiContract
import com.javimartd.theguardian.v2.features.news.components.NewsItemLandscape
import com.javimartd.theguardian.v2.features.news.components.NewsItemPortrait
import com.javimartd.theguardian.v2.features.news.components.Tags
import com.javimartd.theguardian.v2.features.news.model.NewsItemUiState
import com.javimartd.theguardian.v2.features.news.model.NewsViewModel
import com.javimartd.theguardian.v2.ui.components.LoadingDialog
import com.javimartd.theguardian.v2.ui.components.TheGuardianSnackbarHost
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
        snackbarHost = { TheGuardianSnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .background(color = Color.Blue)
                    .testTag(Tags.TAG_NEWS_SCREEN_TOOLBAR),
                title = {
                    Text(stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(
                        modifier = Modifier.testTag(Tags.TAG_NEWS_SCREEN_SETTINGS),
                        onClick = { navController.navigate(TheGuardianDestinations.SETTINGS_ROUTE) }
                    ) {
                        Icon(
                            contentDescription = stringResource(id = R.string.news_screen_settings_icon_content_description),
                            imageVector = Icons.Default.Settings,
                            tint = colorResource(id = R.color.black)
                        )
                    }
                }
            )
        },
    ) { contentPadding ->
        Column {
            DropdownContent(
                defaultValue = uiState.sectionSelected,
                sections = uiState.sections
            ) { sectionName ->
                viewModel.onEvent(NewsUiContract.NewsUiEvent.GetNews(sectionName = sectionName))
            }
            NewsContent(
                modifier = Modifier.padding(contentPadding),
                news = uiState.news
            )
        }
        ErrorMessage(uiState, snackBarHostState)
        if (uiState.isRefreshing) {
            LoadingDialog { /* nothing to do */ }
        }
        if (uiState.news.isEmpty() && !uiState.isRefreshing) {
            NoNewsMessage()
        }
    }
}

@Composable
fun DropdownContent(
    defaultValue: String,
    sections: List<String>,
    onSectionSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Hi") }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(
        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.medium_margin))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                color = Color.Black,
                maxLines = 1,
                text = selectedText,
            )
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    contentDescription = stringResource(id = R.string.sections_dropdown_icon_content_description),
                    imageVector = icon,
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            modifier = Modifier
                .background(color = Color.Red)
                .height(dimensionResource(id = R.dimen.max_sections_dropdown_height))
                .fillMaxWidth(),
            onDismissRequest = { expanded = false }
        ) {
            sections.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {
                        selectedText = it
                        expanded = false
                        onSectionSelected(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun NewsContent(
    modifier: Modifier,
    news: List<NewsItemUiState>
) {

    val configuration = LocalConfiguration.current

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.medium_margin)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_margin)),
    ) {
        items(
            items = news,
            key = { news -> news.id }
        ) {
            when (configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> NewsItemPortrait(uiState = it)
                else -> NewsItemLandscape(uiState = it)
            }
        }
    }
}

@Composable
fun NoNewsMessage() {
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
fun ErrorMessage(
    uiState: NewsUiContract.NewsUiState,
    snackBarHostState: SnackbarHostState
) {
    uiState.errorMessage?.let {
        val errorMessage = stringResource(id = it)
        val actionLabel = stringResource(id = R.string.snackbar_button)
        LaunchedEffect(uiState.errorMessage) {
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
}