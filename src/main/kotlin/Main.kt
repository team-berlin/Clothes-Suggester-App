package com.berlin

import com.berlin.di.appModule
import com.berlin.di.dataModule
import com.berlin.di.uiModule
import com.berlin.di.useCaseModule
import com.berlin.domain.usecase.GetWeatherUseCase
import com.berlin.presentation.MainMenuUi
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

fun main() {

    startKoin {
        modules(appModule, dataModule, useCaseModule, uiModule)
    }

    runBlocking {
        val mainMenuUi: MainMenuUi = getKoin().get()
        mainMenuUi.start()
    }

}

