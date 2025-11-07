package id.co.hasilkarya.smarthome.home.data.datasource

import id.co.hasilkarya.smarthome.core.network.utils.BASE_URL
import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.core.network.utils.safeCall
import id.co.hasilkarya.smarthome.core.preferences.AppPreferences
import id.co.hasilkarya.smarthome.home.data.dto.DevicesResponse
import id.co.hasilkarya.smarthome.home.data.dto.GetUserResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

class HomeDataSourceImpl(
    private val client: HttpClient,
    private val pref: AppPreferences
): HomeDataSource {
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

    override suspend fun getUser(token: String): Result<GetUserResponse, DataError.Remote> {
        return safeCall<GetUserResponse> {
            client.get("$BASE_URL/me") {
                bearerAuth(token)
            }
        }
    }
}