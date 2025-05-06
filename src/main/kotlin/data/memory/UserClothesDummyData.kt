package com.berlin.data.memory

import com.berlin.domain.model.UserClothes

object UserClothesDummyData {
    var userClothes = listOf(
        UserClothes(
            outfitStyle = "casual",
            top = "Crop top",
            bottom = "Denim skirt",
            shoes = "Flat sandals",
            accessories = listOf("Anklet", "Sunglasses")
        ), UserClothes(
            outfitStyle = "casual",
            top = "Crop top",
            bottom = "Denim skirt",
            shoes = "Flat sandals",
            accessories = listOf("Anklet", "Sunglasses")
        ), UserClothes(
            outfitStyle = "casual",
            top = "Crop top",
            bottom = "Denim skirt",
            shoes = "Flat sandals",
            accessories = listOf("Anklet", "Sunglasses")
        )
    )
}