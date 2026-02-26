package id.co.hasilkarya.smarthome.device.presentation

sealed interface DeviceEvent {
    data class OnLoadData(val id: Int) : DeviceEvent
    data class OnDeviceToggle(val property: String, val value: String) : DeviceEvent
    data class OnSliderChanged(val value: Float) : DeviceEvent
    data class OnSliderReleased(val property: String) : DeviceEvent
    data class OnPumpSpeedChanged(val speed: Int) : DeviceEvent
    data object OnPumpSpeedReleased : DeviceEvent
    data class OnLoadSensorHistory(val type: String) : DeviceEvent
}