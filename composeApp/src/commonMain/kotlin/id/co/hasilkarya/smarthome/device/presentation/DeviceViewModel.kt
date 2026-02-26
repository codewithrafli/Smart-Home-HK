package id.co.hasilkarya.smarthome.device.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.hasilkarya.smarthome.core.network.utils.onError
import id.co.hasilkarya.smarthome.core.network.utils.onSuccess
import id.co.hasilkarya.smarthome.core.presentation.asUiText
import id.co.hasilkarya.smarthome.device.domain.DeviceRepository
import id.co.hasilkarya.smarthome.device.presentation.utils.DeviceCategory
import id.co.hasilkarya.smarthome.device.presentation.utils.classifyDevice
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DeviceViewModel(
    private val repository: DeviceRepository
) : ViewModel() {

    companion object {
        private const val POLL_INTERVAL = 5000L
    }

    private var pollingJob: Job? = null
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
            is DeviceEvent.OnPumpSpeedChanged -> updatePumpSpeed(event.speed)
            is DeviceEvent.OnPumpSpeedReleased -> commitPumpSpeed()
            is DeviceEvent.OnLoadSensorHistory -> loadSensorHistory(event.type)
        }
    }

    private fun updateSliderValue(value: Float) {
        _state.update { it.copy(sliderValue = value) }
    }

    private fun updatePumpSpeed(speed: Int) {
        _state.update { it.copy(pumpSpeed = speed) }
    }

    private fun commitPumpSpeed() {
        _state.update { it.copy(isLoading = true) }
        val propertyMutable = state.value.device!!.properties.toMutableMap()
        propertyMutable["speed"] = state.value.pumpSpeed
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

    private fun loadSensorHistory(type: String) {
        viewModelScope.launch {
            repository.getReadingHistory(state.value.token, state.value.device!!.id, type)
                .onSuccess { readings ->
                    _state.update { it.copy(sensorReadings = readings) }
                }
                .onError { /* Sensor history is optional, don't show error */ }
        }
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
                    val controlType = result.uiConfig["control_type"]?.toString() ?: ""
                    val category = classifyDevice(controlType)

                    _state.update {
                        it.copy(
                            device = result,
                            isLoading = false,
                            uiConfig = controlType,
                            sliderValue = if (result.properties.keys.toList().size == 2) {
                                result.properties[result.properties.keys.toList()[1]].toString()
                                    .toFloatOrNull() ?: 0f
                            } else 0f,
                            pumpSpeed = if (category == DeviceCategory.PUMP) {
                                (result.properties["speed"] as? Number)?.toInt() ?: 0
                            } else 0
                        )
                    }

                    // Load sensor history for sensor devices
                    if (category == DeviceCategory.SENSOR) {
                        val valueKey = result.properties.keys.firstOrNull { it != "state" && it != "feedback" }
                        if (valueKey != null) {
                            loadSensorHistory(valueKey)
                        }
                    }

                    startPolling(id)
                }
                .onError { error ->
                    _state.update { it.copy(isError = true, message = error.asUiText(), isLoading = false) }
                }
        }
    }

    private fun startPolling(id: Int) {
        pollingJob?.cancel()
        pollingJob = viewModelScope.launch {
            while (true) {
                delay(POLL_INTERVAL)
                refreshData(id)
            }
        }
    }

    private suspend fun refreshData(id: Int) {
        repository.getDevice(id = id, token = state.value.token)
            .onSuccess { result ->
                val controlType = result.uiConfig["control_type"]?.toString() ?: ""
                val category = classifyDevice(controlType)

                _state.update {
                    it.copy(
                        device = result,
                        uiConfig = controlType,
                        sliderValue = if (result.properties.keys.toList().size == 2) {
                            result.properties[result.properties.keys.toList()[1]].toString()
                                .toFloatOrNull() ?: 0f
                        } else 0f,
                        pumpSpeed = if (category == DeviceCategory.PUMP) {
                            (result.properties["speed"] as? Number)?.toInt() ?: 0
                        } else 0
                    )
                }

                if (category == DeviceCategory.SENSOR) {
                    val valueKey = result.properties.keys.firstOrNull { it != "state" && it != "feedback" }
                    if (valueKey != null) {
                        loadSensorHistory(valueKey)
                    }
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        pollingJob?.cancel()
    }

}
