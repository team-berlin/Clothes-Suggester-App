package com.berlin

import com.berlin.di.appModule
import com.berlin.di.dataModule
import com.berlin.di.uiModule
import com.berlin.di.useCaseModule
import com.berlin.domain.usecase.GetWeatherUseCase
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

fun main() {

    startKoin {
        modules(appModule, dataModule, useCaseModule, uiModule)
    }

    val viewer: Viewer = getKoin().get()
    val reader: Reader = getKoin().get()
    val getWeatherUseCase: GetWeatherUseCase = getKoin().get()

    runBlocking {
        start(viewer, reader, getWeatherUseCase)
    }

}

suspend fun start(
    viewer: Viewer,
    reader: Reader,
    getWeatherUseCase: GetWeatherUseCase,
) {
    viewer.show("Welcome to Weather Outfit Suggester!")

    while (true) {
        viewer.show("Enter latitude (e.g., 32.61889) or type 'exit' to quit:")
        val latInput = reader.read()?.trim() ?: break
        if (latInput.equals("exit", ignoreCase = true)) break

        val latitude = latInput.toDoubleOrNull()
        if (latitude == null) {
            viewer.show("Invalid latitude, please enter a number.")
            continue
        }

        viewer.show("Enter longitude (e.g., 35.79011) or type 'exit' to quit:")
        val lonInput = reader.read()?.trim() ?: break
        if (lonInput.equals("exit", ignoreCase = true)) break

        val longitude = lonInput.toDoubleOrNull()
        if (longitude == null) {
            viewer.show("Invalid longitude, please enter a number.")
            continue
        }

        try {
            val weatherData = getWeatherUseCase(latitude, longitude)
            viewer.show("Weather: ${weatherData.temperature}°C, Code: ${weatherData.weatherCode}")
        } catch (e: Exception) {
            viewer.show("Failed to fetch weather: ${e.message}")
        }
    }

    viewer.show("Goodbye!")
}