package id.co.hasilkarya.smarthome.profile.presentation

sealed interface ProfileEvent {
    data object OnLoadData: ProfileEvent
    data object OnLogoutClick: ProfileEvent
}