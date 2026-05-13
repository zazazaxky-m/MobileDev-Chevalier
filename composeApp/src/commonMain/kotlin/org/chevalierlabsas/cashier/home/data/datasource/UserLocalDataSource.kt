package org.chevalierlabsas.cashier.home.data.datasource

import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {

    suspend fun saveUser(name: String)

    fun getUser(): Flow<String>

}
