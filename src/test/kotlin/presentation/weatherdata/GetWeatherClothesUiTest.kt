package presentation.weatherdata

import com.berlin.domain.mapper.ClothesMapper
import com.berlin.domain.model.WeatherData
import com.berlin.domain.repository.ClothesRepository
import com.berlin.domain.repository.WeatherRepository
import com.berlin.domain.usecase.GetWeatherUseCase
import com.berlin.domain.usecase.SuggestClothesTemperatureUseCase
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer
import com.berlin.presentation.weatherdata.GetWeatherClothesUi
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import io.mockk.verifySequence
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetWeatherClothesUiTest {

    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var clothesRepository: ClothesRepository
    private lateinit var clothesMapper: ClothesMapper
    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private lateinit var getWeatherClothesUseCase: SuggestClothesTemperatureUseCase
    private lateinit var getWeatherClothesUi: GetWeatherClothesUi

    @BeforeEach
    fun setup() {
        viewer = mockk(relaxed = true)
        reader = mockk()
        weatherRepository = mockk()
        getWeatherUseCase = GetWeatherUseCase(weatherRepository)
        clothesMapper = mockk()
        clothesRepository = mockk()
        getWeatherClothesUseCase = SuggestClothesTemperatureUseCase(clothesRepository, getWeatherUseCase, clothesMapper)
        getWeatherClothesUi = GetWeatherClothesUi(viewer, reader, getWeatherUseCase, getWeatherClothesUseCase)
    }

    @Test
    fun `should return when latitude is invalid`() = runTest {
        every { reader.readLatitude() } returns null

        getWeatherClothesUi.start()

        verifySequence {
            viewer.show("Enter latitude (e.g., 32.61889) or type 'back' to return main menu :")
            viewer.show("Invalid latitude, please enter a number.")
        }

        coVerify(exactly = 0) { weatherRepository.fetchWeather(any(), any()) }
    }

    @Test
    fun `should return when user back in latitude`() = runTest {
        every { reader.readLatitude() } returns null

        getWeatherClothesUi.start()

        verify {
            viewer.show("Enter latitude (e.g., 32.61889) or type 'back' to return main menu :")
            viewer.show("Invalid latitude, please enter a number.")
        }

        coVerify(exactly = 0) { weatherRepository.fetchWeather(any(), any()) }
    }

    @Test
    fun `should return when longitude is invalid`() = runTest {
        every { reader.readLatitude() } returns 32.0
        every { reader.readLongitude() } returns null

        getWeatherClothesUi.start()

        verifySequence {
            viewer.show("Enter latitude (e.g., 32.61889) or type 'back' to return main menu :")
            viewer.show("Enter longitude (e.g., 35.79011) or type 'back' to return main menu :")
            viewer.show("Invalid longitude, please enter a number.")
        }

        coVerify(exactly = 0) { weatherRepository.fetchWeather(any(), any()) }
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
        every { reader.readLongitude() } returnsMany listOf(null, null)

        val result = getWeatherClothesUi.getLongitude()

        assertNull(result)

        verifyOrder {
            viewer.show("Enter longitude (e.g., 35.79011) or type 'back' to return main menu :")
            viewer.show("Invalid longitude, please enter a number.")
        }
    }

    @Test
    fun `should return when latitude dis invalid`() = runTest {
        every { reader.readLatitude() } returns null

        getWeatherClothesUi.start()

        verifyOrder {
            viewer.show("Enter latitude (e.g., 32.61889) or type 'back' to return main menu :")
            viewer.show("Invalid latitude, please enter a number.")
        }
    }

    private companion object {
        val WEATHER_DATA = WeatherData(temperature = 27.5, windSpeed = 6.4)
    }
}
