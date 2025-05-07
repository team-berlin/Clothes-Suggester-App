package com.berlin.data.mapper

import com.berlin.domain.model.Coordinates
import data.dto.IpGeolocationResponse

interface IpGeolocationMapper {
    fun toCoordinates(response: IpGeolocationResponse): Coordinates
}
