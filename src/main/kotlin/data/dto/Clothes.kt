package com.berlin.data.dto

import com.berlin.domain.model.TemperatureRange

data class Clothes(
    val temperatureRange: TemperatureRange,
    val weatherCondition: String,
    val outfitStyle: String,
    val top: String,
    val bottom: String,
    val shoes: String,
    val accessories: List<String>
)
