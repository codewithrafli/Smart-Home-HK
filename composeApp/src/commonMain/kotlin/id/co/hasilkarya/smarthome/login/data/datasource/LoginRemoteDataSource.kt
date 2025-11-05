package id.co.hasilkarya.smarthome.login.data.datasource

import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.login.data.dto.LoginRequest
import id.co.hasilkarya.smarthome.login.data.dto.LoginResponse

interface LoginRemoteDataSource {

    suspend fun login(request: LoginRequest): Result<LoginResponse, DataError.Remote>

}