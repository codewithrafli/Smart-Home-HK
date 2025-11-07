package id.co.hasilkarya.smarthome.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import id.co.hasilkarya.smarthome.core.theme.BrokenWhite
import id.co.hasilkarya.smarthome.core.theme.DarkBlue
import id.co.hasilkarya.smarthome.core.theme.SmartHomeTheme
import id.co.hasilkarya.smarthome.home.domain.models.Device
import id.co.hasilkarya.smarthome.home.domain.models.DeviceType
import id.co.hasilkarya.smarthome.home.domain.models.Home
import id.co.hasilkarya.smarthome.home.domain.models.Room
import id.co.hasilkarya.smarthome.home.presentation.components.DeviceCard
import id.co.hasilkarya.smarthome.home.presentation.components.LAMP_ICON_KEY
import id.co.hasilkarya.smarthome.home.presentation.components.STATE_OFF_KEY
import id.co.hasilkarya.smarthome.home.presentation.components.STATE_ON_KEY
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smarthomehasilkarya.composeapp.generated.resources.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun HomeScreen() {
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
                        text = "Sendiko",
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
        LazyVerticalStaggeredGrid(
            contentPadding = PaddingValues(
                top = it.calculateTopPadding(),
                start = 16.dp,
                end = 16.dp,
            ),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item(
                span = StaggeredGridItemSpan.FullLine
            ) {
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
            item {
                DeviceCard(
                    device = Device(
                        id = 1,
                        name = "Gerbang Utama",
                        uniqueId = "32414234",
                        deviceType = DeviceType(id = 1, name = "Lampu"),
                        room = Room(id = 1, name = "Ruang Keluarga"),
                        home = Home(id = 1, name = "Rumah Keluarga Bahagia"),
                        properties = mapOf("state" to STATE_ON_KEY),
                        uiConfig = mapOf("icon" to LAMP_ICON_KEY)
                    )
                )
            }
            item {
                DeviceCard(
                    device = Device(
                        id = 1,
                        name = "Gerbang Utama",
                        uniqueId = "32414234",
                        deviceType = DeviceType(id = 1, name = "Lampu"),
                        room = Room(id = 1, name = "Ruang Keluarga"),
                        home = Home(id = 1, name = "Rumah Keluarga Bahagia"),
                        properties = mapOf("state" to STATE_OFF_KEY),
                        uiConfig = mapOf("icon" to LAMP_ICON_KEY)
                    )
                )
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
        HomeScreen()
    }
}