package org.chevalierlabsas.cashier.home.data.datasource

import org.chevalierlabsas.cashier.home.data.dto.ItemsResponse
import org.chevalierlabsas.cashier.home.data.dto.PostItemRequest

interface ItemRemoteDataSource {
    suspend fun getItems(userId: String): Result<ItemsResponse>
    suspend fun updateItem(id: Int, request: PostItemRequest): Result<Boolean>
    suspend fun postItem(request: PostItemRequest): Result<Boolean>
    suspend fun deleteItem(id: Int): Result<Boolean>
}
