package id.co.hasilkarya.smarthome.splash.data

import androidx.datastore.core.IOException
import id.co.hasilkarya.smarthome.core.network.utils.DataError
import id.co.hasilkarya.smarthome.core.network.utils.Result
import id.co.hasilkarya.smarthome.core.preferences.AppPreferences
import id.co.hasilkarya.smarthome.splash.domain.SplashRepository
import kotlinx.coroutines.flow.Flow

class SplashRepositoryImpl(
    private val preferences: AppPreferences
): SplashRepository {
    override fun getToken(): Flow<String> {
        return preferences.getToken()
    }

    override fun getBiometricAuthEnabled(): Flow<Boolean> {
        return preferences.getBiometric()
    }
}