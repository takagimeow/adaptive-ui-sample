package com.takagimeow.adaptiveuisample.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingsRoute(

) {
    SettingsScreen()
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
) {
    Column() {
        Text(text = "SettingsScreen")
    }
}