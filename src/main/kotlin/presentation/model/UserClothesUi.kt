package com.berlin.presentation.model

data class UserClothesUi(
    val outfitStyle: String,
    val top: String,
    val bottom: String,
    val shoes: String,
    val accessories: List<String>
)