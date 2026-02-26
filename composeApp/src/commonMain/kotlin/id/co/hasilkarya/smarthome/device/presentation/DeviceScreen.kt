package id.co.hasilkarya.smarthome.device.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.co.hasilkarya.smarthome.core.presentation.CustomLoadingNotification
import id.co.hasilkarya.smarthome.core.theme.BrokenWhite
import id.co.hasilkarya.smarthome.core.theme.DarkBlue
import id.co.hasilkarya.smarthome.core.theme.GoodGreen
import id.co.hasilkarya.smarthome.core.theme.PrimaryBlue
import id.co.hasilkarya.smarthome.core.theme.SmartHomeTheme
import id.co.hasilkarya.smarthome.device.presentation.utils.DeviceCategory
import id.co.hasilkarya.smarthome.device.presentation.utils.classifyDevice
import id.co.hasilkarya.smarthome.device.presentation.utils.floatToPercentageString
import id.co.hasilkarya.smarthome.home.domain.models.Device
import id.co.hasilkarya.smarthome.home.domain.models.DeviceType
import id.co.hasilkarya.smarthome.home.domain.models.Home
import id.co.hasilkarya.smarthome.home.domain.models.Room
import id.co.hasilkarya.smarthome.home.domain.models.SensorReading
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
            val category = classifyDevice(state.uiConfig)

            Box {
                if (state.isLoading) {
                    CustomLoadingNotification()
                }
                when (category) {
                    DeviceCategory.SENSOR -> SensorDeviceContent(state, onEvent, onNavigateBack)
                    DeviceCategory.GATE -> GateDeviceContent(state, onEvent, onNavigateBack)
                    DeviceCategory.PUMP -> PumpDeviceContent(state, onEvent, onNavigateBack)
                    else -> ToggleDeviceContent(state, onEvent, onNavigateBack)
                }
            }
        }
    }
}

