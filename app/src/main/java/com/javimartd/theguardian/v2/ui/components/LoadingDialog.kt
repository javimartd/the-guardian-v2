package com.javimartd.theguardian.v2.ui.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.window.Dialog
import com.javimartd.theguardian.v2.R
import com.javimartd.theguardian.v2.ui.components.Tags.TAG_LOADING_DIALOG

@Composable
fun LoadingDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        CircularProgressIndicator(
            color = colorResource(id = R.color.blue_500),
            modifier = Modifier.testTag(TAG_LOADING_DIALOG)
        )
    }
}