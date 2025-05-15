package com.berlin.di

import com.berlin.data.mapper.IpGeolocationMapperImpl
import com.berlin.data.mapper.Mapper
import com.berlin.data.mapper.WeatherMapperImp
import com.berlin.data.memory.ClothesDummyData
import com.berlin.data.remote.dto.IpGeolocationResponseDto
import com.berlin.data.remote.dto.WeatherResponseDto
import com.berlin.data.repository.ClothesRepositoryImpl
import com.berlin.data.repository.WeatherRepositoryImpl
import com.berlin.domain.mapper.ClothesMapper
import com.berlin.domain.model.Coordinates
import com.berlin.domain.model.WeatherData
import com.berlin.domain.repository.ClothesRepository
import com.berlin.domain.repository.WeatherRepository
import com.berlin.presentation.mapper.ClothesMapperImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
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
    single<Mapper<WeatherResponseDto, WeatherData>> { WeatherMapperImp() }
    single<Mapper<IpGeolocationResponseDto, Coordinates>> { IpGeolocationMapperImpl() }
    single<ClothesMapper> { ClothesMapperImpl() }
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get(), get()) }
    single<ClothesRepository> { ClothesRepositoryImpl(get())  }
}