package com.berlin.data.repository

import com.berlin.data.memory.ClothesDummyData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class ClothesRepositoryImplTest {

    private val repository = ClothesRepositoryImpl()

    @Test
    fun `getAllClothes should return non-empty list of clothes`() = runBlocking {
        val result = repository.getAllClothes()

        assertThat(result).isNotEmpty()
    }

    @Test
    fun `getAllClothes should return same data as ClothesDummyData`() = runBlocking {
        val expected = ClothesDummyData.getClothesDummyData()
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
}
