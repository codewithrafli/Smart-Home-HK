package id.co.hasilkarya.smarthome.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.hasilkarya.smarthome.core.network.utils.onError
import id.co.hasilkarya.smarthome.core.network.utils.onSuccess
import id.co.hasilkarya.smarthome.core.presentation.UiText
import id.co.hasilkarya.smarthome.core.presentation.asUiText
import id.co.hasilkarya.smarthome.login.domain.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import smarthomehasilkarya.composeapp.generated.resources.Res
import smarthomehasilkarya.composeapp.generated.resources.email_blank
import smarthomehasilkarya.composeapp.generated.resources.email_invalid
import smarthomehasilkarya.composeapp.generated.resources.login_success
import smarthomehasilkarya.composeapp.generated.resources.password_blank

class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> changeEmail(event.email)
            is LoginEvent.OnPasswordChanged -> changePassword(event.password)
            is LoginEvent.OnPasswordVisibilityChanged -> changePasswordVisibility(event.isVisible)
            LoginEvent.OnLoginClick -> login()
        }
    }

    private fun login() {
        viewModelScope.launch {
            if (state.value.email.isBlank()) {
                _state.update {
                    it.copy(message = UiText.DynamicString(getString(Res.string.email_blank)))
                }
                return@launch
            }
            if (!state.value.email.contains("@")) {
                _state.update {
                    it.copy(message = UiText.DynamicString(getString(Res.string.email_invalid)))
                }
                return@launch
            }
            if (state.value.password.isBlank()) {
                _state.update {
                    it.copy(message = UiText.DynamicString(getString(Res.string.password_blank)))
                }
                return@launch
            }
            _state.update { it.copy(isLoading = true) }
            repository.login(
                email = state.value.email,
                password = state.value.password
            ).onSuccess { _ ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isError = false,
                        message = UiText.DynamicString(getString(Res.string.login_success))
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        message = error.asUiText()
                    )
                }
            }
        }
    }

    private fun changePasswordVisibility(isVisible: Boolean) {
        _state.value = _state.value.copy(
            isPasswordVisible = isVisible
        )
    }

    private fun changePassword(password: String) {
        _state.value = _state.value.copy(
            password = password
        )
    }

    fun changeEmail(email: String) {
        _state.value = _state.value.copy(
            email = email
        )
    }

}