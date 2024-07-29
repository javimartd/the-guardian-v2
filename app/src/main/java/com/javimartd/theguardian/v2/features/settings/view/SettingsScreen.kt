package com.javimartd.theguardian.v2.features.settings.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.features.settings.navigation.SettingsNavigator
import com.javimartd.theguardian.v2.ui.components.TheGuardianSnackBarHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigator: SettingsNavigator,
    viewModel: SettingsViewModel
) {

    var checked by remember { mutableStateOf(true) }
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { TheGuardianSnackBarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_screen_toolbar)) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigator.actionNavigateUp() }

                    ) {
                        Icon(
                            contentDescription = stringResource(id = R.string.news_screen_settings_icon_content_description),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Row(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.medium_margin)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                maxLines = 1,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
                text = stringResource(id = R.string.settings_screen_dark_mode_option),
                overflow = TextOverflow.Ellipsis
            )
            Switch(
                checked = checked,
                onCheckedChange = { checked = !checked }
            )
        }
    }
}