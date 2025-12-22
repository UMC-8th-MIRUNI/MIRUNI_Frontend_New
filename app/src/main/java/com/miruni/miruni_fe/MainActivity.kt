package com.miruni.miruni_fe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.navigation.NavigationDestination
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var destinations: Set<@JvmSuppressWildcards NavigationDestination>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, windowInsets ->
            WindowInsetsCompat.CONSUMED
        }
        setContent {
            MiruniTheme {
                MainScreen(destinations = destinations)
            }
        }
    }
}