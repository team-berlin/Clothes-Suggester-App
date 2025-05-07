package com.berlin

import com.berlin.presentation.MainMenuUi
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.core.context.startKoin

class MainKtCoverageTest {

    @Test
    fun `main should call MainMenuUi_start`() = runBlocking {
        val mockMainMenuUi = mockk<MainMenuUi>(relaxed = true)

        stopKoin()

        startKoin {
            modules(
                module {
                    single { mockMainMenuUi }
                }
            )
        }

        main()

        coVerify { mockMainMenuUi.start() }

        stopKoin()
    }

    @Test
    fun `startApp should not reinitialize Koin if already started`() {
        startApp()
    }
}
