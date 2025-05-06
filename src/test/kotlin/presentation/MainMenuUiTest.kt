package presentation

import com.berlin.presentation.MainMenuUi
import com.berlin.presentation.UiRunner
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class MainMenuUiTest{
    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var mainMenuUi: MainMenuUi

    private val fakeRunner = object : UiRunner {
        override val id = 1
        override val label = "Fake"
        override suspend fun start() {}
    }
    @BeforeEach
    fun setup(){

        viewer = mockk(relaxed = true)
        reader = mockk(relaxed = true)
        mainMenuUi = MainMenuUi(listOf(fakeRunner), viewer, reader)
    }

    @Test
    fun `should return exit when user types 'exit' `(){
        coEvery { reader.read() } returns "exit"

        val ui = MainMenuUi(emptyList(), viewer, reader)

        runBlocking {
            ui.start()
        }
        verify { viewer.show("Goodbye!")  }
    }

    @Test
    fun `should handle invalid data when user entered it`(){
        coEvery { reader.read() } returnsMany  listOf("invalid","exit")
        val ui = MainMenuUi(listOf(fakeRunner), viewer, reader)

        runBlocking {
            ui.start()
        }
        verify { viewer.show("Please choose a correct number!") }
        verify { viewer.show("Goodbye!") }
    }
    @Test
    fun `should run selected runner when user enter valid id`(){
        val fake = mockk<UiRunner>(relaxed = true).apply{
            every { id } returns 1
            every { label } returns "Fake Runner"
        }
        coEvery { reader.read() } returnsMany listOf("1", "exit")

        val ui = MainMenuUi(listOf(fakeRunner), viewer, reader)

        runBlocking {
            ui.start()
        }
        coEvery { fake.start() } just Runs
    }


}