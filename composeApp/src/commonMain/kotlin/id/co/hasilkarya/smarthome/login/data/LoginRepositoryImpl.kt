package id.co.hasilkarya.smarthome.login.data

import androidx.datastore.core.IOException
import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.core.preferences.AppPreferences
import id.co.hasilkarya.smarthome.login.data.datasource.LoginRemoteDataSource
import id.co.hasilkarya.smarthome.login.data.dto.LoginRequest
import id.co.hasilkarya.smarthome.login.domain.LoginRepository
import id.co.hasilkarya.smarthome.login.domain.model.UserWithToken

class LoginRepositoryImpl(
    private val remoteDataSource: LoginRemoteDataSource,
    private val localPreferences: AppPreferences
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

    override suspend fun saveToken(token: String): Result<Boolean, DataError.Local> {
        return try {
            localPreferences.saveToken(token)
            Result.Success(true)
        } catch (_: IOException) {
            Result.Error(DataError.Local.DISK_FULL)
        } catch (_: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun saveUserId(userId: String): Result<Boolean, DataError.Local> {
        return try {
            localPreferences.saveUserId(userId)
            Result.Success(true)
        } catch (_: IOException) {
            Result.Error(DataError.Local.DISK_FULL)
        } catch (_: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}