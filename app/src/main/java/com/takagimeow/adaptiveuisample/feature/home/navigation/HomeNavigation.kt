package com.takagimeow.adaptiveuisample.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import com.takagimeow.adaptiveuisample.core.navigation.NavigationDestination
import com.takagimeow.adaptiveuisample.core.navigation.SCHEME
import com.takagimeow.adaptiveuisample.feature.home.HomeRoute

object HomeDestination : NavigationDestination {
    override val route = "home_route"
    override val destination = "home_destination"

    val deepLinks = listOf(
        navDeepLink {
            uriPattern = "$SCHEME://${destination}"
        }
    )
}

fun NavGraphBuilder.homeGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(route = HomeDestination.route, startDestination = HomeDestination.destination) {
        composable(
            route = HomeDestination.destination,
            deepLinks = HomeDestination.deepLinks,
        ) { navBackStackEntry ->
            HomeRoute()
        }
    }
    nestedGraphs()
}