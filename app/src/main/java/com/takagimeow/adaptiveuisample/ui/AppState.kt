package com.takagimeow.adaptiveuisample.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.takagimeow.adaptiveuisample.core.navigation.NavigationDestination
import com.takagimeow.adaptiveuisample.navigation.TopLevelDestination

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    AppState(
        navController = navController
    )
}

@Stable
class AppState(
    val navController: NavHostController,
) {
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
        if (from != null /* && from.lifecycleIsResumed() */) {
            val newRoute = when (destination) {
                is TopLevelDestination -> {
                    destination.destination
                }
                else -> {
                    route ?: destination.route
                }
            }
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