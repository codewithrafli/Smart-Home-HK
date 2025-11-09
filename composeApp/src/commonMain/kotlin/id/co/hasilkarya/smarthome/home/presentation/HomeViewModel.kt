package id.co.hasilkarya.smarthome.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.hasilkarya.smarthome.core.network.utils.onError
import id.co.hasilkarya.smarthome.core.network.utils.onSuccess
import id.co.hasilkarya.smarthome.core.presentation.asUiText
import id.co.hasilkarya.smarthome.home.domain.HomeRepository
import id.co.hasilkarya.smarthome.home.domain.models.Device
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    private val _token = repository.getToken()
    private val _state = MutableStateFlow(HomeState())
    val state = combine(_token, _state) { token, state ->
        state.copy(token = token)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), HomeState())

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnLoadData -> loadData()
            is HomeEvent.OnDeviceToggle -> updateDevice(event.device, event.property, event.value)
        }
    }

    private fun updateDevice(device: Device, property: String, value: String) {
        val propertyMutable = device.properties.toMutableMap()
        propertyMutable[property] = value
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.updateDevice(id = device.id, token = state.value.token, request = propertyMutable)
                .onSuccess { _ ->
                    loadData()
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false, isError = true, message = error.asUiText()) }
                }
        }
    }

    private fun loadData() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        repository.getUser(state.value.token)
            .onSuccess { result ->
                _state.update { it.copy(user = result, isLoading = false) }
            }
            .onError { error ->
                _state.update { it.copy(isError = true, message = error.asUiText(), isLoading = false) }
            }
        repository.getDevices(state.value.token)
            .onSuccess { result ->
                _state.update { it.copy(devices = result, isLoading = false) }
            }
            .onError { error ->
                _state.update { it.copy(isLoading = false, isError = true, message = error.asUiText()) }
            }
    }

}