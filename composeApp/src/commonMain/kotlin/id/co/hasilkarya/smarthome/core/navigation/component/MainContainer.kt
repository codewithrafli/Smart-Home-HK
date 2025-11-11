package id.co.hasilkarya.smarthome.core.navigation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.toRoute
import id.co.hasilkarya.smarthome.core.navigation.data.*
import id.co.hasilkarya.smarthome.core.theme.BrokenWhite
import id.co.hasilkarya.smarthome.core.utils.log
import id.co.hasilkarya.smarthome.device.presentation.DeviceScreen
import id.co.hasilkarya.smarthome.device.presentation.DeviceViewModel
import id.co.hasilkarya.smarthome.home.presentation.HomeScreen
import id.co.hasilkarya.smarthome.home.presentation.HomeViewModel
import id.co.hasilkarya.smarthome.profile.presentation.ProfileScreen
import id.co.hasilkarya.smarthome.profile.presentation.ProfileViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainContainer(
    logout: () -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            Navbar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
            navController = navController,
            startDestination = HomeDestination,
        ) {
            composable<HomeDestination> {
                val viewModel = koinViewModel<HomeViewModel>()
                val state by viewModel.state.collectAsState()
                HomeScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    onNavigate = { id ->
                        navController.navigate(DeviceDestination(id ?: 0))
                    }
                )
            }
            composable<DeviceDestination> {
                val viewModel = koinViewModel<DeviceViewModel>()
                val state by viewModel.state.collectAsState()
                DeviceScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    onNavigateBack = { navController.navigateUp() },
                    deviceId = it.toRoute<DeviceDestination>().deviceId
                )
            }
            composable<HistoryDestination> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = HistoryDestination.toString(),
                        color = BrokenWhite
                    )
                }
            }
            composable<AutomationDestination> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = AutomationDestination.toString(),
                        color = BrokenWhite
                    )
                }
            }
            composable<ProfileDestination> {
                val viewModel = koinViewModel<ProfileViewModel>()
                val state by viewModel.state.collectAsState()
                ProfileScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    onNavigateBack = logout
                )
            }
        }
    }
}

@Preview
@Composable
fun MainContainerPreview() {
    MainContainer(
        logout = {  }
    )
}