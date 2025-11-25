package id.co.hasilkarya.smarthome.splash.presentation

sealed interface SplashEvent {
    data class OnBiometricAuthSuccess(val success: Boolean): SplashEvent
}