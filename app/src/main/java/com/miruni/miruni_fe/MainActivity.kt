package com.miruni.miruni_fe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.vector.ImageVector
import com.miruni.designsystem.MiruniTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiruniTheme {
                MainScreen()
            }
        }
    }
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
)