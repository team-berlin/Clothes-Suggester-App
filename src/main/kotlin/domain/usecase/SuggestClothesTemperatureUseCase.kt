package com.berlin.domain.usecase

import com.berlin.domain.exepction.ClothesSuggestionException
import com.berlin.domain.model.UserClothes
import com.berlin.domain.repository.ClothesRepository

class SuggestClothesTemperatureUseCase(
    private val clothesRepository: ClothesRepository,
    private val getWeatherUseCase: GetWeatherUseCase
) {
    suspend operator fun invoke(): List<UserClothes> {
        val temperature = getWeatherUseCase().temperature

        return clothesRepository.getAllClothes()
            .filter { isClothingSuitable(it, temperature) }
            .ifEmpty { throw ClothesSuggestionException("No Clothes Found") }
    }

    fun isClothingSuitable(clothes: UserClothes, temperature: Double): Boolean {
        return temperature in clothes.temperature.low..clothes.temperature.high
    }
}