package id.co.hasilkarya.smarthome.home.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class HomesResponse(
    @SerialName("data")
    val data: List<HomeWithDevicesDto>,

    @SerialName("success")
    val success: Boolean,

    @SerialName("message")
    val message: String
)

@Serializable
data class HomeWithDevicesDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("image")
    val image: String? = null,

    @SerialName("role")
    val role: String? = null,

    @SerialName("featured_devices")
    val featuredDevices: List<DataItem> = emptyList()
)
