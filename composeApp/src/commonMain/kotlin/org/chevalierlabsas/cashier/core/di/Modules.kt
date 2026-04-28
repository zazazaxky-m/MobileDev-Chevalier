package org.chevalierlabsas.cashier.core.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.chevalierlabsas.cashier.home.data.DummyDataSource
import org.chevalierlabsas.cashier.home.data.DummyDataSourceImpl
import org.chevalierlabsas.cashier.home.presentation.HomeViewModel
import org.chevalierlabsas.cashier.history.presentation.HistoryViewModel


import org.chevalierlabsas.cashier.home.data.HomeRepositoryImpl
import org.chevalierlabsas.cashier.home.domain.repository.HomeRepository

import org.chevalierlabsas.cashier.history.data.DummyDataSource as HistoryDummyDataSource
import org.chevalierlabsas.cashier.history.data.DummyDataSourceImpl as HistoryDummyDataSourceImpl
import org.chevalierlabsas.cashier.history.data.HistoryRepositoryImpl
import org.chevalierlabsas.cashier.history.domain.repository.HistoryRepository

expect val platformModules: Module

val sharedModules = module {
    single<DummyDataSource> { DummyDataSourceImpl() }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    
    single<HistoryDummyDataSource> { HistoryDummyDataSourceImpl() }
    single<HistoryRepository> { HistoryRepositoryImpl(get()) }

    factory { HomeViewModel(get()) }
    factory { HistoryViewModel(get()) }
}
