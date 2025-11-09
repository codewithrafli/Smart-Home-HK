package id.co.hasilkarya.smarthome.home.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GetUserResponse(

    @SerialName("data")
	val userDto: UserDto,

    @SerialName("success")
	val success: Boolean,

    @SerialName("message")
	val message: String
)