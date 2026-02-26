package id.co.hasilkarya.smarthome.device.data.datasource

import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.device.data.dto.DeviceResponse
import id.co.hasilkarya.smarthome.home.data.dto.DeviceUpdateResponse
import id.co.hasilkarya.smarthome.home.data.dto.SensorReadingsResponse
import kotlinx.serialization.json.JsonObject

interface DeviceRemoteDataSource {

    suspend fun getDevice(token: String, id: Int): Result<DeviceResponse, DataError.Remote>

    suspend fun updateDevice(
        id: Int,
        token: String,
        request: JsonObject
    ): Result<DeviceUpdateResponse, DataError.Remote>

    suspend fun getLatestReadings(
        token: String,
        deviceId: Int
    ): Result<SensorReadingsResponse, DataError.Remote>

    suspend fun getReadingHistory(
        token: String,
        deviceId: Int,
        type: String,
        limit: Int = 100
    ): Result<SensorReadingsResponse, DataError.Remote>

}