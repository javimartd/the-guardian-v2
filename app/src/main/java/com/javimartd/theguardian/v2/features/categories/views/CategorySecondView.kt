package com.javimartd.theguardian.v2.features.categories.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.features.categories.navigation.CategoryNavigator

@Composable
fun CategorySecondView(
    navigator: CategoryNavigator,
    firstArg: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.medium_margin),
            Alignment.CenterVertically
        )
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            text = "Second Category View!",
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 24.sp)
        )
        Text(
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.fillMaxWidth(),
            text = firstArg.toString(),
            textAlign = TextAlign.Center,
        )
        Button(
            onClick = {
                navigator.actionNavigateToThirdView(
                    "my first argument",
                    "my second argument"
                )
            }
        ) {
            Text(text = "Navigate Forward")
        }
        Button(
            onClick = { navigator.actionNavigateUp() }
        ) {
            Text(text = "Navigate Back")
        }
    }
}