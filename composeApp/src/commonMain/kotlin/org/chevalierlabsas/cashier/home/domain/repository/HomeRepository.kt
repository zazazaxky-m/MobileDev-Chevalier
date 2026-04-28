package org.chevalierlabsas.cashier.home.domain.repository

import org.chevalierlabsas.cashier.home.domain.Item

interface HomeRepository {
    suspend fun getItems(): List<Item>
    suspend fun postItem(item: Item): Result<Boolean>
    suspend fun deleteItem(id: Int): Result<Boolean>
    suspend fun putItem(item: Item): Result<Boolean>
    suspend fun postTransaction(): Result<Boolean>
}
