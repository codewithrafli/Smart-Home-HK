package id.co.hasilkarya.smarthome.home.data.dto

import id.co.hasilkarya.smarthome.home.domain.models.Home
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class HomeDto(

	@SerialName("updated_at")
	val updatedAt: String? = null,

	@SerialName("name")
	val name: String,

	@SerialName("created_at")
	val createdAt: String? = null,

	@SerialName("id")
	val id: Int,

	@SerialName("image")
	val image: String? = null
)

fun HomeDto.toDomain() = Home(id = id, name = name, image = image)