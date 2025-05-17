package com.berlin.data.service

import com.berlin.domain.model.Coordinates

interface CoordinateApi {
    suspend fun fetchCoordinates(): Coordinates
}