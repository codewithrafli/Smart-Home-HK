package id.co.hasilkarya.smarthome.core.navigation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.co.hasilkarya.smarthome.core.navigation.data.mainNavigations
import id.co.hasilkarya.smarthome.core.theme.SmartHomeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Navbar(
    navController: NavController,
) {
    val selectedIndex = rememberSaveable {
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier.padding(16.dp)
            .wrapContentHeight(),
        color = Color.Black.copy(0.5f),
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            mainNavigations.forEachIndexed { index, data ->
                NavbarItem(
                    data = data,
                    onNavigate = {
                        selectedIndex.value = index
                        navController.navigate(data.route)
                    },
                    isSelected = selectedIndex.value == index
                )
            }
        }
    }
}

@Preview
@Composable
fun NavbarPreview() {
    SmartHomeTheme {
        Navbar(rememberNavController())
    }
}