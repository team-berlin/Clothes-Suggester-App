package com.berlin.presentation.mapper

import com.berlin.data.dto.ClothesDto
import com.berlin.domain.mapper.ClothesMapper
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