package id.co.hasilkarya.smarthome.splash.domain

import kotlinx.coroutines.flow.Flow

interface SplashRepository {

    fun getToken(): Flow<String>

    fun getBiometricAuthEnabled(): Flow<Boolean>

}