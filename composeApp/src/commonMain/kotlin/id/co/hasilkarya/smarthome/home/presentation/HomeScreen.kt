package id.co.hasilkarya.smarthome.home.presentation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import id.co.hasilkarya.smarthome.core.presentation.CustomLoadingNotification
import id.co.hasilkarya.smarthome.core.presentation.CustomNotification
import id.co.hasilkarya.smarthome.core.theme.BrokenWhite
import id.co.hasilkarya.smarthome.core.theme.DarkBlue
import id.co.hasilkarya.smarthome.core.theme.SmartHomeTheme
import id.co.hasilkarya.smarthome.home.presentation.components.DeviceCard
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smarthomehasilkarya.composeapp.generated.resources.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    onNavigate: (Int?) -> Unit,
) {

    LaunchedEffect(state.token) {
        if (state.token.isNotBlank())
            onEvent(HomeEvent.OnLoadData)
    }

    val deviceRows = state.devices.chunked(2)

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (getCurrentHourInt() in 6..11)
                            stringResource(Res.string.morning)
                        else if (getCurrentHourInt() in 12..17)
                            stringResource(Res.string.afternoon)
                        else stringResource(Res.string.evening),
                        color = BrokenWhite
                    )
                    Text(
                        text = state.user?.name ?: "Loading...",
                        style = MaterialTheme.typography.headlineSmall,
                        color = BrokenWhite
                    )
                }
                Surface(
                    color = DarkBlue.copy(alpha = 0.2f),
                    shape = CircleShape
                ) {
                    Icon(
                        modifier = Modifier.padding(12.dp),
                        painter = painterResource(Res.drawable.notification_bing),
                        contentDescription = stringResource(Res.string.notification),
                        tint = BrokenWhite
                    )
                }
            }
        }
    ) {
        Box {
            AnimatedVisibility(
                visible = state.isLoading,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                CustomLoadingNotification()
            }
            LazyColumn(
                contentPadding = PaddingValues(
                    top = it.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp,
                ),
            ) {
                if (state.isError) {
                    item {
                        AnimatedVisibility(
                            state.isError && state.message.asComposableString().isNotEmpty()
                        ) {
                            CustomNotification(
                                isError = state.isError,
                                data = state.message.asComposableString()
                            )
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.your_device),
                            color = BrokenWhite,
                        )
                        Surface(
                            color = Color.Transparent,
                            onClick = { }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = stringResource(Res.string.all_device),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = BrokenWhite
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                    contentDescription = stringResource(Res.string.all_device),
                                    tint = BrokenWhite,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
                items(deviceRows) { devicePair ->
                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DeviceCard(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            device = devicePair.first(),
                            onToggle = { device, property, value ->
                                onEvent(HomeEvent.OnDeviceToggle(device, property, value))
                            },
                            onClick = { onNavigate(devicePair.first().id) }
                        )

                        if (devicePair.size > 1) {
                            DeviceCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                device = devicePair.last(),
                                onToggle = { device, property, value ->
                                    onEvent(HomeEvent.OnDeviceToggle(device, property, value))
                                },
                                onClick = { onNavigate(devicePair[1].id) }
                            )
                        } else {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
fun getCurrentHourInt(): Int {
    val now = Clock.System.now()
    val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
    return localDateTime.hour
}

@Preview
@Composable
fun HomeScreenPreview() {
    SmartHomeTheme {
        HomeScreen(
            state = HomeState(),
            onEvent = { },
            onNavigate = { }
        )
    }
}