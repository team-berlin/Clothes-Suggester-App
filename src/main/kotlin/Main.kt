package com.berlin

import com.berlin.di.appModule
import com.berlin.di.dataModule
import com.berlin.di.uiModule
import com.berlin.di.useCaseModule
import com.berlin.presentation.MainMenuUi
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

fun startApp() {
    if (org.koin.core.context.GlobalContext.getOrNull() == null) {
        startKoin {
            modules(appModule, dataModule, useCaseModule, uiModule)
        }
    }
}

fun main() {
    startApp()
    runBlocking {
        val mainMenuUi: MainMenuUi = getKoin().get()
        mainMenuUi.start()
    }
}
