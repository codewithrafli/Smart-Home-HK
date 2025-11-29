package id.co.hasilkarya.smarthome.splash.presentation

data class SplashState(
    val token: String = "",
    val biometricAuthSuccess: Boolean = false,
    val biometricAuthEnabled: Boolean = false
)
