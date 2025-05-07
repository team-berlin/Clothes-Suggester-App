package com.berlin.domain.usecase

import com.berlin.domain.mapper.ClothesMapper
import com.berlin.data.dto.Clothes
import com.berlin.domain.exepction.ClothesSuggestionException
import com.berlin.domain.model.UserClothes
import com.berlin.domain.repository.ClothesRepository

class SuggestClothesTemperatureUseCase(
    private val clothesRepository: ClothesRepository,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val clothesMapper: ClothesMapper
) {
    suspend operator fun invoke(): List<UserClothes> {
        val temperature = getWeatherUseCase().temperature

        return clothesRepository.getAllClothes()
            .filter { isClothingSuitable(it, temperature) }
            .map { clothesMapper.toUserClothesData(it) }
            .ifEmpty { throw ClothesSuggestionException("No Clothes Found") }
    }

    fun isClothingSuitable(clothes: Clothes, temperature: Double): Boolean {
        return temperature in clothes.temperatureRange.low..clothes.temperatureRange.high
    }
}