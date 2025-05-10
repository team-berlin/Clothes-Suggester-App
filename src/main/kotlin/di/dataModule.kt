package com.berlin.di

import com.berlin.data.mapper.IpGeolocationMapper
import com.berlin.data.mapper.IpGeolocationMapperImpl
import com.berlin.data.mapper.WeatherMapper
import com.berlin.data.mapper.WeatherMapperImpl
import com.berlin.data.memory.ClothesDummyData
import com.berlin.data.repository.ClothesRepositoryImpl
import com.berlin.data.repository.WeatherRepositoryImpl
import com.berlin.domain.mapper.ClothesMapper
import com.berlin.domain.mapper.ClothesMapperImpl
import com.berlin.domain.repository.ClothesRepository
import com.berlin.domain.repository.WeatherRepository
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
    single { ClothesDummyData() }
    single<WeatherMapper> { WeatherMapperImpl() }
    single<IpGeolocationMapper> { IpGeolocationMapperImpl() }
    single<ClothesMapper> { ClothesMapperImpl() }
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get(), get()) }
    single<ClothesRepository> { ClothesRepositoryImpl(get())  }
}