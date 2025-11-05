package id.co.hasilkarya.smarthome.smarthomehasilkarya.core

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import id.co.hasilkarya.smarthome.smarthomehasilkarya.core.theme.BrokenWhite
import id.co.hasilkarya.smarthome.smarthomehasilkarya.core.theme.SmartHomeTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import smarthomehasilkarya.composeapp.generated.resources.Res
import smarthomehasilkarya.composeapp.generated.resources.app_name
import smarthomehasilkarya.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    SmartHomeTheme {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.displayLarge,
                color = BrokenWhite,
            )
        }
    }
}