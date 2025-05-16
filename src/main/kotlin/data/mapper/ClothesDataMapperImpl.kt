package com.berlin.data.mapper

import com.berlin.data.dto.ClothesDto
import com.berlin.domain.model.UserClothes
import com.berlin.shered.Mapper

class ClothesDataMapperImpl: Mapper<ClothesDto, UserClothes> {
    override fun map(from: ClothesDto): UserClothes {
        return UserClothes(
            temperature = from.temperatureRange,
            outfitStyle = from.outfitStyle,
            top = from.top,
            bottom = from.bottom,
            shoes = from.shoes,
            accessories = from.accessories
        )
    }
}