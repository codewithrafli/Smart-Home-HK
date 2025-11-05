package id.co.hasilkarya.smarthome.core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import smarthomehasilkarya.composeapp.generated.resources.*

@Composable
fun LatoFamily(modifier: Modifier = Modifier) = FontFamily(
    Font(resource = Res.font.lato_thin, weight = FontWeight.Thin),
    Font(resource = Res.font.lato_thinitalic, weight = FontWeight.Thin, style = FontStyle.Italic),
    Font(resource = Res.font.lato_light, weight = FontWeight.Light),
    Font(resource = Res.font.lato_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(resource = Res.font.lato_regular, weight = FontWeight.Normal),
    Font(resource = Res.font.lato_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(resource = Res.font.lato_bold, weight = FontWeight.Bold),
    Font(resource = Res.font.lato_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(resource = Res.font.lato_black, weight = FontWeight.Black),
    Font(resource = Res.font.lato_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic),
)

@Composable
fun LatoTypography(modifier: Modifier = Modifier) = Typography().run {
    val fontFamily = LatoFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}

