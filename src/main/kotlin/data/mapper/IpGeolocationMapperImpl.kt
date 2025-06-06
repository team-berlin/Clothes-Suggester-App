package com.berlin.data.mapper

import com.berlin.domain.model.Coordinates
import com.berlin.data.remote.dto.IpGeolocationResponseDto
import com.berlin.shered.Mapper

class IpGeolocationMapperImpl: Mapper<IpGeolocationResponseDto, Coordinates> {
    override fun map(from: IpGeolocationResponseDto): Coordinates {
        return Coordinates(
            latitude = from.latitude,
            longitude = from.longitude
        )
    }
}

