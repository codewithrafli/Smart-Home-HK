package id.co.hasilkarya.smarthome.login.data

import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.login.data.datasource.LoginRemoteDataSource
import id.co.hasilkarya.smarthome.login.data.dto.LoginRequest
import id.co.hasilkarya.smarthome.login.domain.LoginRepository
import id.co.hasilkarya.smarthome.login.domain.model.UserWithToken

class LoginRepositoryImpl(
    private val remoteDataSource: LoginRemoteDataSource
): LoginRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<UserWithToken, DataError.Remote> {
        val request = LoginRequest(password, email)
        return when (val response = remoteDataSource.login(request)) {
            is Result.Success -> Result.Success(response.data.data.toUserWithToken())
            is Result.Error -> {
                println("[LoginRepositoryImpl] Login failed: $response")
                Result.Error(response.error)
            }
        }
    }
}