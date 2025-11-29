package id.co.hasilkarya.smarthome.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.hasilkarya.smarthome.core.network.utils.onError
import id.co.hasilkarya.smarthome.core.network.utils.onSuccess
import id.co.hasilkarya.smarthome.core.presentation.asUiText
import id.co.hasilkarya.smarthome.home.domain.HomeRepository
import id.co.hasilkarya.smarthome.login.domain.LoginRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: HomeRepository,
    private val loginRepository: LoginRepository,
) : ViewModel() {

    private val _token = repository.getToken()
    private val _biometricAuthEnabled = repository.getBiometricAuthEnabled()
    private val _state = MutableStateFlow(ProfileState())
    val state = combine(_token, _biometricAuthEnabled, _state) { token, biometricAuthEnabled, state ->
        state.copy(token = token, biometricAuthEnabled = biometricAuthEnabled)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ProfileState())

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.OnLoadData -> loadData()
            ProfileEvent.OnLogoutClick -> logout()
            is ProfileEvent.OnBiometricAuthClick -> changeBiometricAuth(event.isEnabled)
        }
    }

    private fun changeBiometricAuth(isEnabled: Boolean) = viewModelScope.launch() {
        repository.saveBiometricAuth(isEnabled)
        _state.update {
            it.copy(biometricAuthEnabled = isEnabled)
        }
    }

    private fun logout() {
        viewModelScope.launch {
            loginRepository.saveToken("")
            _state.update { it.copy(isSuccess = true, token = "") }
        }
    }

    private fun loadData() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.getUser(state.value.token)
                .onSuccess { result ->
                    _state.update {
                        it.copy(isLoading = false, name = result.name, email = result.email)
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false, isError = true, message = error.asUiText()) }
                }
        }
    }

}
