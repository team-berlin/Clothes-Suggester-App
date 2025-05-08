package com.berlin.data.mapper

import com.berlin.data.dto.Clothes
import com.berlin.domain.model.UserClothes

class ClothesMapperImpl : ClothesMapper {
    override fun toUserClothesData(response: Clothes): UserClothes = UserClothes(
        temperatureRange = response.temperatureRange,
        weatherCondition = response.weatherCondition,
        outfitStyle = response.outfitStyle,
        top = response.top,
        bottom = response.bottom,
        shoes = response.shoes,
        accessories = response.accessories
    )
}