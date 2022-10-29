package com.javimartd.theguardian.v2.ui.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val DATE_TIME_API_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

fun Long.toDate(dateFormat: Int = DateFormat.MEDIUM): String {
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this)
}

fun String.toLong(format: String): Long {
    val df =  SimpleDateFormat(format, Locale.getDefault())
    val d = df.parse(this)
    return d.time
}