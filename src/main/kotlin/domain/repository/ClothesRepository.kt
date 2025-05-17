package com.berlin.domain.repository

import com.berlin.domain.model.UserClothes

interface ClothesRepository{
    suspend fun getAllClothes(): List<UserClothes>
}