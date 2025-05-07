package com.berlin.di

import com.berlin.data.repository.WeatherRepositoryImpl
import com.berlin.domain.repository.WeatherRepository
import org.koin.dsl.module

val appModule = module {

    single<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
}