package com.takagimeow.adaptiveuisample

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.takagimeow.adaptiveuisample.feature.home.navigation.HomeDestination
import com.takagimeow.adaptiveuisample.ui.AdaptiveUISampleAppState
import com.takagimeow.adaptiveuisample.ui.AdaptiveUISampleNavGraph
import com.takagimeow.adaptiveuisample.ui.components.AdaptiveUISampleBottomNavigationBar
import com.takagimeow.adaptiveuisample.ui.components.AdaptiveUISampleNavigationDrawerContent
import com.takagimeow.adaptiveuisample.ui.components.AdaptiveUISampleNavigationRail
import com.takagimeow.adaptiveuisample.ui.rememberAppState
import com.takagimeow.adaptiveuisample.ui.utils.AdaptiveUISampleContentType
import com.takagimeow.adaptiveuisample.ui.utils.DevicePosture
import com.takagimeow.adaptiveuisample.ui.utils.AdapativeUISampleNavigationType
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdaptiveUISampleApp(
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture, // 折りたたみの状態
) {
    val appState = rememberAppState()
    val navigationType: AdapativeUISampleNavigationType
    val contentType: AdaptiveUISampleContentType

    when (windowSize) {
        // 画面サイズがコンパクト（スマートフォン）の場合
        WindowWidthSizeClass.Compact -> {
            navigationType = AdapativeUISampleNavigationType.BOTTOM_NAVIGATION
            contentType = AdaptiveUISampleContentType.LIST_ONLY
        }
        // 画面サイズが中程度の場合
        WindowWidthSizeClass.Medium -> {
            navigationType = AdapativeUISampleNavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                // 折りたたみの状態がGalaxy Z Fold 4の開いた状態、もしくはSurface DUOの開いた状態でない場合は、リストと詳細を１つの画面に表示する
                AdaptiveUISampleContentType.LIST_AND_DETAIL
            } else {
                // 折り畳まれている状態の場合はリストのみを表示する
                AdaptiveUISampleContentType.LIST_ONLY
            }
        }
        // 画面サイズがタブレットの場合
        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                AdapativeUISampleNavigationType.NAVIGATION_RAIL
            } else {
                AdapativeUISampleNavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = AdaptiveUISampleContentType.LIST_AND_DETAIL
        }
        else -> {
            navigationType = AdapativeUISampleNavigationType.BOTTOM_NAVIGATION
            contentType = AdaptiveUISampleContentType.LIST_ONLY
        }
    }

    AdaptiveUISampleNavigationWrapperUI(appState, navigationType, contentType)
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdaptiveUISampleNavigationWrapperUI(
    appState: AdaptiveUISampleAppState,
    navigationType: AdapativeUISampleNavigationType,
    contentType: AdaptiveUISampleContentType,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (navigationType == AdapativeUISampleNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet {
                    AdaptiveUISampleNavigationDrawerContent(
                        destinations = appState.topLevelDestinations,
                        selectedDestination = appState.currentDestination?.route,
                        navigateToRoute = appState::navigateAndPopUp
                    )
                }
            }
        ) {
            AppContent(
                appState,
                navigationType,
                contentType,
            )
        }
    } else {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    AdaptiveUISampleNavigationDrawerContent(
                        destinations = appState.topLevelDestinations,
                        selectedDestination = appState.currentDestination?.route,
                        onDrawerClicked = {
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        navigateToRoute = appState::navigateAndPopUp
                    )
                }
            },
            drawerState = drawerState
        ) {
            AppContent(
                appState,
                navigationType,
                contentType,
                onDrawerClicked = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppContent(
    appState: AdaptiveUISampleAppState,
    navigationType: AdapativeUISampleNavigationType,
    contentType: AdaptiveUISampleContentType,
    onDrawerClicked: () -> Unit = {}
) {
    Row(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == AdapativeUISampleNavigationType.NAVIGATION_RAIL) {
            AdaptiveUISampleNavigationRail(
                destinations = appState.topLevelDestinations,
                selectedDestination = appState.currentDestination?.route,
                onDrawerClicked = onDrawerClicked,
                navigateToRoute = appState::navigateAndPopUp
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                // .background(MaterialTheme.colorScheme.inverseSurface)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                AdaptiveUISampleNavGraph(
                    navController = appState.navController,
                    onNavigateToDestination = appState::navigate,
                    onNavigateAndPopUpToDestination = appState::navigateAndPopUp,
                    startDestination = HomeDestination.route,
                    onUpPress = { /*TODO*/ }
                )
            }
            if (contentType == AdaptiveUISampleContentType.LIST_AND_DETAIL) {
                /**
                 * ListAndDetailContent(
                 *   modifier = Modifier.weight(1f),
                 * )
                 */
            } else {
                /**
                 * ListOnlyContent(
                 *   modifier = Modifier.weight(1f)
                 * )
                 */
            }
            AnimatedVisibility(visible = navigationType == AdapativeUISampleNavigationType.BOTTOM_NAVIGATION) {
                AdaptiveUISampleBottomNavigationBar(
                    destinations = appState.topLevelDestinations,
                    currentRoute = appState.currentDestination?.route,
                    navigateToRoute = appState::navigateAndPopUp
                )
            }
        }
    }
}