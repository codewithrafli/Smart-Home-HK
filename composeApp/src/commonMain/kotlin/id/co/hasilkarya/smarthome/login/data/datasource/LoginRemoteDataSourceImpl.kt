package id.co.hasilkarya.smarthome.login.data.datasource

import id.co.hasilkarya.smarthome.core.network.utils.BASE_URL
import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.core.network.utils.safeCall
import id.co.hasilkarya.smarthome.login.data.dto.LoginRequest
import id.co.hasilkarya.smarthome.login.data.dto.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class LoginRemoteDataSourceImpl(
    private val client: HttpClient
): LoginRemoteDataSource {
    override suspend fun login(request: LoginRequest): Result<LoginResponse, DataError.Remote> {
        return safeCall<LoginResponse> {
            client.post("$BASE_URL/login") {
                setBody(request)
            }
        }
    }
}