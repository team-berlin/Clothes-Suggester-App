package presentation.weatherdata

import com.berlin.domain.exepction.ClothesSuggestionException
import com.berlin.domain.usecase.SuggestClothesTemperatureUseCase
import com.berlin.presentation.io.Viewer
import com.berlin.presentation.mapper.ClothesMapperImpl
import com.berlin.presentation.weatherdata.GetWeatherClothesUi
import helper.TestDummyData.EMPTY_LIST
import helper.TestDummyData.FILTERED_CLOTHES
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetWeatherClothesDtoUiTest {

    private lateinit var mockViewer: Viewer
    private lateinit var clothesMapper: ClothesMapperImpl
    private lateinit var mockSuggestClothesTemperatureUseCase: SuggestClothesTemperatureUseCase
    private lateinit var getWeatherClothesUi: GetWeatherClothesUi

    @BeforeEach
    fun setUp() {
        mockViewer = mockk(relaxed = true)
        clothesMapper = mockk(relaxed = true)
        mockSuggestClothesTemperatureUseCase = mockk()
        getWeatherClothesUi = GetWeatherClothesUi(mockViewer, clothesMapper, mockSuggestClothesTemperatureUseCase)
    }

    @Test
    fun `test start should call displayWeatherOutfit for each outfit`() = runBlocking {
        val outfits = FILTERED_CLOTHES

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
    fun `start should show message when ClothesSuggestionException is thrown`() = runBlocking {
        val exceptionMessage = "No Clothes Found"
        coEvery { mockSuggestClothesTemperatureUseCase() } throws ClothesSuggestionException(exceptionMessage)

        getWeatherClothesUi.start()

        coVerify { mockViewer.show("No suiable outfit available $exceptionMessage") }
        coVerify { mockViewer.show("----------------------------------------") }
    }
    @Test
    fun `start should show error message when general Exception is thrown`() = runBlocking {
        val errorMessage = "Network Error"
        coEvery { mockSuggestClothesTemperatureUseCase() } throws Exception(errorMessage)

        getWeatherClothesUi.start()

        coVerify { mockViewer.show("An error occurred while fetching the outfit $errorMessage") }
        coVerify { mockViewer.show("----------------------------------------") }
    }


}
