package id.co.hasilkarya.smarthome.core.utils

import android.util.Log

actual fun log(message: String) {
    Log.d("Ktor", message)
}