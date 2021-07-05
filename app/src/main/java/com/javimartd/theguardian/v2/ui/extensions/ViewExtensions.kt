package com.javimartd.theguardian.v2.ui.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(this.context).inflate(layoutRes, this, false)
}

val View.ctx: Context
    get() = context