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
import id.co.hasilkarya.smarthome.core.theme.SmartHomeTheme
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
                            else -> painterResource(Res.drawable.lamp_on)
                        },
                        contentDescription = device.properties["icon"].toString()
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