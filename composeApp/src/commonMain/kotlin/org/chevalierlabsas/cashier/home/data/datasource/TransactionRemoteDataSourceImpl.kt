package org.chevalierlabsas.cashier.home.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.chevalierlabsas.cashier.core.network.BASE_URL
import org.chevalierlabsas.cashier.core.network.V1
import org.chevalierlabsas.cashier.home.data.dto.CreateTransactionRequest
import org.chevalierlabsas.cashier.home.data.dto.GetHistoryResponse

class TransactionRemoteDataSourceImpl(
    private val client: HttpClient
) : TransactionRemoteDataSource {

    override suspend fun createTransaction(request: CreateTransactionRequest): Int {
        val response = client.post(urlString = "$BASE_URL/$V1/transaction") {
            setBody(request)
        }
        return response.status.value
    }

    override suspend fun getHistory(userId: String): Result<GetHistoryResponse> {
        val response = client.get(urlString = "$BASE_URL/$V1/history/$userId")
        return when (response.status.value) {
            200 -> Result.success(response.body())
            else -> Result.failure(Exception(response.status.description))
        }
    }
}
