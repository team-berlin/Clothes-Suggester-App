package com.berlin.domain.usecase

import com.berlin.data.local.dto.ClothesDto
import com.berlin.domain.exepction.ClothesSuggestionException
import com.berlin.domain.mapper.ClothesMapper
import com.berlin.domain.model.UserClothes
import com.berlin.domain.repository.ClothesRepository
import com.google.common.truth.Truth.assertThat
import helper.TestDummyData.CLOTHES_SUITABLE
import helper.TestDummyData.FILTERD_CLOTHES
import helper.TestDummyData.NO_CLOTHES_EXCEPTION
import helper.TestDummyData.SUNNY_CLOTHES
import helper.TestDummyData.TEMPRATURE
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SuggestClothesDtoTemperatureUseCaseTest {

    private lateinit var clothesRepository: ClothesRepository
    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private lateinit var clothesMapper: ClothesMapper
    private lateinit var useCase: SuggestClothesTemperatureUseCase

    @BeforeEach
    fun setup() {
        clothesRepository = mockk()
        getWeatherUseCase = mockk()
        clothesMapper = mockk()
        useCase =
            SuggestClothesTemperatureUseCase(clothesRepository, getWeatherUseCase, clothesMapper)
    }

    @Test
    fun `invoke should return filtered and mapped clothes list`() = runBlocking {
        val mappedClothesList = FILTERD_CLOTHES.map {
            UserClothes(
                it.outfitStyle, it.top, it.bottom, it.shoes, it.accessories
            )
        }

        coEvery { getWeatherUseCase.invoke().temperature } returns TEMPRATURE
        coEvery { clothesRepository.getAllClothes() } returns FILTERD_CLOTHES
        every { clothesMapper.toUserClothesData(any()) } answers {
            val clothes = it.invocation.args[0] as ClothesDto
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
        coEvery { getWeatherUseCase.invoke().temperature } returns TEMPRATURE
        coEvery { clothesRepository.getAllClothes() } returns emptyList()

        val exception = assertThrows<ClothesSuggestionException> {
            useCase.invoke()
        }

        assertThat(exception.message).isEqualTo(NO_CLOTHES_EXCEPTION)
    }

    @Test
    fun `isClothingSuitable should return true when temperature is within range`() {
        val clothes = CLOTHES_SUITABLE
        val result = useCase.isClothingSuitable(clothes, 15.0)

        assertThat(result).isTrue()
    }

    @Test
    fun `isClothingSuitable should return true for boundary values`() {
        val clothes = SUNNY_CLOTHES
        assertThat(useCase.isClothingSuitable(clothes, 10.0)).isTrue()
        assertThat(useCase.isClothingSuitable(clothes, 20.0)).isTrue()
    }

    @Test
    fun `isClothingSuitable should return false when temperature is below range`() {
        val clothes = SUNNY_CLOTHES

        val result = useCase.isClothingSuitable(clothes, 5.0)

        assertThat(result).isFalse()
    }

    @Test
    fun `isClothingSuitable should return false when temperature is above range`() {
        val clothes = SUNNY_CLOTHES

        val result = useCase.isClothingSuitable(clothes, 25.0)

        assertThat(result).isFalse()
    }


}
