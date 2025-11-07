package id.co.hasilkarya.smarthome.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.co.hasilkarya.smarthome.core.navigation.component.MainContainer
import id.co.hasilkarya.smarthome.core.navigation.data.LoginDestination
import id.co.hasilkarya.smarthome.core.navigation.data.MainGraph
import id.co.hasilkarya.smarthome.core.navigation.data.SplashDestination
import id.co.hasilkarya.smarthome.core.theme.SmartHomeTheme
import id.co.hasilkarya.smarthome.login.presentation.LoginScreen
import id.co.hasilkarya.smarthome.login.presentation.LoginViewModel
import id.co.hasilkarya.smarthome.splash.presentation.SplashScreen
import id.co.hasilkarya.smarthome.splash.presentation.SplashViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    SmartHomeTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = SplashDestination
        ) {
            composable<SplashDestination> {
                val viewModel = koinViewModel<SplashViewModel>()
                val state by viewModel.state.collectAsState()
                SplashScreen(
                    state = state,
                    onNavigate = {
                        navController.navigate(it) {
                            popUpTo(it) { inclusive = true }
                        }
                    }
                )
            }
            composable<LoginDestination> {
                val viewModel = koinViewModel<LoginViewModel>()
                val state by viewModel.state.collectAsState()
                LoginScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    onNavigate = {
                        navController.navigate(MainGraph) {
                            popUpTo(LoginDestination) { inclusive = true }
                        }
                    }
                )
            }
            composable<MainGraph> {
                MainContainer()
            }
        }
    }
}