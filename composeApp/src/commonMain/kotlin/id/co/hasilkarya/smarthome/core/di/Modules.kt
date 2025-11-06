package id.co.hasilkarya.smarthome.core.di

import id.co.hasilkarya.smarthome.core.network.HttpClientFactory
import id.co.hasilkarya.smarthome.core.preferences.AppPreferences
import id.co.hasilkarya.smarthome.login.data.LoginRepositoryImpl
import id.co.hasilkarya.smarthome.login.data.datasource.LoginRemoteDataSource
import id.co.hasilkarya.smarthome.login.data.datasource.LoginRemoteDataSourceImpl
import id.co.hasilkarya.smarthome.login.domain.LoginRepository
import id.co.hasilkarya.smarthome.login.presentation.LoginViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    single<AppPreferences> { AppPreferences(get()) }

    singleOf(::LoginRemoteDataSourceImpl).bind<LoginRemoteDataSource>()
    singleOf(::LoginRepositoryImpl).bind<LoginRepository>()

    factory { LoginViewModel(get()) }
}