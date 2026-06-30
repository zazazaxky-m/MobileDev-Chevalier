package org.chevalierlabsas.cashier.home.data.datasource

import kotlinx.coroutines.flow.Flow
import org.chevalierlabsas.cashier.core.preferences.AppPreferences

class UserLocalDataSourceImpl(
    private val pref: AppPreferences
): UserLocalDataSource {
    override suspend fun saveUser(name: String) {
        pref.saveUserKey(name)
    }

    override fun getUser(): Flow<String> {
        return pref.getUserKey()
    }

    override suspend fun setToken(token: String) {
        pref.setToken(token)
    }

    override fun getToken(): Flow<String> {
        return pref.getToken()
    }
}
