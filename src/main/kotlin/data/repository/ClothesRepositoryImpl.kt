package com.berlin.data.repository

import com.berlin.data.memory.ClothesDummyData
import com.berlin.data.dto.Clothes
import com.berlin.domain.repository.ClothesRepository

class ClothesRepositoryImpl: ClothesRepository {
    override suspend fun getAllClothes(): List<Clothes> {
        return ClothesDummyData.getClothesDummyData()
    }
}