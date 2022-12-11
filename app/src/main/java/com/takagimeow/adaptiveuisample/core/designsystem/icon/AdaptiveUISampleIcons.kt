package com.takagimeow.adaptiveuisample.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

object AdaptiveUISampleIcons {
    val Home = Icons.Outlined.Home
    val HomeFilled = Icons.Filled.Home
    val Settings = Icons.Outlined.Settings
    val SettingsFilled = Icons.Filled.Settings
}

sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}