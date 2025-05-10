package com.berlin.data.mapper

import com.berlin.domain.model.Coordinates
import com.berlin.data.remote.dto.IpGeolocationResponseDto

interface IpGeolocationMapper {
    fun toCoordinates(response: IpGeolocationResponseDto): Coordinates
}
