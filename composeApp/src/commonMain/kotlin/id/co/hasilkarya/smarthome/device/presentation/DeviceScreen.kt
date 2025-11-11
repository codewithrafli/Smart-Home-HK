package id.co.hasilkarya.smarthome.device.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import id.co.hasilkarya.smarthome.core.presentation.CustomLoadingNotification
import id.co.hasilkarya.smarthome.core.theme.BrokenWhite
import id.co.hasilkarya.smarthome.core.theme.DarkBlue
import id.co.hasilkarya.smarthome.core.theme.SmartHomeTheme
import id.co.hasilkarya.smarthome.device.presentation.utils.floatToPercentageString
import id.co.hasilkarya.smarthome.home.domain.models.Device
import id.co.hasilkarya.smarthome.home.domain.models.DeviceType
import id.co.hasilkarya.smarthome.home.domain.models.Home
import id.co.hasilkarya.smarthome.home.domain.models.Room
import id.co.hasilkarya.smarthome.home.presentation.components.*
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smarthomehasilkarya.composeapp.generated.resources.*

const val CONTROL_TYPE_SLIDER = "slider"
const val KEY_BRIGHTNESS = "brightness"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceScreen(
    state: DeviceState,
    deviceId: Int,
    onEvent: (DeviceEvent) -> Unit,
    onNavigateBack: () -> Unit,
) {

    LaunchedEffect(deviceId, state.token) {
        if (deviceId != 0 && state.token.isNotBlank()) {
            onEvent(DeviceEvent.OnLoadData(deviceId))
        }
    }

    Surface(
        color = Color.Transparent,
        contentColor = BrokenWhite,
    ) {
        if (state.device != null) {
            Box {
                if (state.isLoading) {
                    CustomLoadingNotification()
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(
                            onClick = onNavigateBack
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(Res.string.back)
                            )
                        }
                        Text(
                            text = state.device.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Box(modifier = Modifier.size(32.dp))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = when (state.device.properties["state"]) {
                                STATE_ON_KEY -> stringResource(Res.string.on)
                                STATE_OFF_KEY -> stringResource(Res.string.off)
                                STATE_OPEN_KEY -> stringResource(Res.string.open)
                                STATE_CLOSED_KEY -> stringResource(Res.string.closed)
                                else -> stringResource(Res.string.unknown)
                            },
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Switch(
                            modifier = Modifier.height(16.dp),
                            checked = state.device.properties["state"] == STATE_ON_KEY || state.device.properties["state"] == STATE_OPEN_KEY,
                            onCheckedChange = {
                                val newState = when (state.device.properties["state"]) {
                                    STATE_ON_KEY -> STATE_OFF_KEY
                                    STATE_OFF_KEY -> STATE_ON_KEY
                                    STATE_OPEN_KEY -> STATE_CLOSED_KEY
                                    STATE_CLOSED_KEY -> STATE_OPEN_KEY
                                    else -> STATE_OFF_KEY
                                }
                                onEvent(DeviceEvent.OnDeviceToggle("state", newState))
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = DarkBlue,
                                checkedTrackColor = BrokenWhite,
                                uncheckedThumbColor = DarkBlue,
                                uncheckedTrackColor = BrokenWhite,
                            ),
                        )
                    }
                    if (state.uiConfig == CONTROL_TYPE_SLIDER) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = if (state.device.properties.keys.toList()[1] == KEY_BRIGHTNESS)
                                        stringResource(Res.string.intensity)
                                    else stringResource(Res.string.unknown)
                                )
                                Text(
                                    text = floatToPercentageString(state.sliderValue)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Slider(
                                modifier = Modifier.fillMaxWidth(),
                                value = state.sliderValue,
                                onValueChange = {
                                    onEvent(DeviceEvent.OnSliderChanged( it))
                                },
                                onValueChangeFinished = {
                                    onEvent(DeviceEvent.OnSliderReleased(KEY_BRIGHTNESS))
                                },
                                colors = SliderDefaults.colors(
                                    activeTrackColor = Color.LightGray,
                                    inactiveTrackColor = Color.DarkGray,
                                    thumbColor = Color.LightGray,
                                    activeTickColor = Color.LightGray,
                                    inactiveTickColor = Color.DarkGray,
                                ),
                                thumb = {
                                    Surface(
                                        modifier = Modifier.size(24.dp),
                                        shape = CircleShape
                                    ) { }
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DeviceScreenPreview() {
    SmartHomeTheme {
        DeviceScreen(
            state = DeviceState(
                device = Device(
                    id = 1,
                    name = "Gerbang Utama",
                    uniqueId = "32414234",
                    deviceType = DeviceType(id = 1, name = "Lampu"),
                    room = Room(id = 1, name = "Ruang Keluarga"),
                    home = Home(id = 1, name = "Rumah Keluarga Bahagia"),
                    properties = mapOf("state" to STATE_OFF_KEY, "brightness" to 0.5f),
                    uiConfig = mapOf("icon" to LAMP_ICON_KEY, "uiConfig" to CONTROL_TYPE_SLIDER)
                ),
                uiConfig = CONTROL_TYPE_SLIDER,
                sliderValue = 0.5f
            ),
            onEvent = { },
            onNavigateBack = { },
            deviceId = 0
        )
    }
}