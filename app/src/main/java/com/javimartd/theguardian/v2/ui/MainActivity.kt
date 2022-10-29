package com.javimartd.theguardian.v2.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.javimartd.theguardian.v2.ui.navigation.TheGuardianNavGraph
import com.javimartd.theguardian.v2.ui.theme.TheGuardianTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheGuardianTheme {
                TheGuardianNavGraph()
            }
        }
    }
}