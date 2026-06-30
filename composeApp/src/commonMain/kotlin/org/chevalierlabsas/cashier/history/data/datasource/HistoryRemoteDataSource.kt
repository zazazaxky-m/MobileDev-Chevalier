package org.chevalierlabsas.cashier.history.data.datasource

import org.chevalierlabsas.cashier.history.data.dto.HistoryResponse
import org.chevalierlabsas.cashier.history.data.dto.PostTransactionRequest

interface HistoryRemoteDataSource {
    suspend fun postTransaction(request: PostTransactionRequest): Result<Boolean>
    suspend fun getHistory(userId: String): Result<HistoryResponse>
}
