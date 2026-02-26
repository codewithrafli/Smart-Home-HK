package id.co.hasilkarya.smarthome.home.domain.models

data class SensorReading(
    val id: Int,
    val deviceId: Int,
    val type: String,
    val value: Double,
    val createdAt: String
)
