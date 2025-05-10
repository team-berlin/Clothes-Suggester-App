package presentation.weatherdata

import com.berlin.domain.usecase.SuggestClothesTemperatureUseCase
import com.berlin.presentation.io.Viewer
import com.berlin.presentation.weatherdata.GetWeatherClothesUi
import helper.TestDummyData.CLOTHES_LIST
import helper.TestDummyData.EMPTY_LIST
import helper.TestDummyData.EXPECTED_RESULT
import helper.TestDummyData.OUTFIT
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetWeatherClothesDtoUiTest {

    private lateinit var mockViewer: Viewer
    private lateinit var mockSuggestClothesTemperatureUseCase: SuggestClothesTemperatureUseCase
    private lateinit var getWeatherClothesUi: GetWeatherClothesUi

    @BeforeEach
    fun setUp() {
        mockViewer = mockk(relaxed = true)
        mockSuggestClothesTemperatureUseCase = mockk()
        getWeatherClothesUi = GetWeatherClothesUi(mockViewer, mockSuggestClothesTemperatureUseCase)
    }

    @Test
    fun `test start should call displayWeatherOutfit for each outfit`() = runBlocking {
        val outfits = CLOTHES_LIST

        coEvery { mockSuggestClothesTemperatureUseCase() } returns outfits


        getWeatherClothesUi.start()


        outfits.forEach {
            coVerify { mockViewer.show(any()) }
        }
    }

    @Test
    fun `test start should not call displayWeatherOutfit if the list is empty`() = runBlocking {
        coEvery { mockSuggestClothesTemperatureUseCase() } returns EMPTY_LIST

        getWeatherClothesUi.start()

        coVerify { mockViewer.show("\nRecommended outfit based on weather:") }
        coVerify { mockViewer.show("----------------------------------------") }
    }

    @Test
    fun `test start should display a single weather outfit correctly`() = runBlocking {
        val outfit = OUTFIT

        coEvery { mockSuggestClothesTemperatureUseCase() } returns outfit

        getWeatherClothesUi.start()

        coVerify { mockViewer.show(any()) }

        val expectedResult = EXPECTED_RESULT
        coVerify { mockViewer.show(expectedResult) }
    }
}
