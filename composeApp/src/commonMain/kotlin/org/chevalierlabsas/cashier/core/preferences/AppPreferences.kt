package org.chevalierlabsas.cashier.core.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppPreferences(
    private val dataStore: DataStore<Preferences>
) {

    private val userKey = stringPreferencesKey("USER_KEY")
    private val tokenKey = stringPreferencesKey("TOKEN_KEY")

    suspend fun saveUserKey(userKey: String) {
        dataStore.edit { preferences ->
            preferences[this.userKey] = userKey
        }
    }

    fun getUserKey(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[userKey] ?: ""
        }
    }

    suspend fun setToken(token: String) {
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[tokenKey] ?: ""
        }
    }

}
