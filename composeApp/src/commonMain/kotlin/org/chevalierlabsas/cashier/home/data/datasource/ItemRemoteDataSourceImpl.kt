package org.chevalierlabsas.cashier.home.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.delete
import io.ktor.client.request.setBody
import org.chevalierlabsas.cashier.core.network.BASE_URL
import org.chevalierlabsas.cashier.core.network.V2
import org.chevalierlabsas.cashier.home.data.dto.ItemsResponse
import org.chevalierlabsas.cashier.home.data.dto.PostItemRequest

class ItemRemoteDataSourceImpl(
    private val client: HttpClient
): ItemRemoteDataSource {
    override suspend fun getItems(userId: String): Result<ItemsResponse> {
        val response = client.get(urlString = "${BASE_URL}/$V2/item/$userId")
        return when(response.status.value) {
            200 -> Result.success(response.body())
            else -> Result.failure(Exception(response.status.description))
        }
    }

    override suspend fun updateItem(
        id: Int,
        request: PostItemRequest
    ): Result<Boolean> {
        val response = client.post("$BASE_URL/$V2/item/$id") {
            setBody(request)
        }
        return when (response.status.value) {
            200 -> Result.success(true)
            else -> Result.failure(Exception(response.status.description))
        }
    }

    override suspend fun postItem(request: PostItemRequest): Result<Boolean> {
        val response = client.post("$BASE_URL/$V2/item") {
            setBody(request)
        }
        return when (response.status.value) {
            201 -> Result.success(true)
            else -> Result.failure(Exception(response.status.description))
        }
    }

    override suspend fun deleteItem(id: Int): Result<Boolean> {
        val response = client.delete("$BASE_URL/$V2/item/$id")
        return when (response.status.value) {
            200 -> Result.success(true)
            else -> Result.failure(Exception(response.status.description))
        }
    }
}
