package com.takagimeow.adaptiveuisample.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.takagimeow.adaptiveuisample.core.designsystem.icon.Icon
import com.takagimeow.adaptiveuisample.core.navigation.NavigationDestination
import com.takagimeow.adaptiveuisample.feature.home.navigation.HomeDestination
import com.takagimeow.adaptiveuisample.feature.settings.navigation.SettingsDestination
import com.takagimeow.adaptiveuisample.navigation.TopLevelDestination

@Composable
fun AdaptiveUISampleNavigationRail(
    destinations: List<TopLevelDestination>,
    selectedDestination: String?,
    onDrawerClicked: () -> Unit = {},
    navigateToRoute: (NavigationDestination) -> Unit,
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight()
    ) {
        NavigationRailItem(
            selected = false,
            onClick = onDrawerClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = ""
                )
            }
        )
        destinations.forEachIndexed { index, destination ->
            val selected = selectedDestination == destination.destination
            NavigationRailItem(
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    when (icon) {
                        is Icon.ImageVectorIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = destination.route
                        )
                        is Icon.DrawableResourceIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = destination.route
                        )
                    }
                },
                selected = selected,
                onClick = {
                    navigateToRoute(destination)
                },
            )
        }
        NavigationRailItem(
            selected = selectedDestination == HomeDestination.destination,
            onClick = {
              navigateToRoute(HomeDestination)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = ""
                )
            }
        )
        NavigationRailItem(
            selected = selectedDestination == SettingsDestination.destination,
            onClick = {
                navigateToRoute(SettingsDestination)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = ""
                )
            }
        )
    }
}