package com.berlin.domain.model

data class UserClothes(
    val temperature: TemperatureRange,
    val outfitStyle: String,
    val top: String,
    val bottom: String,
    val shoes: String,
    val accessories: List<String>
)

