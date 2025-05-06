package com.berlin.domain.repository

import com.berlin.data.dto.Clothes

interface ClothesRepository{
    suspend fun getAllClothes(): List<Clothes>
}