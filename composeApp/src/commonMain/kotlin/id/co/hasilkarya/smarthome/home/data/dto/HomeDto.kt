package id.co.hasilkarya.smarthome.home.data.dto

import id.co.hasilkarya.smarthome.home.domain.models.Home
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class HomeDto(

	@SerialName("updated_at")
	val updatedAt: String,

	@SerialName("name")
	val name: String,

	@SerialName("created_at")
	val createdAt: String,

	@SerialName("id")
	val id: Int
)

fun HomeDto.toDomain() = Home(id = id, name = name)