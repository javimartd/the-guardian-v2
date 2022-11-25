package com.javimartd.theguardian.v2.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.ui.components.TheGuardianSnackbarHost
import com.javimartd.theguardian.v2.ui.news.model.NewsUiContract

@Composable
fun SettingsScreen(navHostController: NavHostController) {

    var checked by remember { mutableStateOf(true) }

    Scaffold(
        snackbarHost = { TheGuardianSnackbarHost(hostState = it) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_screen_toolbar)) },
                navigationIcon = {
                    IconButton(
                        onClick = { navHostController.navigateUp() }
                    ) {
                        Icon(
                            contentDescription = stringResource(id = R.string.news_screen_settings_icon_content_description),
                            imageVector = Icons.Default.ArrowBack,
                            tint = colorResource(id = R.color.white)
                        )
                    }
                }
            )
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.medium_margin))
        ) {
            Text(
                maxLines = 1,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.body1,
                text = stringResource(id = R.string.settings_screen_first_option),
                overflow = TextOverflow.Ellipsis
            )
            Switch(
                checked = checked,
                onCheckedChange = { checked = !checked }
            )
        }
    }
}