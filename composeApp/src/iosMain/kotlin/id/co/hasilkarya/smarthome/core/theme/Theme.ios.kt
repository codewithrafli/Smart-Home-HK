package id.co.hasilkarya.smarthome.core.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import id.co.hasilkarya.smarthome.core.theme.DarkBlue
import id.co.hasilkarya.smarthome.core.theme.LatoTypography
import id.co.hasilkarya.smarthome.core.theme.LightBlue

@Composable
actual fun SmartHomeTheme(content: @Composable (() -> Unit)) {
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