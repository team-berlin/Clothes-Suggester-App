package com.berlin.domain.mapper

import com.berlin.domain.model.UserClothes
import com.berlin.domain.model.UserUiClothes

class ClothesMapperImpl : ClothesMapper {
    override fun toUserClothesData(clothes: UserClothes): UserUiClothes = UserUiClothes(
        outfitStyle = clothes.outfitStyle,
        top = clothes.top,
        bottom = clothes.bottom,
        shoes = clothes.shoes,
        accessories = clothes.accessories
    )
}