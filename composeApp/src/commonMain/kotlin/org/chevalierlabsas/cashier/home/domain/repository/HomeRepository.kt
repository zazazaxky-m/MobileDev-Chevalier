package org.chevalierlabsas.cashier.home.domain.repository

import org.chevalierlabsas.cashier.home.domain.Item

interface HomeRepository {
    suspend fun getItems(userId: String): Result<List<Item>>
    suspend fun postItem(item: Item): Result<Boolean>
    suspend fun deleteItem(id: Int): Result<Boolean>
    suspend fun putItem(item: Item): Result<Boolean>
    suspend fun postTransaction(): Result<Boolean>
    suspend fun createUser()
    suspend fun saveUser(user: String): Result<Boolean>
    fun getUser(): kotlinx.coroutines.flow.Flow<String>
}
