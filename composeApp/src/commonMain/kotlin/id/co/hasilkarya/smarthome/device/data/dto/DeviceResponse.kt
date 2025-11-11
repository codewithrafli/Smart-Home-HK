package id.co.hasilkarya.smarthome.device.data.dto

import id.co.hasilkarya.smarthome.home.data.dto.DataItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceResponse(

    @SerialName("data")
    val data: DataItem,

    @SerialName("success")
    val success: Boolean,

    @SerialName("message")
    val message: String
)
