package id.co.hasilkarya.smarthome.core.di

import id.co.hasilkarya.smarthome.core.network.HttpClientFactory
import id.co.hasilkarya.smarthome.core.preferences.AppPreferences
import id.co.hasilkarya.smarthome.device.data.DeviceRepositoryImpl
import id.co.hasilkarya.smarthome.device.data.datasource.DeviceRemoteDataSource
import id.co.hasilkarya.smarthome.device.data.datasource.DeviceRemoteDataSourceImpl
import id.co.hasilkarya.smarthome.device.domain.DeviceRepository
import id.co.hasilkarya.smarthome.device.presentation.DeviceViewModel
import id.co.hasilkarya.smarthome.home.data.HomeRepositoryImpl
import id.co.hasilkarya.smarthome.home.data.datasource.HomeDataSource
import id.co.hasilkarya.smarthome.home.data.datasource.HomeDataSourceImpl
import id.co.hasilkarya.smarthome.home.domain.HomeRepository
import id.co.hasilkarya.smarthome.home.presentation.HomeViewModel
import id.co.hasilkarya.smarthome.login.data.LoginRepositoryImpl
import id.co.hasilkarya.smarthome.login.data.datasource.LoginRemoteDataSource
import id.co.hasilkarya.smarthome.login.data.datasource.LoginRemoteDataSourceImpl
import id.co.hasilkarya.smarthome.login.domain.LoginRepository
import id.co.hasilkarya.smarthome.login.presentation.LoginViewModel
import id.co.hasilkarya.smarthome.profile.presentation.ProfileViewModel
import id.co.hasilkarya.smarthome.splash.data.SplashRepositoryImpl
import id.co.hasilkarya.smarthome.splash.domain.SplashRepository
import id.co.hasilkarya.smarthome.splash.presentation.SplashViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    single<AppPreferences> { AppPreferences(get()) }

    singleOf(::SplashRepositoryImpl).bind<SplashRepository>()
    singleOf(::LoginRemoteDataSourceImpl).bind<LoginRemoteDataSource>()
    singleOf(::LoginRepositoryImpl).bind<LoginRepository>()
    singleOf(::HomeDataSourceImpl).bind<HomeDataSource>()
    singleOf(::HomeRepositoryImpl).bind<HomeRepository>()
    singleOf(::DeviceRemoteDataSourceImpl).bind<DeviceRemoteDataSource>()
    singleOf(::DeviceRepositoryImpl).bind<DeviceRepository>()

    factory { LoginViewModel(get()) }
    factory { SplashViewModel(get()) }
    factory { HomeViewModel(get()) }
    factory { DeviceViewModel(get()) }
    factory { ProfileViewModel(get(), get()) }
}