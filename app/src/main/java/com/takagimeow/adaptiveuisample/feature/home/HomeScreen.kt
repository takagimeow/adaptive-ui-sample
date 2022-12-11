package com.takagimeow.adaptiveuisample.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeRoute(

) {
    HomeScreen(
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        // modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "HomeScreen")
    }
}