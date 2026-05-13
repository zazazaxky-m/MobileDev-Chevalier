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
import org.chevalierlabsas.cashier.core.preferences.AppPreferences
import org.chevalierlabsas.cashier.home.data.datasource.UserLocalDataSource
import org.chevalierlabsas.cashier.home.data.datasource.UserLocalDataSourceImpl
import org.chevalierlabsas.cashier.home.data.datasource.UserRemoteDataSource
import org.chevalierlabsas.cashier.home.data.datasource.UserRemoteDataSourceImpl
import org.chevalierlabsas.cashier.home.data.datasource.ItemRemoteDataSource
import org.chevalierlabsas.cashier.home.data.datasource.ItemRemoteDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

expect val platformModules: Module

val sharedModules = module {
    /* Define modules here */
    /* From DataSourceImpl binded with interface -> RepositoryImpl binded with interface */
    singleOf(::DummyDataSourceImpl).bind<DummyDataSource>()
    singleOf(::UserLocalDataSourceImpl).bind<UserLocalDataSource>()
    singleOf(::UserRemoteDataSourceImpl).bind<UserRemoteDataSource>()
    singleOf(::ItemRemoteDataSourceImpl).bind<ItemRemoteDataSource>()
    singleOf(::HomeRepositoryImpl).bind<HomeRepository>()
    
    single<HistoryDummyDataSource> { HistoryDummyDataSourceImpl() }
    single<HistoryRepository> { HistoryRepositoryImpl(get()) }

    factory { AppPreferences(get()) }
    factory { HomeViewModel(get()) }
    factory { HistoryViewModel(get()) }
}
