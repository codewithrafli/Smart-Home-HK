package id.co.hasilkarya.smarthome.home.data.datasource

import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.home.data.dto.DevicesResponse
import id.co.hasilkarya.smarthome.home.data.dto.GetUserResponse
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {

    fun getToken(): Flow<String>

    suspend fun getDevices(token: String): Result<DevicesResponse, DataError.Remote>

    suspend fun getUser(token: String): Result<GetUserResponse, DataError.Remote>

}