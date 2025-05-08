package com.berlin.data.repository

import com.berlin.data.memory.ClothesDummyData
import com.berlin.data.dto.Clothes
import com.berlin.data.mapper.ClothesMapper
import com.berlin.domain.model.UserClothes
import com.berlin.domain.repository.ClothesRepository

class ClothesRepositoryImpl(
    private val clothesMapper: ClothesMapper
): ClothesRepository {
    override suspend fun getAllClothes(): List<UserClothes> {
        return ClothesDummyData.getClothesDummyData()
            .map { clothesMapper.toUserClothesData(it) }
    }
}