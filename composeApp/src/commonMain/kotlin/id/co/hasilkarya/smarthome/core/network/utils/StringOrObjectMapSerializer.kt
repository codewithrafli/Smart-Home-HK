package id.co.hasilkarya.smarthome.core.network.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

object StringOrObjectMapSerializer : KSerializer<Map<String, JsonElement>> {

    private val mapSerializer = kotlinx.serialization.serializer<Map<String, JsonElement>>()
    override val descriptor: SerialDescriptor = mapSerializer.descriptor

    override fun serialize(encoder: Encoder, value: Map<String, JsonElement>) {
        encoder.encodeSerializableValue(mapSerializer, value)
    }

    override fun deserialize(decoder: Decoder): Map<String, JsonElement> {
        val jsonDecoder = decoder as? JsonDecoder ?: error("This serializer can only be used with Json")

        when (val element = jsonDecoder.decodeJsonElement()) {
            is JsonObject -> {
                return element.jsonObject.toMap()
            }
            is JsonPrimitive -> {
                if (element.content.isBlank()) {
                    return emptyMap()
                }
                val innerElement = Json.parseToJsonElement(element.content)
                if (innerElement is JsonObject) {
                    return innerElement.jsonObject.toMap()
                } else {
                    error("Expected JSON string to contain a valid JSON object")
                }
            }
            else -> {
                return emptyMap()
            }
        }
    }
}