package org.chevalierlabsas.cashier.home.data.datasource

import org.chevalierlabsas.cashier.home.data.dto.CreateTransactionRequest
import org.chevalierlabsas.cashier.home.data.dto.GetHistoryResponse

interface TransactionRemoteDataSource {
    suspend fun createTransaction(request: CreateTransactionRequest): Int
    suspend fun getHistory(userId: String): Result<GetHistoryResponse>
}
