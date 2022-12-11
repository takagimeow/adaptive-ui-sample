package com.takagimeow.adaptiveuisample.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.takagimeow.adaptiveuisample.R
import com.takagimeow.adaptiveuisample.core.designsystem.icon.AdaptiveUISampleIcons
import com.takagimeow.adaptiveuisample.core.designsystem.icon.Icon
import com.takagimeow.adaptiveuisample.core.navigation.NavigationDestination
import com.takagimeow.adaptiveuisample.feature.home.navigation.HomeDestination
import com.takagimeow.adaptiveuisample.feature.settings.navigation.SettingsDestination
import com.takagimeow.adaptiveuisample.navigation.TopLevelDestination

const val TAG = "AdaptiveUISampleAppState"

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    AdaptiveUISampleAppState(
        navController = navController
    )
}

@Stable
class AdaptiveUISampleAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val topLevelDestinations: List<TopLevelDestination> = listOf(
        TopLevelDestination(
            route = HomeDestination.route,
            destination = HomeDestination.destination,
            selectedIcon = Icon.ImageVectorIcon(AdaptiveUISampleIcons.HomeFilled),
            unselectedIcon = Icon.ImageVectorIcon(AdaptiveUISampleIcons.Home),
            iconTextId = R.string.home,
        ),
        TopLevelDestination(
            route = SettingsDestination.route,
            destination = SettingsDestination.destination,
            selectedIcon = Icon.ImageVectorIcon(AdaptiveUISampleIcons.SettingsFilled),
            unselectedIcon = Icon.ImageVectorIcon(AdaptiveUISampleIcons.Settings),
            iconTextId = R.string.settings
        )
    )

    fun upPress() {
        navController.popBackStack()
    }

    fun navigate(
        destination: NavigationDestination,
        route: String? = null,
        from: NavBackStackEntry? = try { navController.currentBackStackEntry } catch(err: Exception) { null }
    ) {
        if (from != null /* && from.lifecycleIsResumed() */) {
            navController.navigate(route ?: destination.destination) {
                // 特定のディスティネーションのコピーがバックスタックの一番上に最大で１つだけ配置されるようにする
                launchSingleTop = true
            }
        }
    }

    fun navigateAndPopUp(
        destination: NavigationDestination,
        route: String? = null,
        from: NavBackStackEntry? = try { navController.currentBackStackEntry } catch(err: Exception) { null }
    ) {
        Log.d(TAG, "from: ${from}")
        if (from != null /* && from.lifecycleIsResumed() */) {
            val newRoute = when (destination) {
                is TopLevelDestination -> {
                    destination.destination
                }
                else -> {
                    route ?: destination.route
                }
            }
            Log.d(TAG, "newRoute: $newRoute")
            Log.d(TAG, "from.destination.route: ${from.destination.route}")
            when(newRoute) {
                from.destination.route -> {
                    /* 何もしない */
                }
                else -> {
                    navController.navigate(newRoute) {
                        // タブ選択時に、バックスタックに大量のデスティネーションが積み重なるのを回避するために、グラフの最初のデスティネーションまでポップする
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // 特定のディスティネーションのコピーがバックスタックの一番上に最大で１つだけ配置されるようにする
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}