@Composable
private fun DeviceHeader(
    deviceName: String,
    onNavigateBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onNavigateBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(Res.string.back)
            )
        }
        Text(
            text = deviceName,
            style = MaterialTheme.typography.titleLarge
        )
        Box(modifier = Modifier.size(32.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToggleDeviceContent(
    state: DeviceState,
    onEvent: (DeviceEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DeviceHeader(state.device!!.name, onNavigateBack)
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
        if (state.uiConfig == CONTROL_TYPE_SLIDER || state.uiConfig == "ac") {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = if (state.device.properties.keys.toList().getOrNull(1) == KEY_BRIGHTNESS)
                            stringResource(Res.string.intensity)
                        else stringResource(Res.string.unknown)
                    )
                    Text(text = floatToPercentageString(state.sliderValue))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Slider(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.sliderValue,
                    onValueChange = { onEvent(DeviceEvent.OnSliderChanged(it)) },
                    onValueChangeFinished = { onEvent(DeviceEvent.OnSliderReleased(KEY_BRIGHTNESS)) },
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

@Composable
private fun SensorDeviceContent(
    state: DeviceState,
    onEvent: (DeviceEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val device = state.device!!
    val valueKey = device.properties.keys.firstOrNull { it != "state" && it != "feedback" }
    val value = device.properties[valueKey]
    val unit = device.uiConfig["unit"]?.toString() ?: ""
    val isSmokeSensor = valueKey == "smoke"

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DeviceHeader(device.name, onNavigateBack)

        Spacer(modifier = Modifier.height(16.dp))

        // Large centered value display
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isSmokeSensor) {
                val isDetected = value?.toString() == "detected"
                Text(
                    text = if (isDetected) stringResource(Res.string.smoke_detected)
                    else stringResource(Res.string.smoke_safe),
                    style = MaterialTheme.typography.displaySmall,
                    color = if (isDetected) Color.Red else GoodGreen,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = value?.toString() ?: "-",
                    style = MaterialTheme.typography.displayLarge,
                    color = BrokenWhite,
                    fontWeight = FontWeight.Bold
                )
                if (unit.isNotEmpty()) {
                    Text(
                        text = unit,
                        style = MaterialTheme.typography.titleLarge,
                        color = BrokenWhite.copy(alpha = 0.7f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Sensor reading history
        if (state.sensorReadings.isNotEmpty()) {
            Text(
                text = stringResource(Res.string.sensor_history),
                style = MaterialTheme.typography.titleMedium,
                color = BrokenWhite
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(state.sensorReadings) { reading ->
                    SensorReadingRow(reading, unit)
                }
            }
        } else {
            Text(
                text = stringResource(Res.string.no_readings),
                style = MaterialTheme.typography.bodyMedium,
                color = BrokenWhite.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
private fun SensorReadingRow(reading: SensorReading, unit: String) {
    Surface(
        color = Color.White.copy(alpha = 0.05f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = reading.createdAt,
                style = MaterialTheme.typography.bodySmall,
                color = BrokenWhite.copy(alpha = 0.6f)
            )
            Text(
                text = "${reading.value} $unit",
                style = MaterialTheme.typography.bodyMedium,
                color = BrokenWhite,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun GateDeviceContent(
    state: DeviceState,
    onEvent: (DeviceEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val device = state.device!!
    val currentState = device.properties["state"]?.toString() ?: "closed"
    val feedback = device.properties["feedback"]?.toString() ?: ""

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DeviceHeader(device.name, onNavigateBack)

        Spacer(modifier = Modifier.height(16.dp))

        // Feedback status display
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = when (feedback) {
                    "Gate_opened" -> stringResource(Res.string.gate_feedback_opened)
                    "Gate_closed" -> stringResource(Res.string.gate_feedback_closed)
                    else -> stringResource(Res.string.gate_feedback_unknown)
                },
                style = MaterialTheme.typography.headlineMedium,
                color = when (feedback) {
                    "Gate_opened" -> GoodGreen
                    "Gate_closed" -> BrokenWhite
                    else -> BrokenWhite.copy(alpha = 0.5f)
                },
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // BUKA / TUTUP buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // TUTUP button
            Button(
                onClick = { onEvent(DeviceEvent.OnDeviceToggle("state", "closed")) },
                modifier = Modifier.weight(1f).height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (currentState == "closed") PrimaryBlue else Color.DarkGray,
                    contentColor = if (currentState == "closed") DarkBlue else BrokenWhite
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(Res.string.tutup),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            // BUKA button
            Button(
                onClick = { onEvent(DeviceEvent.OnDeviceToggle("state", "open")) },
                modifier = Modifier.weight(1f).height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (currentState == "open") PrimaryBlue else Color.DarkGray,
                    contentColor = if (currentState == "open") DarkBlue else BrokenWhite
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(Res.string.buka),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PumpDeviceContent(
    state: DeviceState,
    onEvent: (DeviceEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val device = state.device!!
    val isOn = device.properties["state"] == "on"

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DeviceHeader(device.name, onNavigateBack)

        // Toggle ON/OFF
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isOn) stringResource(Res.string.pump_on)
                else stringResource(Res.string.pump_off),
                style = MaterialTheme.typography.titleMedium,
            )
            Switch(
                modifier = Modifier.height(16.dp),
                checked = isOn,
                onCheckedChange = {
                    val newState = if (isOn) "off" else "on"
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

        // Speed slider
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = stringResource(Res.string.pump_speed))
                Text(text = "${state.pumpSpeed}%")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = state.pumpSpeed / 100f,
                onValueChange = { onEvent(DeviceEvent.OnPumpSpeedChanged((it * 100).toInt())) },
                onValueChangeFinished = { onEvent(DeviceEvent.OnPumpSpeedReleased) },
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
                    deviceType = DeviceType(id = 1, name = "Gate"),
                    room = Room(id = 1, name = "Ruang Keluarga"),
                    home = Home(id = 1, name = "Rumah Keluarga Bahagia"),
                    properties = mapOf("state" to "closed", "feedback" to "Gate_closed"),
                    uiConfig = mapOf("control_type" to "gate", "icon" to GATE_ICON_KEY)
                ),
                uiConfig = "gate"
            ),
            onEvent = { },
            onNavigateBack = { },
            deviceId = 0
        )
    }
}
