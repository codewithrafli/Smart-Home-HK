package id.co.hasilkarya.smarthome.login.domain

import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.login.domain.model.UserWithToken

interface LoginRepository {

    suspend fun login(email: String, password: String): Result<UserWithToken, DataError.Remote>

}