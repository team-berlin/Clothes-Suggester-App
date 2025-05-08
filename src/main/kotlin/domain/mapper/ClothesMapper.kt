package com.berlin.domain.mapper

import com.berlin.domain.model.UserUiClothes
import com.berlin.domain.model.UserClothes

interface ClothesMapper {
    fun toUserClothesData(clothes: UserClothes): UserUiClothes
}