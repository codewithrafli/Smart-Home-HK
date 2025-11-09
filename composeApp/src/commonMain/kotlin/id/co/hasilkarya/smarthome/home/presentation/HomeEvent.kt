package id.co.hasilkarya.smarthome.home.presentation

import id.co.hasilkarya.smarthome.home.domain.models.Device

sealed interface HomeEvent {
    data object OnLoadData : HomeEvent
    data class OnDeviceToggle(val device: Device, val property: String, val value: String) : HomeEvent
}