package com.berlin.di

import com.berlin.data.mapper.WeatherMapper
import com.berlin.data.mapper.WeatherMapperImpl
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
    single { Json { ignoreUnknownKeys = true } }

    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(get())
            }
        }
    }

    single<WeatherMapper> { WeatherMapperImpl() }
}