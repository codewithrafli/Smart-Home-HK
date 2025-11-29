package id.co.hasilkarya.smarthome.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.hasilkarya.smarthome.splash.domain.SplashRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SplashViewModel(
    repository: SplashRepository
) : ViewModel() {

    private val _token = repository.getToken()
    private val _biometricAuthEnabled = repository.getBiometricAuthEnabled()
    private val _state = MutableStateFlow(SplashState())
    val state = combine(_token, _biometricAuthEnabled, _state) { token, biometricAuthEnabled, state ->
        state.copy(token = token, biometricAuthEnabled = biometricAuthEnabled)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SplashState())

    fun onEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.OnBiometricAuthSuccess -> _state.update { it.copy(biometricAuthSuccess = true) }
        }
    }

}