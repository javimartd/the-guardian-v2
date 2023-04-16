package com.javimartd.theguardian.v2.features.news.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.features.news.NewsUiContract
import com.javimartd.theguardian.v2.ui.components.TheGuardianSnackbarHost
import com.javimartd.theguardian.v2.ui.navigation.TheGuardianDestinations
import com.javimartd.theguardian.v2.features.news.components.NewsItemLandscape
import com.javimartd.theguardian.v2.features.news.components.NewsItemPortrait
import com.javimartd.theguardian.v2.features.news.model.NewsItemUiState
import com.javimartd.theguardian.v2.features.news.model.NewsUiState
import com.javimartd.theguardian.v2.features.news.model.NewsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun NewsScreen(
    navController: NavHostController,
    viewModel: NewsViewModel
) {

    val uiState = viewModel.uiState

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(Unit) {
        viewModel.effect
            .onEach {
                when (it) {
                    NewsUiContract.NewsUiEffect.NavigateToSettings -> {
                        navController.navigate(TheGuardianDestinations.SETTINGS_ROUTE)
                    }
                }
            }
            .collect()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { TheGuardianSnackbarHost(hostState = it) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    IconButton(
                        onClick = { viewModel.onEvent(NewsUiContract.NewsUiEvent.NavigateToSettings) }
                    ) {
                        Icon(
                            contentDescription = stringResource(id = R.string.news_screen_settings_icon_content_description),
                            imageVector = Icons.Default.Settings,
                            tint = colorResource(id = R.color.white)
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
        ErrorContent(uiState, scaffoldState)
    }
}

@Composable
fun DropdownContent(
    defaultValue: String,
    sections: List<String>,
    onSectionSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(defaultValue) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.medium_margin))) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
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
                .height(dimensionResource(id = R.dimen.max_sections_dropdown_height))
                .fillMaxWidth(),
            onDismissRequest = { expanded = false }
        ) {
            sections.forEach {
                DropdownMenuItem(
                    onClick = {
                        selectedText = it
                        expanded = false
                        onSectionSelected(it)
                    }
                ) {
                    Text(text = it)
                }
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
        modifier = modifier
            .background(color = colorResource(id = R.color.gray_200))
            .fillMaxSize(),
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
private fun ErrorContent(
    uiState: NewsUiState,
    scaffoldState: ScaffoldState
) {
    if (uiState.errorMessage != null) {
        val errorMessage = stringResource(id = uiState.errorMessage)
        val actionLabel = stringResource(id = R.string.snackbar_button)
        LaunchedEffect(uiState.errorMessage) {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.ActionPerformed) {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            }
        }
    }
}