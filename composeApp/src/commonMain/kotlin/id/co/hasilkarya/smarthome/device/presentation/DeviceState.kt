package id.co.hasilkarya.smarthome.device.presentation

import id.co.hasilkarya.smarthome.core.presentation.UiText
import id.co.hasilkarya.smarthome.home.domain.models.Device
import id.co.hasilkarya.smarthome.home.domain.models.SensorReading

data class DeviceState(
    val token: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val message: UiText = UiText.DynamicString( ""),
    val uiConfig: String = "",
    val sliderValue: Float = 0f,
    val pumpSpeed: Int = 0,
    val sensorReadings: List<SensorReading> = emptyList(),
    val device: Device? = null,
)
