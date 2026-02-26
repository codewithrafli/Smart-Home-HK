package id.co.hasilkarya.smarthome.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import id.co.hasilkarya.smarthome.core.network.utils.BASE_URL
import id.co.hasilkarya.smarthome.core.theme.BrokenWhite
import id.co.hasilkarya.smarthome.core.theme.DarkBlue
import id.co.hasilkarya.smarthome.home.domain.models.Device
import id.co.hasilkarya.smarthome.home.domain.models.HomeWithDevices

@Composable
fun HomeCard(
    modifier: Modifier = Modifier,
    homeWithDevices: HomeWithDevices,
    onToggle: (device: Device, property: String, value: String) -> Unit,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f)),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp)
        ) {
            // Background Image
            if (homeWithDevices.image != null) {
                AsyncImage(
                    model = homeWithDevices.image,
                    contentDescription = homeWithDevices.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            } else {
                 // Placeholder Gradient if no image
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    DarkBlue.copy(alpha = 0.6f),
                                    Color.Black.copy(alpha = 0.8f)
                                )
                            )
                        )
                )
            }

            // Gradient overlay for text readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.8f)
                            ),
                            startY = 100f
                        )
                    )
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Home name badge (Top)
                Surface(
                    color = Color.Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = homeWithDevices.name,
                        color = BrokenWhite,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Minimum spacing
                Spacer(modifier = Modifier.height(24.dp))
                
                // Push content to bottom
                Spacer(modifier = Modifier.weight(1f))

                // Featured devices at the bottom
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    homeWithDevices.featuredDevices.forEach { device ->
                        FeaturedDeviceRow(
                            device = device,
                            onToggle = onToggle
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FeaturedDeviceRow(
    device: Device,
    onToggle: (device: Device, property: String, value: String) -> Unit
) {
    Surface(
        color = Color.Black.copy(alpha = 0.6f), // Darker overlay for better visibility
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
                text = device.name,
                color = BrokenWhite,
                fontSize = 14.sp
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // State buttons
                val currentState = device.properties["state"]?.toString() ?: "off"
                val isOnOrOpen = currentState == STATE_ON_KEY || currentState == STATE_OPEN_KEY

                // OFF / CLOSE Button (Left)
                OutlinedButton(
                    onClick = {
                        val newState = if (currentState == STATE_OPEN_KEY || currentState == "open") STATE_CLOSED_KEY else STATE_OFF_KEY
                        onToggle(device, "state", newState)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (!isOnOrOpen) BrokenWhite else Color.Transparent,
                        contentColor = if (!isOnOrOpen) DarkBlue else BrokenWhite
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                    modifier = Modifier.height(28.dp)
                ) {
                    Text(
                        text = if (currentState.contains("open", ignoreCase = true) || currentState.contains("close", ignoreCase = true)) "TUTUP" else "OFF",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // ON / OPEN Button (Right)
                OutlinedButton(
                    onClick = {
                        val newState = if (currentState == STATE_CLOSED_KEY || currentState == "close") STATE_OPEN_KEY else STATE_ON_KEY
                        onToggle(device, "state", newState)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isOnOrOpen) BrokenWhite else Color.Transparent,
                        contentColor = if (isOnOrOpen) DarkBlue else BrokenWhite
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                    modifier = Modifier.height(28.dp)
                ) {
                    Text(
                        text = if (currentState.contains("open", ignoreCase = true) || currentState.contains("close", ignoreCase = true)) "BUKA" else "ON",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
