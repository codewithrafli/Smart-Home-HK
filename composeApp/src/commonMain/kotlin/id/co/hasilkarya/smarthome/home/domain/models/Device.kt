package id.co.hasilkarya.smarthome.home.domain.models

data class Device(
    val id: Int,
    val name: String,
    val uniqueId: String,
    val deviceType: DeviceType,
    val room: Room,
    val home: Home,
    val gpio: String? = null,
    val properties: Map<String, Any?>,
    val uiConfig: Map<String, Any?>,
    val sort: Int = 0,
    val isFeatured: Boolean = false
)
