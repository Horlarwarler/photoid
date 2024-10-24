package util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun getDpi(): Double {
    val context = LocalContext.current
    val density = context.resources.displayMetrics.density * 160
    return density.toDouble()
}