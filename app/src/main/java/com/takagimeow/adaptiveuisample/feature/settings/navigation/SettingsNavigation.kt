package com.takagimeow.adaptiveuisample.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import com.takagimeow.adaptiveuisample.core.navigation.NavigationDestination
import com.takagimeow.adaptiveuisample.core.navigation.SCHEME
import com.takagimeow.adaptiveuisample.feature.settings.SettingsRoute

object SettingsDestination : NavigationDestination {
    override val route = "settings_route"
    override val destination = "settings_destination"

    val deepLinks = listOf(
        navDeepLink {
            uriPattern = "$SCHEME://${destination}"
        }
    )
}

fun NavGraphBuilder.settingsGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(route = SettingsDestination.route, startDestination = SettingsDestination.destination) {
        composable(
            route = SettingsDestination.destination,
            deepLinks = SettingsDestination.deepLinks
        ) { navBackStackEntry ->
            SettingsRoute()
        }
    }
    nestedGraphs()
}