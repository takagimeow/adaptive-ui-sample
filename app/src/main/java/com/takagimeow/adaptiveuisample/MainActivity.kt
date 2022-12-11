package com.takagimeow.adaptiveuisample

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.takagimeow.adaptiveuisample.ui.theme.AdaptiveUISampleTheme
import com.takagimeow.adaptiveuisample.ui.utils.DevicePosture
import com.takagimeow.adaptiveuisample.ui.utils.isBookPosture
import com.takagimeow.adaptiveuisample.ui.utils.isSeparating
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 折りたたみの状態を確認し、かつモニタリングする
        val devicePostureFlow = WindowInfoTracker.getOrCreate(this)
            .windowLayoutInfo(this)
            .flowWithLifecycle(this.lifecycle) // lifecycle-viewmodel-compose:2.5.1以降対応
            .map { layoutInfo ->
                val foldingFeature =
                    layoutInfo.displayFeatures
                        .filterIsInstance<FoldingFeature>()
                        .firstOrNull()
                when {
                    isBookPosture(foldingFeature) ->
                        DevicePosture.BookPosture(foldingFeature.bounds)
                    isSeparating(foldingFeature) ->
                        DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)
                    else -> DevicePosture.NormalPosture
                }
            }
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DevicePosture.NormalPosture
            )

        setContent {
            AdaptiveUISampleTheme {
                val windowSize = calculateWindowSizeClass(this)
                val devicePosture = devicePostureFlow.collectAsState().value

                AdaptiveUISampleApp(windowSize = windowSize.widthSizeClass, foldingDevicePosture = devicePosture)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AdaptiveUISampleTheme {
        Greeting("Android")
    }
}