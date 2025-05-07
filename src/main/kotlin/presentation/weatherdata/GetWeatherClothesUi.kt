package com.berlin.presentation.weatherdata

import com.berlin.domain.model.UserClothes
import com.berlin.domain.usecase.SuggestClothesTemperatureUseCase
import com.berlin.presentation.UiRunner
import com.berlin.presentation.io.Viewer

class GetWeatherClothesUi(
    private val viewer: Viewer,
    private val suggestClothesTemperature: SuggestClothesTemperatureUseCase,
) : UiRunner {
    override val id: Int = 2
    override val label: String = "Get weather clothes depends temp"

    override suspend fun start() {
        val weatherOutfits = suggestClothesTemperature()
        viewer.show("\nRecommended outfit based on weather:")
        weatherOutfits.forEachIndexed { index, weatherOutfit ->
            displayWeatherOutfit(index, weatherOutfit)
        }
        viewer.show("----------------------------------------")
    }

    private fun displayWeatherOutfit(index: Int, weatherOutfit: UserClothes) {
        viewer.show( "${index + 1} - ${weatherOutfit.outfitStyle}\n"
                + "\t- Top: ${weatherOutfit.top}\n"
                + "\t- Bottom: ${weatherOutfit.bottom}\n"
                + "\t- Shoes: ${weatherOutfit.shoes}\n"
                + "\t- Accessories: ${weatherOutfit.accessories.joinToString()}"
        )
    }
}


