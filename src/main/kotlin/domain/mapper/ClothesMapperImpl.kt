package com.berlin.domain.mapper

import com.berlin.data.local.dto.ClothesDto
import com.berlin.domain.model.UserClothes

class ClothesMapperImpl : ClothesMapper {
    override fun toUserClothesData(response: ClothesDto): UserClothes = UserClothes(
        outfitStyle = response.outfitStyle,
        top = response.top,
        bottom = response.bottom,
        shoes = response.shoes,
        accessories = response.accessories
    )
}