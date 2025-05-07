package com.berlin.domain.mapper

import com.berlin.data.dto.Clothes
import com.berlin.domain.model.UserClothes

interface ClothesMapper {
    fun toUserClothesData(response: Clothes): UserClothes
}