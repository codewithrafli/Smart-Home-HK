package id.co.hasilkarya.smarthome.home.data.dto

import id.co.hasilkarya.smarthome.core.network.utils.StringOrObjectMapSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.longOrNull

@Serializable
data class DataItem(

    @SerialName("unique_id")
	val uniqueId: String,

    @SerialName("updated_at")
	val updatedAt: String,

    @SerialName("name")
	val name: String,

    @SerialName("created_at")
	val createdAt: String,

    @SerialName("device_type")
	val deviceTypeDto: DeviceTypeDto,

    @SerialName("id")
	val id: Int,

    @Serializable(with = StringOrObjectMapSerializer::class)
    @SerialName("ui_config")
	val uiConfig: Map<String, JsonElement>,

    @SerialName("room")
	val roomDto: RoomDto,

    @Serializable(with = StringOrObjectMapSerializer::class)
	@SerialName("properties")
	val properties: Map<String, JsonElement>,

    @SerialName("home")
	val homeDto: HomeDto,

    @SerialName("gpio")
	val gpio: String? = null
)

private fun Map<String, JsonElement>.toDomainMap(): Map<String, Any?> {
    return this.mapValues { (_, value) -> value.toDomainValue() }
}

private fun JsonElement.toDomainValue(): Any? {
    return when (this) {
        is JsonNull -> null
        is JsonPrimitive -> this.toPrimitiveValue()
        is JsonObject -> this.toDomainMap()
        is JsonArray -> this.map { it.toDomainValue() }
    }
}

private fun JsonPrimitive.toPrimitiveValue(): Any {
    return this.booleanOrNull ?:
    this.longOrNull ?:
    this.doubleOrNull ?:
    this.content
}