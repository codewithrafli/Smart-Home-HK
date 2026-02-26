package id.co.hasilkarya.smarthome.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.co.hasilkarya.smarthome.core.theme.BrokenWhite
import id.co.hasilkarya.smarthome.core.theme.DarkBlue
import id.co.hasilkarya.smarthome.core.theme.GoodGreen
import id.co.hasilkarya.smarthome.core.theme.SmartHomeTheme
import id.co.hasilkarya.smarthome.device.presentation.utils.DeviceCategory
import id.co.hasilkarya.smarthome.device.presentation.utils.classifyDevice
import id.co.hasilkarya.smarthome.home.domain.models.Device
import id.co.hasilkarya.smarthome.home.domain.models.DeviceType
import id.co.hasilkarya.smarthome.home.domain.models.Home
import id.co.hasilkarya.smarthome.home.domain.models.Room
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smarthomehasilkarya.composeapp.generated.resources.*

const val LAMP_ICON_KEY = "fa-lightbulb"
const val THERMOMETER_ICON_KEY = "fa-thermometer"
const val GATE_ICON_KEY = "fa-warehouse"
const val DROPLET_ICON_KEY = "fa-droplet"
const val SMOKE_ICON_KEY = "fa-smoke"
const val WIND_ICON_KEY = "fa-wind"
const val FIRE_ICON_KEY = "fa-fire"
const val SEEDLING_ICON_KEY = "fa-seedling"
const val FAUCET_ICON_KEY = "fa-faucet-drip"
const val SNOWFLAKE_ICON_KEY = "fa-snowflake"
const val STATE_ON_KEY = "on"
const val STATE_OPEN_KEY = "open"
const val STATE_OFF_KEY = "off"
const val STATE_CLOSED_KEY = "closed"

@Composable
fun DeviceCard(
    modifier: Modifier = Modifier,
    device: Device,
    onToggle: (device: Device, property: String, value: String) -> Unit,
    onClick: () -> Unit,
) {
    val controlType = device.uiConfig["control_type"]?.toString()
    val category = classifyDevice(controlType)

    Card(
        modifier = modifier.fillMaxHeight(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(0.09f),
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxHeight().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    color = Color.White.copy(0.08f),
                    shape = CircleShape
                ) {
                    Image(
                        modifier = Modifier.padding(8.dp).size(24.dp),
                        painter = when (device.uiConfig["icon"]) {
                            LAMP_ICON_KEY -> painterResource(Res.drawable.lamp_on)
                            GATE_ICON_KEY -> painterResource(Res.drawable.barricade)
                            THERMOMETER_ICON_KEY -> painterResource(Res.drawable.thermometer)
                            DROPLET_ICON_KEY -> painterResource(Res.drawable.droplet)
                            SMOKE_ICON_KEY -> painterResource(Res.drawable.smoke)
                            WIND_ICON_KEY -> painterResource(Res.drawable.wind)
                            FIRE_ICON_KEY -> painterResource(Res.drawable.fire)
                            SEEDLING_ICON_KEY -> painterResource(Res.drawable.seedling)
                            FAUCET_ICON_KEY -> painterResource(Res.drawable.faucet)
                            SNOWFLAKE_ICON_KEY -> painterResource(Res.drawable.fan)
                            else -> painterResource(Res.drawable.lamp_on)
                        },
                        contentDescription = device.name
                    )
                }
                Surface(
                    color = Color.White.copy(0.08f),
                    shape = CircleShape
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = device.room.name,
                        fontSize = 10.sp,
                        color = BrokenWhite
                    )
                }
            }
            Text(
                text = device.name,
                style = MaterialTheme.typography.titleMedium,
                color = BrokenWhite,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                when (category) {
                    DeviceCategory.SENSOR -> {
                        // Show sensor value with unit
                        val valueKey = device.properties.keys.firstOrNull { it != "state" && it != "feedback" }
                        val value = device.properties[valueKey]
                        val unit = device.uiConfig["unit"]?.toString() ?: ""

                        if (valueKey == "smoke") {
                            val isDetected = value?.toString() == "detected"
                            Text(
                                text = if (isDetected) stringResource(Res.string.smoke_detected)
                                else stringResource(Res.string.smoke_safe),
                                style = MaterialTheme.typography.titleMedium,
                                color = if (isDetected) Color.Red else GoodGreen
                            )
                        } else {
                            Text(
                                text = "$value $unit",
                                style = MaterialTheme.typography.titleMedium,
                                color = BrokenWhite
                            )
                        }
                    }
                    DeviceCategory.GATE -> {
                        Text(
                            text = when (device.properties["state"]) {
                                STATE_OPEN_KEY -> stringResource(Res.string.buka)
                                STATE_CLOSED_KEY -> stringResource(Res.string.tutup)
                                else -> stringResource(Res.string.unknown)
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = BrokenWhite
                        )
                        Switch(
                            modifier = Modifier.height(16.dp),
                            checked = device.properties["state"] == STATE_OPEN_KEY,
                            onCheckedChange = {
                                val newState = if (device.properties["state"] == STATE_OPEN_KEY) STATE_CLOSED_KEY else STATE_OPEN_KEY
                                onToggle(device, "state", newState)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = DarkBlue,
                                checkedTrackColor = BrokenWhite,
                                uncheckedThumbColor = DarkBlue,
                                uncheckedTrackColor = BrokenWhite,
                            ),
                        )
                    }
                    else -> {
                        Text(
                            text = when (device.properties["state"]) {
                                STATE_ON_KEY -> stringResource(Res.string.on)
                                STATE_OFF_KEY -> stringResource(Res.string.off)
                                STATE_OPEN_KEY -> stringResource(Res.string.open)
                                STATE_CLOSED_KEY -> stringResource(Res.string.closed)
                                else -> stringResource(Res.string.unknown)
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = BrokenWhite
                        )
                        Switch(
                            modifier = Modifier.height(16.dp),
                            checked = device.properties["state"] == STATE_ON_KEY || device.properties["state"] == STATE_OPEN_KEY,
                            onCheckedChange = {
                                val newState = when (device.properties["state"]) {
                                    STATE_ON_KEY -> STATE_OFF_KEY
                                    STATE_OFF_KEY -> STATE_ON_KEY
                                    STATE_OPEN_KEY -> STATE_CLOSED_KEY
                                    STATE_CLOSED_KEY -> STATE_OPEN_KEY
                                    else -> STATE_OFF_KEY
                                }
                                onToggle(device, "state", newState)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = DarkBlue,
                                checkedTrackColor = BrokenWhite,
                                uncheckedThumbColor = DarkBlue,
                                uncheckedTrackColor = BrokenWhite,
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DeviceCardPreview() {
    SmartHomeTheme {
        DeviceCard(
            modifier = Modifier.padding(16.dp),
            device = Device(
                id = 1,
                name = "Gerbang Utama",
                uniqueId = "32414234",
                deviceType = DeviceType(id = 1, name = "Lampu"),
                room = Room(id = 1, name = "Ruang Keluarga"),
                home = Home(id = 1, name = "Rumah Keluarga Bahagia"),
                properties = mapOf("state" to STATE_OFF_KEY),
                uiConfig = mapOf("icon" to LAMP_ICON_KEY)
            ),
            onToggle = { _, _, _ -> },
            onClick = {  }
        )
    }
}
