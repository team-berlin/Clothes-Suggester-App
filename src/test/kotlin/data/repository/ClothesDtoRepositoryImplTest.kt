package com.berlin.data.repository

import com.berlin.data.dto.ClothesDto
import com.berlin.data.mapper.ClothesDataMapperImpl
import com.berlin.data.memory.ClothesDummyData
import com.berlin.domain.model.TemperatureRange
import com.berlin.domain.model.UserClothes
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class ClothesDtoRepositoryImplTest {

    private lateinit var clothesDummyData: ClothesDummyData
    private lateinit var clothesDataMapper: ClothesDataMapperImpl

    private lateinit var repository: ClothesRepositoryImpl

    @BeforeEach
    fun setup() {
        clothesDummyData = mockk()
        clothesDataMapper = ClothesDataMapperImpl()
        every { clothesDummyData.getClothesDummyData() } returns dummyClothesList
        repository = ClothesRepositoryImpl(clothesDummyData, clothesDataMapper)
    }

    @Test
    fun `getAllClothes should return non-empty list of clothes`() = runBlocking {
        val result = repository.getAllClothes()
        assertThat(result).isNotEmpty()
    }

    @Test
    fun `getAllClothes should return same data as ClothesDummyData`() = runBlocking {
        val expected: List<UserClothes> = dummyClothesList.map { clothesDataMapper.map(it) }

        val actual = repository.getAllClothes()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getAllClothes items should have valid fields`() = runBlocking {
        val result = repository.getAllClothes()

        result.forEach { clothes ->
            //temp
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