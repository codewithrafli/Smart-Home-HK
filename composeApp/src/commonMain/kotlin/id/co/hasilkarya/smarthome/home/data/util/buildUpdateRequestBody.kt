package id.co.hasilkarya.smarthome.home.data.util

import kotlinx.serialization.json.*

fun buildUpdateRequestBody(properties: Map<String, Any?>): JsonObject {
    val jsonProperties = properties.mapValues { (_, value) ->
        anyToJsonElement(value)
    }

    return buildJsonObject {
        put("properties", JsonObject(jsonProperties))
    }
}

private fun anyToJsonElement(value: Any?): JsonElement = when (value) {
    null -> JsonNull
    is String -> JsonPrimitive(value)
    is Number -> JsonPrimitive(value)
    is Boolean -> JsonPrimitive(value)
    is Map<*, *> -> JsonObject(value.entries.associate {
        it.key.toString() to anyToJsonElement(it.value)
    })
    is List<*> -> JsonArray(value.map { anyToJsonElement(it) })
    else -> JsonPrimitive(value.toString())
}
