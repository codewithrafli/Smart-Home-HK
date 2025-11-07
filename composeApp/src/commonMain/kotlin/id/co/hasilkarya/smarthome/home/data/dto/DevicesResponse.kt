package id.co.hasilkarya.smarthome.home.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class DevicesResponse(

	@SerialName("data")
	val data: List<DataItem>,

	@SerialName("success")
	val success: Boolean,

	@SerialName("message")
	val message: String
)