package com.berlin.di

import com.berlin.domain.usecase.GetWeatherUseCase
import com.berlin.domain.usecase.SuggestClothesTemperatureUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single { GetWeatherUseCase(get()) }
    single { SuggestClothesTemperatureUseCase(get(),get(), get()) }

}