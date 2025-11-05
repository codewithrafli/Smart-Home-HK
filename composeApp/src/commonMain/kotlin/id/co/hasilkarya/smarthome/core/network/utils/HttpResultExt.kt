package id.co.hasilkarya.smarthome.core.network.utils

import io.ktor.client.call.*
import io.ktor.client.network.sockets.*
import io.ktor.client.statement.*
import io.ktor.serialization.*
import io.ktor.util.network.*
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (e: SocketTimeoutException) {
        return Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnresolvedAddressException) {
        return Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: SerializationException) {
        return Result.Error(DataError.Remote.SERIALIZATION)
    } catch (e: JsonConvertException) {
        return Result.Error(DataError.Remote.SERIALIZATION)
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive()
        return Result.Error(DataError.Remote.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError.Remote> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(DataError.Remote.SERIALIZATION)
            }
        }

        400 -> Result.Error(DataError.Remote.BAD_REQUEST)
        422 -> Result.Error(DataError.Remote.BAD_REQUEST)
        401 -> Result.Error(DataError.Remote.UNAUTHORIZED)
        404 -> Result.Error(DataError.Remote.NOT_FOUND)
        408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Remote.SERVER)
        else -> Result.Error(DataError.Remote.UNKNOWN)
    }
}