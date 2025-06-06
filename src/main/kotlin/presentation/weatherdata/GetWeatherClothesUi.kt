package com.berlin.presentation.weatherdata

import com.berlin.domain.exepction.ClothesSuggestionException
import com.berlin.domain.usecase.SuggestClothesTemperatureUseCase
import com.berlin.presentation.UiRunner
import com.berlin.presentation.io.Viewer
import com.berlin.presentation.mapper.ClothesMapperImpl
import com.berlin.presentation.model.UserClothesUi

class GetWeatherClothesUi(
    private val viewer: Viewer,
    private val clothesMapper: ClothesMapperImpl,
    private val suggestClothesTemperature: SuggestClothesTemperatureUseCase,
) : UiRunner {
    override val id: Int = 2
    override val label: String = "Get weather clothes depends temp"


    override suspend fun start() {
        try {
            val weatherOutfits = suggestClothesTemperature()
            viewer.show("\nRecommended outfit based on weather:")
            weatherOutfits.forEachIndexed { index, weatherOutfit ->
                displayWeatherOutfit(index, clothesMapper.map(weatherOutfit))
            }
        }catch (e: ClothesSuggestionException){
            viewer.show("No suiable outfit available ${e.message}")
        }catch (e: Exception){
            viewer.show("An error occurred while fetching the outfit ${e.message}")
        }
        viewer.show("----------------------------------------")
    }

    private fun displayWeatherOutfit(index: Int, weatherOutfit: UserClothesUi) {
        viewer.show( "${index + 1} - ${weatherOutfit.outfitStyle}\n"
                + "\t- Top: ${weatherOutfit.top}\n"
                + "\t- Bottom: ${weatherOutfit.bottom}\n"
                + "\t- Shoes: ${weatherOutfit.shoes}\n"
                + "\t- Accessories: ${weatherOutfit.accessories.joinToString()}"
        )
    }
}


