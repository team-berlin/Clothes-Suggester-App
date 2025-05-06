package com.berlin.domain.usecase

import com.berlin.data.memory.UserClothesDummyData
import com.berlin.domain.model.UserClothes

class GetWeatherClothesUseCase {

    operator fun invoke(): List<UserClothes>{
       return UserClothesDummyData.userClothes
   }
}