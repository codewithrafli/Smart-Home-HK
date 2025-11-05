package id.co.hasilkarya.smarthome.login.presentation

import id.co.hasilkarya.smarthome.core.presentation.UiText

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val message: UiText = UiText.DynamicString(""),
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
)
