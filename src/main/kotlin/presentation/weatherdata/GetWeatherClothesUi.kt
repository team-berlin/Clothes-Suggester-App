package com.berlin.presentation.weatherdata

import com.berlin.data.memory.UserClothesDummyData
import com.berlin.domain.model.UserClothes
import com.berlin.domain.usecase.GetWeatherUseCase
import com.berlin.presentation.UiRunner
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer

class GetWeatherClothesUi(
    private val viewer: Viewer,
    private val reader: Reader,
    private val getWeatherUseCase: GetWeatherUseCase,
) : UiRunner {
    override val id: Int = 2
    override val label: String = "Get weather clothes depends temp"

    override suspend fun start() {
        val latitude = getLatitude() ?: return

        val longitude = getLongitude() ?: return

        val temperature = getWeatherUseCase(latitude, longitude).temperature
        //get weather clothes use case (temperature)

        val weatherOutfits = UserClothesDummyData.userClothes
        weatherOutfits.forEach { weatherOutfit ->
            displayWeatherOutfit(weatherOutfit)
        }
    }

    private fun getLatitude(): Double? {
        viewer.show("Enter latitude (e.g., 32.61889) or type 'back' to return main menu :")
        reader.readLatitude()
        viewer.show("Invalid latitude, please enter a number.")
        return null
    }

    fun getLongitude(): Double? {
        viewer.show("Enter longitude (e.g., 35.79011) or type 'back' to return main menu :")
        reader.readLongitude()
        viewer.show("Invalid longitude, please enter a number.")
        return null
    }
    private fun displayWeatherOutfit(weatherOutfit: UserClothes) {
        viewer.show(
            "Recommended outfit based on weather:\n" + "- Style: ${weatherOutfit.outfitStyle}\n" + "- Top: ${weatherOutfit.top}\n" + "- Bottom: ${weatherOutfit.bottom}\n" + "- Shoes: ${weatherOutfit.shoes}\n" + "- Accessories: ${weatherOutfit.accessories.joinToString()}"
        )
    }

}


