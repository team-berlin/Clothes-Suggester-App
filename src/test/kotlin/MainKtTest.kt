package com.berlin

import com.berlin.presentation.MainMenuUi
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer
import com.google.common.base.Verify.verify
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class MainTest {

    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var mainMenuUi: MainMenuUi

    @BeforeEach
    fun setup() {
        viewer = mockk(relaxed = true)
        reader = mockk()
        mainMenuUi = MainMenuUi(
            runner = listOf(),
            viewer = viewer,
            reader = reader
        )
    }

    @Test
    fun `should start main menu ui correctly`() {
        every { reader.read() } returns "exit"

        runBlocking {
            mainMenuUi.start()
        }

        verify { viewer.show("Goodbye!") }
    }
}

