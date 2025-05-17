package helper

import com.berlin.data.dto.ClothesDto
import com.berlin.domain.model.TemperatureRange
import com.berlin.domain.model.UserClothes

object TestDummyData {
        val TEMPRATURE = 18.5
        val NO_CLOTHES_EXCEPTION = "No Clothes Found"
        val SUNNY_CLOTHES = ClothesDto(
            temperatureRange = TemperatureRange(low = 10.0, high = 20.0),
            weatherCondition = "Sunny",
            outfitStyle = "Casual",
            top = "T-Shirt",
            bottom = "Jeans",
            shoes = "Sneakers",
            accessories = listOf("Sunglasses")
        )
        val CLOTHES_SUITABLE = ClothesDto(
            temperatureRange = TemperatureRange(low = 10.0, high = 20.0),
            weatherCondition = "Sunny",
            outfitStyle = "Casual",
            top = "T-Shirt",
            bottom = "Jeans",
            shoes = "Sneakers",
            accessories = listOf("Sunglasses")
        )
        val FILTERD_CLOTHES =  listOf( ClothesDto(
            temperatureRange =  TemperatureRange(low = 10.0, high = 20.0),
            weatherCondition = "Sunny",
            outfitStyle = "Casual",
            top = "T-Shirt",
            bottom = "Jeans" ,
            shoes = "Sneakers",
            accessories = listOf("Sunglasses")
        ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 15.0, high = 25.0),
                weatherCondition = "Cloudy",
                outfitStyle = "Semi-Casual",
                top = "Sweater",
                bottom = "Chinos",
                shoes = "Boots",
                accessories = listOf("Scarf")
            )
        )
    //Clothes Ui test
    val CLOTHES_SUITABLE_CASUAL = UserClothes(
        temperature = TemperatureRange(low = 10.0, high = 20.0),
        outfitStyle = "Casual",
        top = "T-Shirt",
        bottom = "Jeans",
        shoes = "Sneakers",
        accessories = listOf("Sunglasses")
    )

    val FILTERED_CLOTHES = listOf(
        UserClothes(
            temperature = TemperatureRange(low = 10.0, high = 20.0),
            outfitStyle = "Casual",
            top = "T-Shirt",
            bottom = "Jeans",
            shoes = "Sneakers",
            accessories = listOf("Sunglasses")
        ),
        UserClothes(
            temperature = TemperatureRange(low = 15.0, high = 25.0),
            outfitStyle = "Semi-Casual",
            top = "Sweater",
            bottom = "Chinos",
            shoes = "Boots",
            accessories = listOf("Scarf")
        )
    )

    val CLOTHES_LIST = UserClothes(
        temperature = TemperatureRange(low = 10.0, high = 20.0),
        outfitStyle = "Casual",
        top = "T-Shirt",
        bottom = "Jeans",
        shoes = "Sneakers",
        accessories = listOf("Sunglasses")
    )

    val CLOTHES_LIST_SEMI_CASUAL = UserClothes(
        temperature = TemperatureRange(low = 15.0, high = 25.0),
        outfitStyle = "Semi-Casual",
        top = "Sweater",
        bottom = "Chinos",
        shoes = "Boots",
        accessories = listOf("Scarf")
    )

    val EMPTY_LIST = emptyList<UserClothes>()

    val OUTFIT = UserClothes(
        temperature = TemperatureRange(low = 10.0, high = 20.0),
        outfitStyle = "Casual",
        top = "T-Shirt",
        bottom = "Jeans",
        shoes = "Sneakers",
        accessories = listOf("Sunglasses")
    )

    val EXPECTED_RESULT = "1 - Casual\n" +
            "\t- Top: T-Shirt\n" +
            "\t- Bottom: Jeans\n" +
            "\t- Shoes: Sneakers\n" +
            "\t- Accessories: Sunglasses"
}