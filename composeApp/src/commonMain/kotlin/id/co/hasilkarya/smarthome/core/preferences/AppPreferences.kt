package id.co.hasilkarya.smarthome.core.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppPreferences(
    private val dataStore: DataStore<Preferences>
) {

    private val tokenKey = stringPreferencesKey("token")
    private val userIdKey = stringPreferencesKey("userId")

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[tokenKey] ?: ""
        }
    }

    suspend fun saveUserId(userId: String) {
        dataStore.edit { preferences ->
            preferences[userIdKey] = userId
        }
    }

    fun getUserId(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[userIdKey] ?: ""
        }
    }

}