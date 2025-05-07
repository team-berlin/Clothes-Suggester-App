package com.berlin.domain.usecase

import com.berlin.data.dto.Clothes
import com.berlin.domain.exepction.ClothesSuggestionException
import com.berlin.domain.mapper.ClothesMapper
import com.berlin.domain.model.TemperatureRange
import com.berlin.domain.model.UserClothes
import com.berlin.domain.repository.ClothesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class SuggestClothesTemperatureUseCaseTest {

    private lateinit var clothesRepository: ClothesRepository
    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private lateinit var clothesMapper: ClothesMapper
    private lateinit var useCase: SuggestClothesTemperatureUseCase

    @BeforeEach
    fun setup() {
        clothesRepository = mockk()
        getWeatherUseCase = mockk()
        clothesMapper = mockk()
        useCase = SuggestClothesTemperatureUseCase(clothesRepository, getWeatherUseCase, clothesMapper)
    }

    @Test
    fun `invoke should return filtered and mapped clothes list`() = runBlocking {
        val latitude = 52.52
        val longitude = 13.405
        val temperature = 18.5

        val clothesList = listOf(
            Clothes(
                temperatureRange = TemperatureRange(low = 10.0, high = 20.0),
                weatherCondition = "Sunny",
                outfitStyle = "Casual",
                top = "T-Shirt",
                bottom = "Jeans",
                shoes = "Sneakers",
                accessories = listOf("Sunglasses")
            ),
            Clothes(
                temperatureRange = TemperatureRange(low = 15.0, high = 25.0),
                weatherCondition = "Cloudy",
                outfitStyle = "Semi-Casual",
                top = "Sweater",
                bottom = "Chinos",
                shoes = "Boots",
                accessories = listOf("Scarf")
            )
        )

        val mappedClothesList = clothesList.map {
            UserClothes(it.outfitStyle, it.top, it.bottom,
                it.shoes, it.accessories)
        }

        coEvery { getWeatherUseCase.invoke().temperature } returns temperature
        coEvery { clothesRepository.getAllClothes() } returns clothesList
        every { clothesMapper.toUserClothesData(any()) } answers {
            val clothes = it.invocation.args[0] as Clothes
            UserClothes(
                outfitStyle = clothes.outfitStyle,
                top = clothes.top,
                bottom = clothes.bottom,
                shoes = clothes.shoes,
                accessories = clothes.accessories
            )
        }

        val result = useCase.invoke()
        assertThat(result).isEqualTo(mappedClothesList)
        coVerify { clothesRepository.getAllClothes() }
        verify { clothesMapper.toUserClothesData(any()) }
    }

    @Test
    fun `invoke should throw exception when no clothes match`() = runBlocking {
        val latitude = 52.52
        val longitude = 13.405
        val temperature = 5.0

        coEvery { getWeatherUseCase.invoke().temperature } returns temperature
        coEvery { clothesRepository.getAllClothes() } returns emptyList()

        val exception = assertThrows<ClothesSuggestionException> {
            useCase.invoke()
        }

        assertThat(exception.message).isEqualTo("No Clothes Found")
    }

    @Test
    fun `isClothingSuitable should return true when temperature is within range`() {
        val clothes = Clothes(
            temperatureRange = TemperatureRange(low = 10.0, high = 20.0),
            weatherCondition = "Sunny",
            outfitStyle = "Casual",
            top = "T-Shirt",
            bottom = "Jeans",
            shoes = "Sneakers",
            accessories = listOf("Sunglasses")
        )

        val result = useCase.isClothingSuitable(clothes, 15.0)

        assertThat(result).isTrue()
    }
    @Test
    fun `isClothingSuitable should return true for boundary values`() {
        val clothes = Clothes(
            temperatureRange = TemperatureRange(low = 10.0, high = 20.0),
            weatherCondition = "Sunny",
            outfitStyle = "Casual",
            top = "T-Shirt",
            bottom = "Jeans",
            shoes = "Sneakers",
            accessories = listOf("Sunglasses")
        )

        assertThat(useCase.isClothingSuitable(clothes, 10.0)).isTrue()
        assertThat(useCase.isClothingSuitable(clothes, 20.0)).isTrue()
    }

    @Test
    fun `isClothingSuitable should return false when temperature is below range`() {
        val clothes = Clothes(
            temperatureRange = TemperatureRange(low = 10.0, high = 20.0),
            weatherCondition = "Sunny",
            outfitStyle = "Casual",
            top = "T-Shirt",
            bottom = "Jeans",
            shoes = "Sneakers",
            accessories = listOf("Sunglasses")
        )

        val result = useCase.isClothingSuitable(clothes, 5.0)

        assertThat(result).isFalse()
    }

    @Test
    fun `isClothingSuitable should return false when temperature is above range`() {
        val clothes = Clothes(
            temperatureRange = TemperatureRange(low = 10.0, high = 20.0),
            weatherCondition = "Sunny",
            outfitStyle = "Casual",
            top = "T-Shirt",
            bottom = "Jeans",
            shoes = "Sneakers",
            accessories = listOf("Sunglasses")
        )

        val result = useCase.isClothingSuitable(clothes, 25.0)

        assertThat(result).isFalse()
    }

}
