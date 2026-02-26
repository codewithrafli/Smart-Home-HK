package id.co.hasilkarya.smarthome.device.domain

import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.home.domain.models.Device
import id.co.hasilkarya.smarthome.home.domain.models.SensorReading
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {

    fun getToken(): Flow<String>

    suspend fun getDevice(id: Int, token: String): Result<Device, DataError.Remote>

    suspend fun updateDevice(id: Int, token: String, request: Map<String, Any?>): Result<Boolean, DataError.Remote>

    suspend fun getLatestReadings(token: String, deviceId: Int): Result<List<SensorReading>, DataError.Remote>

    suspend fun getReadingHistory(token: String, deviceId: Int, type: String, limit: Int = 100): Result<List<SensorReading>, DataError.Remote>

}