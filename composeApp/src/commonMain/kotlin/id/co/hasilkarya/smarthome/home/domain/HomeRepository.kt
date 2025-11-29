package id.co.hasilkarya.smarthome.home.domain

import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.home.domain.models.Device
import id.co.hasilkarya.smarthome.home.domain.models.User
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getToken(): Flow<String>

    suspend fun getDevices(token: String): Result<List<Device>, DataError.Remote>

    suspend fun getUser(token: String): Result<User, DataError.Remote>

    suspend fun updateDevice(id: Int,token: String, request: Map<String, Any?>): Result<Boolean, DataError.Remote>

    suspend fun saveBiometricAuth(enabled: Boolean): Result<Boolean, DataError.Local>

    fun getBiometricAuthEnabled(): Flow<Boolean>

}