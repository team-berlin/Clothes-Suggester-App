package presentation.weatherdata

import com.berlin.data.memory.UserClothesDummyData
import com.berlin.domain.model.UserClothes
import com.berlin.domain.model.WeatherData
import com.berlin.domain.repository.WeatherRepository
import com.berlin.domain.usecase.GetWeatherClothesUseCase
import com.berlin.domain.usecase.GetWeatherUseCase
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer
import com.berlin.presentation.weatherdata.GetWeatherClothesUi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetWeatherClothesUiTest {

    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private lateinit var getWeatherClothesUseCase: GetWeatherClothesUseCase
    private lateinit var getWeatherClothesUi: GetWeatherClothesUi

    @BeforeEach
    fun setup() {
        viewer = mockk(relaxed = true)
        reader = mockk()
        weatherRepository = mockk()
        getWeatherUseCase = GetWeatherUseCase(weatherRepository)
        getWeatherClothesUseCase = GetWeatherClothesUseCase()
        getWeatherClothesUi = GetWeatherClothesUi(viewer, reader, getWeatherUseCase)
    }

    @Test
    fun `should return when latitude is invalid`() = runTest {
        every { reader.readLatitude() } returns null

        getWeatherClothesUi.start()

        verify { viewer.show("Enter latitude (e.g., 32.61889) or type 'back' to return main menu :") }
        verify { viewer.show("Invalid latitude, please enter a number.") }
    }

    @Test
    fun `should return when user back in latitude`() = runTest {
        every { reader.readLatitude() } returns null

        getWeatherClothesUi.start()

        coVerify(exactly = 0) { weatherRepository.fetchWeather(any(), any()) }
    }

    @Test
    fun `should return when longitude is invalid`() = runTest {
        every { reader.readLatitude() } returns 32.0
        every { reader.readLongitude() } returns null

        getWeatherClothesUi.start()

        verify { viewer.show("Enter longitude (e.g., 35.79011) or type 'back' to return main menu :") }
        verify { viewer.show("Invalid longitude, please enter a number.") }
        coVerify(exactly = 0) { weatherRepository.fetchWeather(any(), any()) }
    }

    @Test
    fun `should show outfits when temperature is high`() = runTest {
        every { reader.readLatitude() } returns 32.0
        every { reader.readLongitude() } returns 35.0
        coEvery { weatherRepository.fetchWeather(32.0, 35.0) } returns WeatherData(temperature = 40.0, weatherCode = 2, windSpeed = 6.4)

        val weatherOutfit = UserClothes(
            outfitStyle = "Summer",
            top = "Tank top",
            bottom = "Shorts",
            shoes = "Flip flops",
            accessories = listOf("Hat", "Sunglasses")
        )
        UserClothesDummyData.userClothes = listOf(weatherOutfit)

        getWeatherClothesUi.start()

        verify {
            viewer.show("Recommended outfit based on weather:\n" +
                    "- Style: Summer\n" +
                    "- Top: Tank top\n" +
                    "- Bottom: Shorts\n" +
                    "- Shoes: Flip flops\n" +
                    "- Accessories: Hat, Sunglasses")
        }
    }

    @Test
    fun `should show outfits when coordinates are valid and weather data is fetched`() = runTest {
        every { reader.readLatitude() } returns 32.0
        every { reader.readLongitude() } returns 35.0
        coEvery { weatherRepository.fetchWeather(32.0, 35.0) } returns WEATHER_DATA

        val weatherOutfit = UserClothes(
            outfitStyle = "Casual",
            top = "T-shirt",
            bottom = "Jeans",
            shoes = "Sneakers",
            accessories = listOf("Sunglasses")
        )
        UserClothesDummyData.userClothes = listOf(weatherOutfit)

        getWeatherClothesUi.start()

        verify {
            viewer.show("Recommended outfit based on weather:\n" +
                    "- Style: Casual\n" +
                    "- Top: T-shirt\n" +
                    "- Bottom: Jeans\n" +
                    "- Shoes: Sneakers\n" +
                    "- Accessories: Sunglasses")
        }
    }

    @Test
    fun `should return null when user types back for longitude`() = runTest {
        every { reader.readLongitude() } returns null
        every { reader.readLatitude() } returns 32.0

        val result = getWeatherClothesUi.getLongitude()

        assertNull(result)
        verify { viewer.show("Enter longitude (e.g., 35.79011) or type 'back' to return main menu :") }
    }

    @Test
    fun `should return null when longitude is invalid and user types back after invalid longitude`() = runTest {
        every { reader.readLongitude() } returnsMany listOf(null)

        val result = getWeatherClothesUi.getLongitude()

        assertNull(result)
        verify { viewer.show("Enter longitude (e.g., 35.79011) or type 'back' to return main menu :") }
        verify { viewer.show("Invalid longitude, please enter a number.") }
    }

    private companion object {
        val WEATHER_DATA = WeatherData(temperature = 27.5, weatherCode = 2, windSpeed = 6.4)
    }
}
