import com.berlin.domain.model.WeatherData
import com.berlin.domain.usecase.GetWeatherUseCase
import com.berlin.presentation.io.Viewer
import com.berlin.presentation.weatherdata.GetWeatherDataUi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetWeatherDataUiTest {

    private lateinit var mockViewer: Viewer
    private lateinit var mockGetWeatherUseCase: GetWeatherUseCase
    private lateinit var getWeatherDataUi: GetWeatherDataUi

    @BeforeEach
    fun setUp() {
        mockViewer = mockk(relaxed = true)
        mockGetWeatherUseCase = mockk()
        getWeatherDataUi = GetWeatherDataUi(mockViewer, mockGetWeatherUseCase)
    }

    @Test
    fun `test start should display weather data when the API call is successful`() = runBlocking {
        val weatherData = WeatherData(temperature = 25.0, windSpeed = 10.0)
        coEvery { mockGetWeatherUseCase() } returns weatherData

        getWeatherDataUi.start()

        coVerify { mockViewer.show("Weather: 25.0°C") }
    }

    @Test
    fun `test start should display an error message when the API call fails`() = runBlocking {
        val exceptionMessage = "Network error"
        coEvery { mockGetWeatherUseCase() } throws Exception(exceptionMessage)

        getWeatherDataUi.start()

        coVerify { mockViewer.show("Failed to fetch weather: $exceptionMessage") }
    }

    @Test
    fun `test start should display a generic error message when an unknown error occurs`() =
        runBlocking {
            coEvery { mockGetWeatherUseCase() } throws Exception("Unknown error")

            getWeatherDataUi.start()

            coVerify { mockViewer.show("Failed to fetch weather: Unknown error") }
        }
}
