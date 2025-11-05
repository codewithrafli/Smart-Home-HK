package id.co.hasilkarya.smarthome.core.utils

import platform.Foundation.NSLog

actual fun log(message: String) {
    NSLog("Ktor: %@", message)
}