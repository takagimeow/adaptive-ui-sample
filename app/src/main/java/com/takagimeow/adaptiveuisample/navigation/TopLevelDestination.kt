package com.takagimeow.adaptiveuisample.navigation

import com.takagimeow.adaptiveuisample.core.designsystem.icon.Icon
import com.takagimeow.adaptiveuisample.core.navigation.NavigationDestination

/**
 * アプリケーションのトップレベルのデスティネーション用のタイプです。これらのデスティネーションはそれぞれ
 * は、1つまたは複数の画面を含むことができます（ウィンドウサイズに基づく）。1つのデスティネーション内の1つのスクリーンから次のスクリーンへのナビゲーションは
 * 1つのデスティネーション内の次の画面へのナビゲーションは、コンポーザブルで直接処理されます。
 */
data class TopLevelDestination(
    override val route: String,
    override val destination: String,
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val iconTextId: Int
) : NavigationDestination