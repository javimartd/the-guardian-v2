package com.javimartd.theguardian.v2

import java.io.IOException

fun readJsonFromAssets(fileName: String): String? {
    return try {
        TheGuardianApp.instance.applicationContext.assets
            .open(fileName)
            .bufferedReader()
            .use { it.readText() }
    } catch (ioException: IOException) {
        null
    }
}