package id.co.hasilkarya.smarthome.core.di

import id.co.hasilkarya.smarthome.core.preferences.createDataStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single { createDataStore(androidContext()) }
    }