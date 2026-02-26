package id.co.hasilkarya.smarthome.home.domain

import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.home.domain.models.Device
import id.co.hasilkarya.smarthome.home.domain.models.HomeWithDevices
import id.co.hasilkarya.smarthome.home.domain.models.User
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getToken(): Flow<String>

    suspend fun getDevices(token: String): Result<List<Device>, DataError.Remote>

    suspend fun getHomes(token: String): Result<List<HomeWithDevices>, DataError.Remote>

    suspend fun getDevicesByHome(token: String, homeId: Int): Result<List<Device>, DataError.Remote>

    suspend fun getUser(token: String): Result<User, DataError.Remote>

    suspend fun updateDevice(id: Int, token: String, request: Map<String, Any?>): Result<Boolean, DataError.Remote>

}