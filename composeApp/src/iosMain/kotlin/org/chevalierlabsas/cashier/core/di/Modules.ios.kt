package org.chevalierlabsas.cashier.core.di

import org.koin.core.module.Module
import org.koin.dsl.module

import org.chevalierlabsas.cashier.core.preferences.createDataStore
import org.chevalierlabsas.cashier.core.network.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual val platformModules: Module
    get() = module {
        single<HttpClient> { HttpClientFactory.create(Darwin.create()) }
        single { createDataStore() }
    }
