package com.berlin.data.mapper

import com.berlin.domain.model.Coordinates
import data.dto.IpGeolocationResponse

class IpGeolocationMapperImpl : IpGeolocationMapper{
    override fun toCoordinates(response: IpGeolocationResponse): Coordinates {
        return Coordinates(
            latitude = response.latitude,
            longitude = response.longitude
        )
    }
}