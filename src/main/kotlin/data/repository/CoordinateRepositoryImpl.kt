package com.berlin.data.repository

import com.berlin.data.mapper.IpGeolocationMapperImpl
import com.berlin.data.remote.dto.IpGeolocationResponseDto
import com.berlin.domain.exepction.GeolocationFetchException
import com.berlin.domain.model.Coordinates
import com.berlin.data.service.CoordinateApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CoordinateRepositoryImpl(
    private val client: HttpClient,
    private val geolocationMapper: IpGeolocationMapperImpl
): CoordinateApi {
    override suspend fun fetchCoordinates(): Coordinates {
        try {
            val response = client.get("http://ip-api.com/json").body<IpGeolocationResponseDto>()
            return geolocationMapper.map(response)
        } catch (e: Exception) {
            throw GeolocationFetchException("Failed to fetch coordinates")
        }
    }

}