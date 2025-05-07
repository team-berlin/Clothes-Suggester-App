package com.berlin.presentation.weatherdata

import com.berlin.domain.model.UserClothes
import com.berlin.domain.usecase.GetWeatherUseCase
import com.berlin.domain.usecase.SuggestClothesTemperatureUseCase
import com.berlin.presentation.UiRunner
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer

class GetWeatherClothesUi(
    private val viewer: Viewer,
    private val suggestClothesTemperature: SuggestClothesTemperatureUseCase,
) : UiRunner {
    override val id: Int = 2
    override val label: String = "Get weather clothes depends temp"

    override suspend fun start() {

        val weatherOutfits = suggestClothesTemperature()
        weatherOutfits.forEach { weatherOutfit ->
            displayWeatherOutfit(weatherOutfit)
        }
    }

    private fun displayWeatherOutfit(weatherOutfit: UserClothes) {
        viewer.show(
            "Recommended outfit based on weather:\n"
                + "- Style: ${weatherOutfit.outfitStyle}\n"
                + "- Top: ${weatherOutfit.top}\n"
                + "- Bottom: ${weatherOutfit.bottom}\n"
                + "- Shoes: ${weatherOutfit.shoes}\n"
                + "- Accessories: ${weatherOutfit.accessories.joinToString()}"
        )
    }
}


