package id.co.hasilkarya.smarthome.login.data.dto

import id.co.hasilkarya.smarthome.login.domain.model.UserWithToken
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Data(

	@SerialName("name")
	val name: String,

	@SerialName("id")
	val id: Int,

	@SerialName("email")
	val email: String,

	@SerialName("token")
	val token: String
) {
    fun toUserWithToken() = UserWithToken(id, name, token)
}