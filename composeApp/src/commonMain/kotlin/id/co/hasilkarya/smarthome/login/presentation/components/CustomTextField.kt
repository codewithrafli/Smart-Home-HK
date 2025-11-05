package id.co.hasilkarya.smarthome.login.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.unit.dp
import id.co.hasilkarya.smarthome.core.theme.BrokenWhite
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smarthomehasilkarya.composeapp.generated.resources.Res
import smarthomehasilkarya.composeapp.generated.resources.clear_field
import smarthomehasilkarya.composeapp.generated.resources.email
import smarthomehasilkarya.composeapp.generated.resources.email_icon

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onNext: (() -> Unit),
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(8.dp),
        leadingIcon = leadingIcon,
        textStyle = TextStyle(
            color = BrokenWhite
        ),
        placeholder = {
            Text(hint, color = BrokenWhite.copy(0.85f))
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { onNext.invoke() }
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
        trailingIcon = {
            AnimatedVisibility(
                visible = value.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(
                    onClick = { onValueChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(Res.string.clear_field),
                        tint = BrokenWhite
                    )
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    CustomTextField(
        modifier = Modifier.padding(16.dp),
        value = "",
        onValueChange = { },
        hint = "Email",
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.email),
                contentDescription = stringResource(Res.string.email_icon),
                tint = BrokenWhite
            )
        },
        onNext = {  }
    )
}