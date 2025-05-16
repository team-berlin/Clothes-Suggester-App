package com.berlin.presentation.mapper

import com.berlin.shered.Mapper
import com.berlin.domain.model.UserClothes
import com.berlin.presentation.model.UserClothesUi

class ClothesMapperImpl: Mapper<UserClothes, UserClothesUi> {
    override fun map(from: UserClothes): UserClothesUi {
       return UserClothesUi(
           outfitStyle = from.outfitStyle,
           top = from.top,
           bottom = from.bottom,
           shoes = from.shoes,
           accessories = from.accessories
       )
    }
}