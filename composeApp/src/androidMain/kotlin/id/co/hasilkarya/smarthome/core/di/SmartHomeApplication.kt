package id.co.hasilkarya.smarthome.core.di

import android.app.Application
import org.koin.android.ext.koin.androidContext

class SmartHomeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@SmartHomeApplication)
        }
    }
}