package com.takagimeow.adaptiveuisample.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.takagimeow.adaptiveuisample.core.navigation.NavigationDestination
import com.takagimeow.adaptiveuisample.feature.home.navigation.homeGraph

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onNavigateToDestination: (NavigationDestination, String, from: NavBackStackEntry?) -> Unit,
    onNavigateAndPopUpToDestination: (NavigationDestination, String, from: NavBackStackEntry?) -> Unit,
    startDestination: String,
    onUpPress: () -> Unit,
    onSetListState: (listState: LazyListState) -> Unit = {},
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        homeGraph {

        }
    }
}
