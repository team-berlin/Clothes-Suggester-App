package com.berlin.data.dto

import com.berlin.domain.model.Temp

data class Clothes(
    val temperatureRange: Temp,
    val weatherCondition: String,
    val outfitStyle: String,
    val top: String,
    val bottom: String,
    val shoes: String,
    val accessories: List<String>
)