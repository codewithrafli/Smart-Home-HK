package id.co.hasilkarya.smarthome.profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPinCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.co.hasilkarya.smarthome.core.presentation.CustomLoadingNotification
import id.co.hasilkarya.smarthome.core.theme.BrokenWhite
import io.ktor.client.engine.callContext
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smarthomehasilkarya.composeapp.generated.resources.Res
import smarthomehasilkarya.composeapp.generated.resources.logout

@Composable
fun ProfileScreen(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    onNavigateBack: () -> Unit
) {

    LaunchedEffect(state.token) {
        if (state.token.isNotBlank())
            onEvent(ProfileEvent.OnLoadData)
    }

    LaunchedEffect(state.token, state.isSuccess) {
        if (state.isSuccess && state.token.isBlank()) {
            onNavigateBack()
        }
    }

    Box{
        if (state.isLoading) {
            CustomLoadingNotification()
        }
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = state.name,
                    modifier = Modifier.size(72.dp),
                    tint = BrokenWhite
                )
                Column{
                    Text(text = state.name, style = MaterialTheme.typography.titleMedium, color = BrokenWhite)
                    Text(text = state.email, style = MaterialTheme.typography.titleSmall, color = BrokenWhite)
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                onClick = {
                    onEvent(ProfileEvent.OnLogoutClick)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text(text = stringResource(Res.string.logout))
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        state = ProfileState(),
        onEvent = { },
        onNavigateBack = {  }
    )
}