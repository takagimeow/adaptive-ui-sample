package com.takagimeow.adaptiveuisample.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.takagimeow.adaptiveuisample.core.designsystem.icon.Icon
import com.takagimeow.adaptiveuisample.core.navigation.NavigationDestination
import com.takagimeow.adaptiveuisample.navigation.TopLevelDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveUISampleNavigationDrawerContent(
    destinations: List<TopLevelDestination>,
    selectedDestination: String?,
    modifier: Modifier = Modifier,
    onDrawerClicked: () -> Unit = {},
    navigateToRoute: (NavigationDestination) -> Unit,
) {
    Column(
        modifier
            .wrapContentWidth()
            .fillMaxHeight()
            // .background(MaterialTheme.colorScheme.inverseSurface)
            .padding(24.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

        }

        destinations.forEachIndexed { index, destination ->
            val selected = selectedDestination == destination.destination
            NavigationDrawerItem(
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
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
                label = { Text(stringResource(id = destination.iconTextId)) },
                selected = selected,
                onClick = {
                    navigateToRoute(destination)
                },
            )
        }
    }
}