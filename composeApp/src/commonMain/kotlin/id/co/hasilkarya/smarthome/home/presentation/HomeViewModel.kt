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
            is HomeEvent.OnSelectHome -> selectHome(event.homeId, event.homeName)
            HomeEvent.OnBackToHomes -> backToHomes()
        }
    }

    private fun selectHome(homeId: Int, homeName: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, selectedHomeId = homeId, selectedHomeName = homeName) }
            repository.getDevicesByHome(state.value.token, homeId)
                .onSuccess { result ->
                    _state.update { it.copy(devices = result, isLoading = false) }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false, isError = true, message = error.asUiText()) }
                }
        }
    }

    private fun backToHomes() {
        _state.update { it.copy(selectedHomeId = null, selectedHomeName = null, devices = emptyList()) }
    }

    private fun updateDevice(device: Device, property: String, value: String) {
        val propertyMutable = device.properties.toMutableMap()
        propertyMutable[property] = value
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.updateDevice(id = device.id, token = state.value.token, request = propertyMutable)
                .onSuccess { _ ->
                    // Reload current view data
                    val homeId = state.value.selectedHomeId
                    if (homeId != null) {
                        repository.getDevicesByHome(state.value.token, homeId)
                            .onSuccess { result ->
                                _state.update { it.copy(devices = result, isLoading = false) }
                            }
                    } else {
                        loadData()
                    }
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
        repository.getHomes(state.value.token)
            .onSuccess { result ->
                _state.update { it.copy(homes = result, isLoading = false) }
            }
            .onError { error ->
                _state.update { it.copy(isLoading = false, isError = true, message = error.asUiText()) }
            }
    }

}