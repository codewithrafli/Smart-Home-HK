package id.co.hasilkarya.smarthome.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import id.co.hasilkarya.smarthome.core.theme.SmartHomeTheme
import id.co.hasilkarya.smarthome.login.presentation.LoginScreen
import id.co.hasilkarya.smarthome.login.presentation.LoginViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    SmartHomeTheme {
        val viewModel = koinViewModel<LoginViewModel>()
        val state by viewModel.state.collectAsState()
        LoginScreen(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}