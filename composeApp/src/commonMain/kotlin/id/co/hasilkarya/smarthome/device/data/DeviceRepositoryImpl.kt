package id.co.hasilkarya.smarthome.device.data

import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.core.preferences.AppPreferences
import id.co.hasilkarya.smarthome.device.data.datasource.DeviceRemoteDataSource
import id.co.hasilkarya.smarthome.device.domain.DeviceRepository
import id.co.hasilkarya.smarthome.home.data.dto.toDomain
import id.co.hasilkarya.smarthome.home.data.util.buildUpdateRequestBody
import id.co.hasilkarya.smarthome.home.domain.models.Device
import kotlinx.coroutines.flow.Flow

class DeviceRepositoryImpl(
    private val pref: AppPreferences,
    private val dataSource: DeviceRemoteDataSource
) : DeviceRepository {
    override fun getToken(): Flow<String> {
        return pref.getToken()
    }

    override suspend fun getDevice(id: Int, token: String): Result<Device, DataError.Remote> {
        return when (val result = dataSource.getDevice(token = token, id = id)) {
            is Result.Success -> Result.Success(result.data.data.toDomain())
            is Result.Error -> Result.Error(result.error)
        }
    }

    override suspend fun updateDevice(
        id: Int,
        token: String,
        request: Map<String, Any?>
    ): Result<Boolean, DataError.Remote> {
        val requestBody = buildUpdateRequestBody(request)
        return when (val result = dataSource.updateDevice(id, token, requestBody)) {
            is Result.Success -> Result.Success(true)
            is Result.Error -> Result.Error(result.error)
        }
    }
}