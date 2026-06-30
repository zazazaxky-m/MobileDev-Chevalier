package org.chevalierlabsas.cashier.history.domain.repository

import org.chevalierlabsas.cashier.history.domain.TransactionHistory

interface HistoryRepository {
    suspend fun getHistoryItems(userId: String): Result<List<TransactionHistory>>
    fun getUserId(): kotlinx.coroutines.flow.Flow<String>
}
