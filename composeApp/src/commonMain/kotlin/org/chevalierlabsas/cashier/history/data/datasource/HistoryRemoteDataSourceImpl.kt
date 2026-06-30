package org.chevalierlabsas.cashier.history.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.chevalierlabsas.cashier.core.network.BASE_URL
import org.chevalierlabsas.cashier.core.network.V1
import org.chevalierlabsas.cashier.history.data.dto.HistoryResponse

class HistoryRemoteDataSourceImpl(
    private val client: HttpClient
) : HistoryRemoteDataSource {
    override suspend fun getHistory(userId: String): Result<HistoryResponse> {
        val response = client.get("$BASE_URL/$V1/history/$userId")
        return when (response.status.value) {
            200 -> Result.success(response.body())
            else -> Result.failure(Exception(response.status.description))
        }
    }
}
