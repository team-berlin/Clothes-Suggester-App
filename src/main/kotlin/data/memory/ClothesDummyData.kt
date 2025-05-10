package com.berlin.data.memory

import com.berlin.data.local.dto.ClothesDto
import com.berlin.domain.model.TemperatureRange

class ClothesDummyData {
    fun getClothesDummyData(): List<ClothesDto>{
        return listOf(
            ClothesDto(
                temperatureRange = TemperatureRange(low = 30.0, high = 40.0),
                weatherCondition = "hot",
                outfitStyle = "casual",
                top = "Tank top",
                bottom = "Shorts",
                shoes = "Flip-flops",
                accessories = listOf("Cap", "Sunglasses")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 20.0, high = 30.0),
                weatherCondition = "sunny",
                outfitStyle = "semi-formal",
                top = "Polo shirt",
                bottom = "Chinos",
                shoes = "Loafers",
                accessories = listOf("Watch", "Belt")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 0.0, high = 10.0),
                weatherCondition = "rainy",
                outfitStyle = "casual",
                top = "Raincoat",
                bottom = "Jeans",
                shoes = "Boots",
                accessories = listOf("Umbrella", "Scarf")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 25.0, high = 35.0),
                weatherCondition = "sunny",
                outfitStyle = "casual",
                top = "Short sleeve shirt",
                bottom = "Skirt",
                shoes = "Sandals",
                accessories = listOf("Hat", "Sunglasses")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 15.0, high = 25.0),
                weatherCondition = "cloudy",
                outfitStyle = "formal",
                top = "Blazer",
                bottom = "Dress pants",
                shoes = "Oxfords",
                accessories = listOf("Watch", "Tie")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = -5.0, high = 5.0),
                weatherCondition = "snowy",
                outfitStyle = "casual",
                top = "Sweater",
                bottom = "Jeans",
                shoes = "Sneakers",
                accessories = listOf("Gloves", "Scarf")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 30.0, high = 40.0),
                weatherCondition = "hot",
                outfitStyle = "casual",
                top = "Sleeveless top",
                bottom = "Shorts",
                shoes = "Flip-flops",
                accessories = listOf("Necklace", "Sunglasses")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 20.0, high = 30.0),
                weatherCondition = "partly cloudy",
                outfitStyle = "casual",
                top = "T-shirt",
                bottom = "Joggers",
                shoes = "Sneakers",
                accessories = listOf("Cap", "Watch")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 5.0, high = 15.0),
                weatherCondition = "rainy",
                outfitStyle = "semi-formal",
                top = "Trench coat",
                bottom = "Trousers",
                shoes = "Chelsea boots",
                accessories = listOf("Umbrella", "Leather gloves")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 15.0, high = 25.0),
                weatherCondition = "cloudy",
                outfitStyle = "casual",
                top = "Hoodie",
                bottom = "Jeans",
                shoes = "Running shoes",
                accessories = listOf("Backpack", "Cap")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = -5.0, high = 5.0),
                weatherCondition = "snowy",
                outfitStyle = "formal",
                top = "Wool coat",
                bottom = "Dress pants",
                shoes = "Leather boots",
                accessories = listOf("Scarf", "Gloves")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 25.0, high = 35.0),
                weatherCondition = "sunny",
                outfitStyle = "sporty",
                top = "Sports tank",
                bottom = "Running shorts",
                shoes = "Running shoes",
                accessories = listOf("Sweatband", "Cap")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 10.0, high = 20.0),
                weatherCondition = "windy",
                outfitStyle = "casual",
                top = "Windbreaker",
                bottom = "Cargo pants",
                shoes = "Sneakers",
                accessories = listOf("Backpack", "Cap")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 5.0, high = 10.0),
                weatherCondition = "rainy",
                outfitStyle = "casual",
                top = "Hooded jacket",
                bottom = "Leggings",
                shoes = "Rain boots",
                accessories = listOf("Umbrella", "Crossbody bag")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 20.0, high = 30.0),
                weatherCondition = "sunny",
                outfitStyle = "boho",
                top = "Flowy blouse",
                bottom = "Maxi skirt",
                shoes = "Sandals",
                accessories = listOf("Bracelets", "Hat")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 30.0, high = 40.0),
                weatherCondition = "hot",
                outfitStyle = "beach",
                top = "Linen shirt",
                bottom = "Swim shorts",
                shoes = "Slides",
                accessories = listOf("Sunglasses", "Beach bag")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 15.0, high = 25.0),
                weatherCondition = "cloudy",
                outfitStyle = "casual",
                top = "Long sleeve tee",
                bottom = "Jeans",
                shoes = "Sneakers",
                accessories = listOf("Watch", "Messenger bag")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 5.0, high = 15.0),
                weatherCondition = "windy",
                outfitStyle = "semi-formal",
                top = "Cardigan",
                bottom = "Slacks",
                shoes = "Loafers",
                accessories = listOf("Scarf", "Leather bag")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 25.0, high = 35.0),
                weatherCondition = "sunny",
                outfitStyle = "casual",
                top = "Crop top",
                bottom = "Denim skirt",
                shoes = "Flat sandals",
                accessories = listOf("Anklet", "Sunglasses")
            ),
            ClothesDto(
                temperatureRange = TemperatureRange(low = 10.0, high = 20.0),
                weatherCondition = "rainy",
                outfitStyle = "casual",
                top = "Light sweater",
                bottom = "Culottes",
                shoes = "Water-resistant shoes",
                accessories = listOf("Rain hat", "Umbrella")
            )
        )
    }
}