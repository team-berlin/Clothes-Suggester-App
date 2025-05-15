package com.berlin.data.repository

import com.berlin.data.dto.ClothesDto
import com.berlin.data.memory.ClothesDummyData
import com.berlin.domain.repository.ClothesRepository

class ClothesRepositoryImpl(
    private val clothesDummyData: ClothesDummyData
): ClothesRepository {
    override suspend fun getAllClothes(): List<ClothesDto> {
        return clothesDummyData.getClothesDummyData()
    }
}