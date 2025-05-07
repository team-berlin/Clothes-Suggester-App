package com.berlin.di


import com.berlin.presentation.MainMenuUi
import com.berlin.presentation.UiRunner
import com.berlin.presentation.io.ConsoleReader
import com.berlin.presentation.io.ConsoleViewer
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer
import com.berlin.presentation.weatherdata.GetWeatherClothesUi
import com.berlin.presentation.weatherdata.GetWeatherDataUi
import org.koin.dsl.module

val uiModule = module {
    single<Reader> { ConsoleReader() }
    single<Viewer> { ConsoleViewer() }
    single {GetWeatherDataUi(get(),get(), get())  }
    single { GetWeatherClothesUi(get(),get(),get()) }
    single {
        MainMenuUi(
           listOf(
               get<GetWeatherDataUi>(),
               get<GetWeatherClothesUi>()
           ), get (), get()
    ) }
}