package id.co.hasilkarya.smarthome.home.presentation

import id.co.hasilkarya.smarthome.core.presentation.UiText
import id.co.hasilkarya.smarthome.home.domain.models.Device
import id.co.hasilkarya.smarthome.home.domain.models.HomeWithDevices
import id.co.hasilkarya.smarthome.home.domain.models.User

data class HomeState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val token: String = "",
    val message: UiText = UiText.DynamicString(""),
    val user: User? = null,
    val homes: List<HomeWithDevices> = emptyList(),
    val devices: List<Device> = emptyList(),
    val selectedHomeId: Int? = null,
    val selectedHomeName: String? = null,
)
