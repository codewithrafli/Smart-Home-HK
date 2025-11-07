package id.co.hasilkarya.smarthome.core.navigation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.co.hasilkarya.smarthome.core.navigation.data.AutomationDestination
import id.co.hasilkarya.smarthome.core.navigation.data.HistoryDestination
import id.co.hasilkarya.smarthome.core.navigation.data.HomeDestination
import id.co.hasilkarya.smarthome.core.navigation.data.ProfileDestination
import id.co.hasilkarya.smarthome.home.presentation.HomeScreen
import id.co.hasilkarya.smarthome.home.presentation.HomeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainContainer() {
    val navController = rememberNavController()
    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            Navbar(navController = navController)
        }
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = HomeDestination,
        ) {
            composable<HomeDestination> {
                val viewModel = koinViewModel<HomeViewModel>()
                val state by viewModel.state.collectAsState()
                HomeScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                )
            }
            composable<HistoryDestination> {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = HistoryDestination.toString()
                    )
                }
            }
            composable<AutomationDestination> {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = AutomationDestination.toString()
                    )
                }
            }
            composable<ProfileDestination> {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = ProfileDestination.toString()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MainContainerPreview() {
    MainContainer()
}