package id.co.hasilkarya.smarthome.home.data.dto

import id.co.hasilkarya.smarthome.home.domain.models.Room
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class RoomDto(

	@SerialName("updated_at")
	val updatedAt: String,

	@SerialName("name")
	val name: String,

	@SerialName("created_at")
	val createdAt: String,

	@SerialName("id")
	val id: Int
)

fun RoomDto.toDomain(): Room = Room(id = id, name = name)