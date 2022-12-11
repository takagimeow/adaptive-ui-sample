package com.takagimeow.adaptiveuisample.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.takagimeow.adaptiveuisample.core.designsystem.icon.Icon
import com.takagimeow.adaptiveuisample.core.navigation.NavigationDestination
import com.takagimeow.adaptiveuisample.navigation.TopLevelDestination

const val TAG = "AdaptiveUISampleBottomNavigationBar"

@Composable
fun AdaptiveUISampleBottomNavigationBar(
    destinations: List<TopLevelDestination>,
    currentRoute: String?,
    navigateToRoute: (NavigationDestination) -> Unit,
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        destinations.forEachIndexed { index, destination ->
            val selected = currentRoute == destination.destination
            NavigationBarItem(
                modifier = Modifier.testTag(
                    destination.destination
                ),
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
                label = {
                    Text(stringResource(id = destination.iconTextId))
                },
                selected = selected,
                onClick = {
                    navigateToRoute(destination)
                },
            )
        }
    }
}