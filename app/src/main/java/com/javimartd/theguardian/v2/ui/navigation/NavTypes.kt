package com.javimartd.theguardian.v2.ui.navigation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

inline fun <reified T: Parcelable> NavType.Companion.parcelableTypeOf() =
    object: NavType<T>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): T? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, T::class.java)
            } else {
                @Suppress("DEPRECATION") // for backwards compatibility
                bundle.getParcelable(key) as T?
            }
        }

        override fun parseValue(value: String): T {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putParcelable(key, value)
        }
    }

inline fun <reified T: Parcelable> Bundle.getCustomParcelable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION") // for backwards compatibility
        this.getParcelable(key) as T?
    }
}