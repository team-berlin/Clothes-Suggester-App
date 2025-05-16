package com.berlin.di

import com.berlin.data.dto.ClothesDto
import com.berlin.data.mapper.ClothesDataMapperImpl
import com.berlin.data.mapper.IpGeolocationMapperImpl
import com.berlin.shered.Mapper
import com.berlin.data.mapper.WeatherMapperImpl
import com.berlin.data.memory.ClothesDummyData
import com.berlin.data.remote.dto.IpGeolocationResponseDto
import com.berlin.data.remote.dto.WeatherResponseDto
import com.berlin.data.repository.ClothesRepositoryImpl
import com.berlin.data.repository.CoordinateRepositoryImpl
import com.berlin.data.repository.WeatherRepositoryImpl
import com.berlin.domain.model.Coordinates
import com.berlin.domain.model.UserClothes
import com.berlin.domain.model.WeatherData
import com.berlin.domain.repository.ClothesRepository
import com.berlin.data.service.CoordinateApi
import com.berlin.domain.repository.WeatherRepository
import com.berlin.presentation.mapper.ClothesMapperImpl
import com.berlin.presentation.model.UserClothesUi
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {

    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    single { ClothesDummyData() }
    single { ClothesDataMapperImpl() }.bind<Mapper<ClothesDto, UserClothes>>()
    single { ClothesMapperImpl() }.bind<Mapper<UserClothes, UserClothesUi>>()
    single { IpGeolocationMapperImpl() }.bind<Mapper<IpGeolocationResponseDto, Coordinates>>()
    single { WeatherMapperImpl() }.bind<Mapper<WeatherResponseDto, WeatherData>>()
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get(), get()) }
    single<ClothesRepository> { ClothesRepositoryImpl(get(), get())  }
    single<CoordinateApi> { CoordinateRepositoryImpl(get(), get()) }



}