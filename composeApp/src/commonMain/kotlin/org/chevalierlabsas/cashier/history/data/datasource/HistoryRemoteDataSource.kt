package org.chevalierlabsas.cashier.history.data.datasource

import org.chevalierlabsas.cashier.history.data.dto.HistoryResponse

interface HistoryRemoteDataSource {
    suspend fun getHistory(userId: String): Result<HistoryResponse>
}
