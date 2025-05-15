package com.berlin.domain.repository

import com.berlin.data.dto.ClothesDto

interface ClothesRepository{
    suspend fun getAllClothes(): List<ClothesDto>
}