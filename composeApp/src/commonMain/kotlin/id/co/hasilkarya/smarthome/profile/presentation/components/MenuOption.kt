package id.co.hasilkarya.smarthome.profile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import id.co.hasilkarya.smarthome.core.theme.SmartHomeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BiometricAuthOption(
    modifier: Modifier = Modifier,
    title: String,
    enabled: Boolean,
    onAction: () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.09f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, color = Color.White)
            Switch(
                checked = enabled,
                onCheckedChange = {
                    onAction()
                }
            )
        }
    }
}

@Preview
@Composable
fun MenuOptionsPreview() {
    SmartHomeTheme {
        BiometricAuthOption(
            modifier = Modifier,
            title = "Autentikasi Biometrik",
            enabled = false,
            onAction = {  }
        )
    }
}