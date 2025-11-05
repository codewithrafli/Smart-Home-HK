package id.co.hasilkarya.smarthome.core.network.utils

sealed interface DataError : Error {
    enum class Remote : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN,
        NOT_FOUND,
        BAD_REQUEST,

        UNAUTHORIZED
    }

    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN
    }
}