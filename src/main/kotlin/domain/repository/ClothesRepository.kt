package com.berlin.domain.repository

import com.berlin.data.local.dto.ClothesDto
interface ClothesRepository{
    suspend fun getAllClothes(): List<ClothesDto>
}