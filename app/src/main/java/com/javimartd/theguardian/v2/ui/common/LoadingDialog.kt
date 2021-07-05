package com.javimartd.theguardian.v2.ui.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.javimartd.theguardian.v2.R

class LoadingDialog(private var context: Context?): DialogActions {

    private lateinit var dialog: Dialog

    init {
        createDialog()
    }

    private fun createDialog() {
        context?.let {
            dialog = Dialog(it)
            dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.dialog_loading)
            dialog.setCancelable(false)
        }
    }

    override fun showDialog() {
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    override fun hideDialog() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }

    override fun onDetach() {
        if (context != null) {
            context = null
        }
    }
}