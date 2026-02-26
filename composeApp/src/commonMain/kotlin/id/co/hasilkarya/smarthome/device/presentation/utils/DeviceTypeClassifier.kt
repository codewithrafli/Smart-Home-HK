package id.co.hasilkarya.smarthome.device.presentation.utils

enum class DeviceCategory {
    TOGGLE,
    TOGGLE_SLIDER,
    GATE,
    PUMP,
    SENSOR,
    UNKNOWN
}

fun classifyDevice(controlType: String?): DeviceCategory {
    return when (controlType) {
        "read_only" -> DeviceCategory.SENSOR
        "gate" -> DeviceCategory.GATE
        "pump" -> DeviceCategory.PUMP
        "slider" -> DeviceCategory.TOGGLE_SLIDER
        "toggle" -> DeviceCategory.TOGGLE
        "ac" -> DeviceCategory.TOGGLE_SLIDER
        else -> DeviceCategory.UNKNOWN
    }
}
