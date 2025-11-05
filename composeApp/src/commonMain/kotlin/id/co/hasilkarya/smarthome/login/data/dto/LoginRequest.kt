package id.co.hasilkarya.smarthome.login.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class LoginRequest(

	@SerialName("password")
	val password: String,

	@SerialName("email")
	val email: String
)