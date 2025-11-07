package id.co.hasilkarya.smarthome.home.presentation

sealed interface HomeEvent {
    data object OnLoadData : HomeEvent
    data class OnDeviceToggle(val deviceId: Int) : HomeEvent
}