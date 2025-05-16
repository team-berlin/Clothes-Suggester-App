package com.berlin.domain.usecase

import com.berlin.data.mapper.ClothesDataMapperImpl
import com.berlin.domain.exepction.ClothesSuggestionException
import com.berlin.domain.repository.ClothesRepository
import com.google.common.truth.Truth.assertThat
import helper.TestDummyData.CLOTHES_SUITABLE_CASUAL
import helper.TestDummyData.NO_CLOTHES_EXCEPTION
import helper.TestDummyData.TEMPRATURE
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SuggestClothesTemperatureUseCaseTest {

    private lateinit var clothesRepository: ClothesRepository
    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private lateinit var clothesMapper: ClothesDataMapperImpl
    private lateinit var useCase: SuggestClothesTemperatureUseCase

    @BeforeEach
    fun setup() {
        clothesRepository = mockk()
        getWeatherUseCase = mockk()
        clothesMapper = mockk()
        useCase = SuggestClothesTemperatureUseCase(clothesRepository, getWeatherUseCase)
    }

    @Test
    fun `invoke should throw exception when no clothes match`() = runBlocking {
        coEvery { getWeatherUseCase.invoke().temperature } returns TEMPRATURE
        coEvery { clothesRepository.getAllClothes() } returns emptyList()

        val exception = assertThrows<ClothesSuggestionException> {
            useCase.invoke()
        }

        assertThat(exception.message).isEqualTo(NO_CLOTHES_EXCEPTION)
    }

    @Test
    fun `isClothingSuitable should return true when temperature is within range`() {
        val clothes = CLOTHES_SUITABLE_CASUAL
        val result = useCase.isClothingSuitable(clothes, 15.0)

        assertThat(result).isTrue()
    }

    @Test
    fun `isClothingSuitable should return true for boundary values`() {
        val clothes = CLOTHES_SUITABLE_CASUAL
        assertThat(useCase.isClothingSuitable(clothes, 10.0)).isTrue()
        assertThat(useCase.isClothingSuitable(clothes, 20.0)).isTrue()
    }

    @Test
    fun `isClothingSuitable should return false when temperature is below range`() {
        val clothes = CLOTHES_SUITABLE_CASUAL

        val result = useCase.isClothingSuitable(clothes, 5.0)

        assertThat(result).isFalse()
    }

    @Test
    fun `isClothingSuitable should return false when temperature is above range`() {
        val clothes = CLOTHES_SUITABLE_CASUAL

        val result = useCase.isClothingSuitable(clothes, 25.0)

        assertThat(result).isFalse()
    }


}
