package id.co.hasilkarya.smarthome.splash.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.zaval.biometricauthentificator.rememberBiometricAuthHelper
import id.co.hasilkarya.smarthome.core.navigation.data.LoginDestination
import id.co.hasilkarya.smarthome.core.navigation.data.MainGraph
import id.co.hasilkarya.smarthome.core.theme.BrokenWhite
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smarthomehasilkarya.composeapp.generated.resources.Res
import smarthomehasilkarya.composeapp.generated.resources.app_name

@Composable
fun SplashScreen(
    state: SplashState,
    onEvent: (SplashEvent) -> Unit,
    onNavigate: (Any) -> Unit,
) {
    val biometricAuthHelper = rememberBiometricAuthHelper()

    LaunchedEffect(state.token, state.biometricAuthEnabled) {
        delay(2000)
        if (state.token.isNotBlank()) {
            if (state.biometricAuthEnabled) {
                biometricAuthHelper.authenticate(
                    onSuccess = { onEvent(SplashEvent.OnBiometricAuthSuccess(true)) },
                    onFailure = { }
                )
            } else
                onNavigate(MainGraph)
        } else
            onNavigate(LoginDestination)
    }

    LaunchedEffect(state.biometricAuthSuccess) {
        if (state.biometricAuthSuccess)
            onNavigate(MainGraph)
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = stringResource(resource = Res.string.app_name),
            style = MaterialTheme.typography.headlineSmall,
            color = BrokenWhite
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(
        state = SplashState(),
        onNavigate = { },
        onEvent = { }
    )
}