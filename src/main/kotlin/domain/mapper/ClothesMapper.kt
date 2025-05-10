package com.berlin.domain.mapper

import com.berlin.data.local.dto.ClothesDto
import com.berlin.domain.model.UserClothes

interface ClothesMapper {
    fun toUserClothesData(response: ClothesDto): UserClothes
}