package id.co.hasilkarya.smarthome.smarthomehasilkarya.core.theme

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
actual fun SmartHomeTheme(content: @Composable (() -> Unit)) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    val verticalGradientBrush = Brush.verticalGradient(
        colors = listOf(LightBlue, DarkBlue)
    )
    MaterialTheme(
        typography = LatoTypography(),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(verticalGradientBrush),
            color = Color.Transparent
        ) {
            content()
        }
    }
}