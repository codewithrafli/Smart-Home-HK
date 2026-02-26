package id.co.hasilkarya.smarthome.home.domain.models

data class HomeWithDevices(
    val id: Int,
    val name: String,
    val image: String? = null,
    val role: String? = null,
    val featuredDevices: List<Device> = emptyList()
)
