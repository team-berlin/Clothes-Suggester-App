package data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IpGeolocationResponse(
    val status: String,
    val country: String,
    @SerialName("countryCode") val countryCode: String,
    val region: String,
    @SerialName("regionName") val regionName: String,
    val city: String,
    val zip: String,
    @SerialName("lat") val latitude: Double,
    @SerialName("lon") val longitude: Double,
    val timezone: String,
    val isp: String,
    val org: String,
    @SerialName("as") val asName: String,
    val query: String
)
