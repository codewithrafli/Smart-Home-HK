package id.co.hasilkarya.smarthome.login.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import id.co.hasilkarya.smarthome.core.theme.BrokenWhite
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smarthomehasilkarya.composeapp.generated.resources.Res
import smarthomehasilkarya.composeapp.generated.resources.lock
import smarthomehasilkarya.composeapp.generated.resources.lock_icon
import smarthomehasilkarya.composeapp.generated.resources.password_not_visible
import smarthomehasilkarya.composeapp.generated.resources.password_visible

@Composable
fun SecureTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onVisibilityChange: (Boolean) -> Unit,
    hint: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    isVisible: Boolean,
    onDone: (() -> Unit),
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        leadingIcon = leadingIcon,
        shape = RoundedCornerShape(8.dp),
        placeholder = {
            Text(hint, color = BrokenWhite.copy(0.85f))
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone.invoke() }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BrokenWhite,
            unfocusedBorderColor = BrokenWhite.copy(0.11f),
            focusedLeadingIconColor = BrokenWhite,
            unfocusedLeadingIconColor = BrokenWhite.copy(0.85f),
            focusedTextColor = BrokenWhite,
            focusedTrailingIconColor = BrokenWhite,
            unfocusedTrailingIconColor = BrokenWhite.copy(0.85f),
        ),
        textStyle = TextStyle(
            color = BrokenWhite
        ),
        visualTransformation = if (isVisible)
            VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if (isVisible) {
                IconButton(
                    onClick = { onVisibilityChange(!isVisible) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = stringResource(Res.string.password_visible),
                    )
                }
            } else {
                IconButton(
                    onClick = { onVisibilityChange(!isVisible) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = stringResource(Res.string.password_not_visible),
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun SecureTextFieldPreview() {
    SecureTextField(
        value = "",
        onValueChange = { },
        onVisibilityChange = { },
        hint = "Password",
        isVisible = true,
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.lock),
                contentDescription = stringResource(Res.string.lock_icon),
            )
        },
        modifier = Modifier,
        onDone = {}
    )
}