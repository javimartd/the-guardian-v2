package com.javimartd.theguardian.v2.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.window.Dialog
import com.javimartd.theguardian.v2.R

@Composable
fun LoadingDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        CircularProgressIndicator(
            color = colorResource(id = R.color.blue_500),
            modifier = Modifier.testTag(Tags.TAG_LOADING_DIALOG)
        )
    }
}