package com.berlin.data.repository

import com.berlin.data.mapper.ClothesDataMapperImpl
import com.berlin.data.memory.ClothesDummyData
import com.berlin.domain.model.UserClothes
import com.berlin.domain.repository.ClothesRepository

class ClothesRepositoryImpl(
    private val clothesDummyData: ClothesDummyData,
    private val clothesDataMapper: ClothesDataMapperImpl
): ClothesRepository {
    override suspend fun getAllClothes(): List<UserClothes> {
        return clothesDummyData.getClothesDummyData().map {
           clothesDataMapper.map(it)
        }
    }
}