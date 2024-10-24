package util

import androidx.compose.runtime.Composable
import platform.UIKit.UIScreen

@Composable
actual fun getDpi(): Double {
    val screen = UIScreen.mainScreen
    return screen.scale * 160
}