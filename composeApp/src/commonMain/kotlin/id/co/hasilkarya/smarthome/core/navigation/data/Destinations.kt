package id.co.hasilkarya.smarthome.core.navigation.data

import kotlinx.serialization.Serializable

@Serializable
data object SplashDestination

@Serializable
data object LoginDestination

@Serializable
data object MainGraph

@Serializable
data object HomeDestination

@Serializable
data object HistoryDestination

@Serializable
data class DeviceDestination(val deviceId: Int)

@Serializable
data object AutomationDestination

@Serializable
data object ProfileDestination