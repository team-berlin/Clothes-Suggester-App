package com.berlin.presentation.weatherdata

import com.berlin.data.dto.Clothes
import com.berlin.data.memory.ClothesDummyData
import com.berlin.domain.usecase.GetWeatherUseCase
import com.berlin.domain.usecase.SuggestClothesTemperatureUseCase
import com.berlin.presentation.UiRunner
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer

class GetWeatherClothesUi(
    private val viewer: Viewer,
    private val reader: Reader,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getWeatherClothesUseCase: SuggestClothesTemperatureUseCase
) : UiRunner {
    override val id: Int = 2
    override val label: String = "Get weather clothes depends temp"

    override suspend fun start() {
        val latitude = getLatitude() ?: return

        val longitude = getLongitude() ?: return

        getWeatherClothesUseCase.invoke(latitude, longitude)
        val temperature = getWeatherUseCase(latitude, longitude).temperature
        val dummyData = ClothesDummyData.getClothesDummyData()
        val suitableClothes = dummyData.filter { clothes ->
            getWeatherClothesUseCase.isClothingSuitable(clothes, temperature)
        }
        if (suitableClothes.isEmpty()) {
            viewer.show("No suitable clothes found for $temperature°C.")
        }
        suitableClothes.forEach { clothes ->
            displayWeatherOutfit(clothes)
        }
    }

    fun getLatitude(): Double? {
        viewer.show("Enter latitude (e.g., 32.61889) or type 'back' to return main menu :")
        val latitude = reader.readLatitude()
        if (latitude == null) {
            viewer.show("Invalid latitude, please enter a number.")
        }
        return latitude
    }

    fun getLongitude(): Double? {
        viewer.show("Enter longitude (e.g., 35.79011) or type 'back' to return main menu :")
        val longitude = reader.readLongitude()
        if (longitude == null) {
            viewer.show("Invalid longitude, please enter a number.")
        }
        return longitude
    }

    private fun displayWeatherOutfit(weatherOutfit: Clothes) {
        viewer.show(
            "Recommended outfit based on weather:\n" + "- Style: ${weatherOutfit.outfitStyle}\n" + "- Top: ${weatherOutfit.top}\n" + "- Bottom: ${weatherOutfit.bottom}\n" + "- Shoes: ${weatherOutfit.shoes}\n" + "- Accessories: ${weatherOutfit.accessories.joinToString()}"
        )
    }
}


