package org.chevalierlabsas.cashier.history.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.chevalierlabsas.cashier.core.network.BASE_URL
import org.chevalierlabsas.cashier.core.network.V1
import org.chevalierlabsas.cashier.history.data.dto.HistoryResponse
import org.chevalierlabsas.cashier.history.data.dto.PostTransactionRequest

class HistoryRemoteDataSourceImpl(
    private val client: HttpClient
) : HistoryRemoteDataSource {

    override suspend fun postTransaction(request: PostTransactionRequest): Result<Boolean> {
        val response = client.post("$BASE_URL/$V1/history") {
            setBody(request)
        }
        return when (response.status.value) {
            201 -> Result.success(true)
            else -> Result.failure(Exception(response.status.description))
        }
    }

    override suspend fun getHistory(userId: String): Result<HistoryResponse> {
        val response = client.get("$BASE_URL/$V1/history/$userId")
        return when (response.status.value) {
            200 -> Result.success(response.body())
            else -> Result.failure(Exception(response.status.description))
        }
    }
}
