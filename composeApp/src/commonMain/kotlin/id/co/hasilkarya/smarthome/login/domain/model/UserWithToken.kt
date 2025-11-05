package id.co.hasilkarya.smarthome.login.domain.model

data class UserWithToken(
    val id: Int,
    val name: String,
    val token: String,
)
