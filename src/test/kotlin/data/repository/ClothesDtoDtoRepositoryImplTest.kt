package com.berlin.data.repository

import com.berlin.data.dto.ClothesDto
import com.berlin.data.memory.ClothesDummyData
import com.berlin.domain.model.TemperatureRange
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class ClothesDtoDtoRepositoryImplTest {

    private lateinit var clothesDummyData: ClothesDummyData
    private lateinit var repository: ClothesRepositoryImpl

    @BeforeEach
    fun setup() {
        clothesDummyData = mockk()
        every { clothesDummyData.getClothesDummyData() } returns dummyClothesList
        repository = ClothesRepositoryImpl(clothesDummyData)
    }

    @Test
    fun `getAllClothes should return non-empty list of clothes`() = runBlocking {
        val result = repository.getAllClothes()
        assertThat(result).isNotEmpty()
    }

    @Test
    fun `getAllClothes should return same data as ClothesDummyData`() = runBlocking {
        val expected = dummyClothesList
        val actual = repository.getAllClothes()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getAllClothes items should have valid fields`() = runBlocking {
        val result = repository.getAllClothes()

        result.forEach { clothes ->
            assertThat(clothes.weatherCondition).isNotEmpty()
            assertThat(clothes.outfitStyle).isNotEmpty()
            assertThat(clothes.top).isNotEmpty()
            assertThat(clothes.bottom).isNotEmpty()
            assertThat(clothes.shoes).isNotEmpty()
        }
    }
    private companion object{
        private val dummyClothesList = listOf(
            ClothesDto(
                temperatureRange = TemperatureRange(30.0, 40.0),
                weatherCondition = "hot",
                outfitStyle = "casual",
                top = "Tank top",
                bottom = "Shorts",
                shoes = "Flip-flops",
                accessories = listOf("Cap", "Sunglasses")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(20.0, 30.0),
                weatherCondition = "sunny",
                outfitStyle = "semi-formal",
                top = "Polo shirt",
                bottom = "Chinos",
                shoes = "Loafers",
                accessories = listOf("Watch", "Belt")
            )
        )

    }
}