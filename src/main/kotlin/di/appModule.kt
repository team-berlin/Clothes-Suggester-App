package com.berlin.di

import org.koin.dsl.module

val appModule = module {
    includes(
        dataModule,
        uiModule,
        useCaseModule
    )
}