package id.co.hasilkarya.smarthome.device.data.datasource

import id.co.hasilkarya.smarthome.core.network.utils.BASE_URL
import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.core.network.utils.safeCall
import id.co.hasilkarya.smarthome.device.data.dto.DeviceResponse
import id.co.hasilkarya.smarthome.home.data.dto.DeviceUpdateResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlinx.serialization.json.JsonObject

class DeviceRemoteDataSourceImpl(
    private val client: HttpClient
): DeviceRemoteDataSource {

    override suspend fun getDevice(token: String, id: Int): Result<DeviceResponse, DataError.Remote> {
        return safeCall<DeviceResponse> {
            client.get("$BASE_URL/devices/$id") {
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