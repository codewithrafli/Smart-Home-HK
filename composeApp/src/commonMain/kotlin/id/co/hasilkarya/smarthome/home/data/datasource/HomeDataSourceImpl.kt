package id.co.hasilkarya.smarthome.home.data.datasource

import id.co.hasilkarya.smarthome.core.network.utils.BASE_URL
import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.core.network.utils.safeCall
import id.co.hasilkarya.smarthome.core.preferences.AppPreferences
import id.co.hasilkarya.smarthome.home.data.dto.DeviceUpdateResponse
import id.co.hasilkarya.smarthome.home.data.dto.DevicesResponse
import id.co.hasilkarya.smarthome.home.data.dto.GetUserResponse
import id.co.hasilkarya.smarthome.home.data.dto.HomesResponse
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject

class HomeDataSourceImpl(
    private val client: HttpClient,
    private val pref: AppPreferences
) : HomeDataSource {
    override fun getToken(): Flow<String> {
        return pref.getToken()
    }

    override suspend fun getDevices(token: String): Result<DevicesResponse, DataError.Remote> {
        return safeCall<DevicesResponse> {
            client.get("$BASE_URL/devices") {
                bearerAuth(token)
            }
        }
    }

    override suspend fun getHomes(token: String): Result<HomesResponse, DataError.Remote> {
        return safeCall<HomesResponse> {
            client.get("$BASE_URL/homes") {
                bearerAuth(token)
            }
        }
    }

    override suspend fun getDevicesByHome(token: String, homeId: Int): Result<DevicesResponse, DataError.Remote> {
        return safeCall<DevicesResponse> {
            client.get("$BASE_URL/homes/$homeId/devices") {
                bearerAuth(token)
            }
        }
    }

    override suspend fun getUser(token: String): Result<GetUserResponse, DataError.Remote> {
        return safeCall<GetUserResponse> {
            client.get("$BASE_URL/me") {
                bearerAuth(token)
            }
        }
    }

    override suspend fun updateDevice(
        id: Int,
        token: String,
        request: JsonObject
    ): Result<DeviceUpdateResponse, DataError.Remote> {
        return safeCall<DeviceUpdateResponse> {
            client.put("$BASE_URL/devices/$id") {
                setBody(request)
                bearerAuth(token)
            }
        }
    }
}