package id.co.hasilkarya.smarthome.profile.presentation

sealed interface ProfileEvent {
    data object OnLoadData: ProfileEvent
    data class OnBiometricAuthClick(val isEnabled: Boolean): ProfileEvent
    data object OnLogoutClick: ProfileEvent
}