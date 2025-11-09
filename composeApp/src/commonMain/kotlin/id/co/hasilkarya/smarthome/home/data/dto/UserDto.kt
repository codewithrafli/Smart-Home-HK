package id.co.hasilkarya.smarthome.home.data.dto

import id.co.hasilkarya.smarthome.home.domain.models.User
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UserDto(

	@SerialName("name")
	val name: String,

	@SerialName("id")
	val id: Int,

	@SerialName("email")
	val email: String
)

fun UserDto.toDomain() = User(id = id, name = name, email = email)