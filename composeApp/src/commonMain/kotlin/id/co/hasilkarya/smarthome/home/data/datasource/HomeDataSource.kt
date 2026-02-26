package id.co.hasilkarya.smarthome.home.data.datasource

import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.home.data.dto.DeviceUpdateResponse
import id.co.hasilkarya.smarthome.home.data.dto.DevicesResponse
import id.co.hasilkarya.smarthome.home.data.dto.GetUserResponse
import id.co.hasilkarya.smarthome.home.data.dto.HomesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject

interface HomeDataSource {

    fun getToken(): Flow<String>

    suspend fun getDevices(token: String): Result<DevicesResponse, DataError.Remote>

    suspend fun getHomes(token: String): Result<HomesResponse, DataError.Remote>

    suspend fun getDevicesByHome(token: String, homeId: Int): Result<DevicesResponse, DataError.Remote>

    suspend fun getUser(token: String): Result<GetUserResponse, DataError.Remote>

    suspend fun updateDevice(
        id: Int,
        token: String,
        request: JsonObject
    ): Result<DeviceUpdateResponse, DataError.Remote>

}