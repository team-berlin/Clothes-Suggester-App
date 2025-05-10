package com.berlin.data.mapper

import com.berlin.domain.model.Coordinates
import com.berlin.data.remote.dto.IpGeolocationResponseDto

class IpGeolocationMapperImpl : IpGeolocationMapper{
    override fun toCoordinates(response: IpGeolocationResponseDto): Coordinates {
        return Coordinates(
            latitude = response.latitude,
            longitude = response.longitude
        )
    }
}