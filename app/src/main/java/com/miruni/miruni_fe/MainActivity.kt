package com.miruni.miruni_fe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.navigation.NavigationDestination
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var destinations: Set<@JvmSuppressWildcards NavigationDestination>

    // 스플래시 얼마나 유지할지
    private val keepSplashOn = AtomicBoolean(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 앱 준비 완료 시점까지 시스템 기본 스플래시 유지
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplashOn.get() }

        enableEdgeToEdge()
//        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, windowInsets ->
//            WindowInsetsCompat.CONSUMED
//        }
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        controller.hide(
            WindowInsetsCompat.Type.systemBars()
        )

        setContent {
            MiruniTheme {
                LaunchedEffect(Unit) {
                    snapshotFlow { true }.first()
                    keepSplashOn.set(false)
                }
                MainScreen(destinations = destinations)
            }
        }
    }
}