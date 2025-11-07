package id.co.hasilkarya.smarthome.home.data.dto

import id.co.hasilkarya.smarthome.home.domain.models.Device
import id.co.hasilkarya.smarthome.home.domain.models.DeviceType
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class DeviceTypeDto(

	@SerialName("updated_at")
	val updatedAt: String,

	@SerialName("name")
	val name: String,

	@SerialName("created_at")
	val createdAt: String,

	@SerialName("id")
	val id: Int
)

fun DeviceTypeDto.toDomain(): DeviceType = DeviceType(id = id, name = name)