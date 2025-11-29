package id.co.hasilkarya.smarthome.profile.presentation

import id.co.hasilkarya.smarthome.core.presentation.UiText

data class ProfileState(
    val token: String = "",
    val name: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val message: UiText = UiText.DynamicString(""),
    val biometricAuthEnabled: Boolean = false
)
