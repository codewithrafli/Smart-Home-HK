package id.co.hasilkarya.smarthome.device.presentation.utils

import kotlin.math.roundToInt

fun floatToPercentage(value: Float): Float {
    val clampedValue = value.coerceIn(0.0f, 1.0f)
    return clampedValue * 100.0f
}

fun floatToPercentageInt(value: Float): Int {
    return floatToPercentage(value).roundToInt()
}

fun floatToPercentageString(value: Float, decimalPlaces: Int = 0): String {
    if (decimalPlaces <= 0) {
        return "${floatToPercentageInt(value)}%"
    }

    val percentage = floatToPercentage(value).toDouble()

    var multiplier = 1.0
    repeat(decimalPlaces) { multiplier *= 10.0 }

    val roundedPercentage = (percentage * multiplier).roundToInt() / multiplier

    var resultString = roundedPercentage.toString()

    val decimalPointIndex = resultString.indexOf('.')

    if (decimalPointIndex == -1) {
        resultString += "."
        repeat(decimalPlaces) { resultString += "0" }
    } else {
        val currentDecimalPlaces = resultString.length - decimalPointIndex - 1
        val zerosToPad = decimalPlaces - currentDecimalPlaces
        if (zerosToPad > 0) {
            repeat(zerosToPad) { resultString += "0" }
        }
    }

    return "$resultString%"
}