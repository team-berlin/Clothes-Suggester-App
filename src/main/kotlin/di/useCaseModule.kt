package com.berlin.di

import com.berlin.domain.usecase.GetWeatherUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single { GetWeatherUseCase(get()) }
}