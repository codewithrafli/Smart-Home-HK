package id.co.hasilkarya.smarthome.home.data.dto

import id.co.hasilkarya.smarthome.home.domain.models.SensorReading
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SensorReadingDto(
    @SerialName("id") val id: Int,
    @SerialName("device_id") val deviceId: Int,
    @SerialName("type") val type: String,
    @SerialName("value") val value: Double,
    @SerialName("created_at") val createdAt: String
)

fun SensorReadingDto.toDomain() = SensorReading(
    id = id,
    deviceId = deviceId,
    type = type,
    value = value,
    createdAt = createdAt
)

@Serializable
data class SensorReadingsResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("message") val message: String,
    @SerialName("data") val data: List<SensorReadingDto>
)
