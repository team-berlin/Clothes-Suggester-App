package com.berlin.di

import com.berlin.presentation.io.ConsoleReader
import com.berlin.presentation.io.ConsoleViewer
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer
import org.koin.dsl.module

val uiModule = module {

    single<Reader> { ConsoleReader() }
    single<Viewer> { ConsoleViewer() }
}