package id.co.hasilkarya.smarthome.device.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.hasilkarya.smarthome.core.network.utils.onError
import id.co.hasilkarya.smarthome.core.network.utils.onSuccess
import id.co.hasilkarya.smarthome.core.presentation.asUiText
import id.co.hasilkarya.smarthome.device.domain.DeviceRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DeviceViewModel(
    private val repository: DeviceRepository
) : ViewModel() {

    private val _token = repository.getToken()
    private val _state = MutableStateFlow(DeviceState())
    val state = combine(_token, _state) { token, state ->
        state.copy(token = token)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), DeviceState())

    fun onEvent(event: DeviceEvent) {
        when (event) {
            is DeviceEvent.OnLoadData -> loadData(event.id)
            is DeviceEvent.OnDeviceToggle -> toggleDevice(event.property, event.value)
            is DeviceEvent.OnSliderChanged -> updateSliderValue(event.value)
            is DeviceEvent.OnSliderReleased -> updateDeviceValue(event.property)
        }
    }

    private fun updateSliderValue(value: Float) {
        _state.update { it.copy(sliderValue = value) }
    }

    private fun updateDeviceValue(property: String) {
        _state.update { it.copy(isLoading = true) }
        val propertyMutable = state.value.device!!.properties.toMutableMap()
        propertyMutable[property] = state.value.sliderValue
        viewModelScope.launch {
            repository.updateDevice(state.value.device!!.id, state.value.token, propertyMutable)
                .onSuccess { _ ->
                    loadData(state.value.device!!.id)
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false, isError = true, message = error.asUiText()) }
                }
        }
    }

    private fun toggleDevice(property: String, value: String) {
        _state.update { it.copy(isLoading = true) }
        val propertyMutable = state.value.device!!.properties.toMutableMap()
        propertyMutable[property] = value
        viewModelScope.launch {
            repository.updateDevice(state.value.device!!.id, state.value.token, propertyMutable)
                .onSuccess { _ ->
                    loadData(state.value.device!!.id)
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false, isError = true, message = error.asUiText()) }
                }
        }
    }

    private fun loadData(id: Int) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.getDevice(id = id, token = state.value.token)
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            device = result,
                            isLoading = false,
                            uiConfig = result.uiConfig["control_type"].toString(),
                            sliderValue = if (result.properties.keys.toList().size == 2) {
                                result.properties[result.properties.keys.toList()[1]].toString()
                                    .toFloatOrNull() ?: 0f
                            } else 0f
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isError = true, message = error.asUiText(), isLoading = false) }
                }
        }
    }

}