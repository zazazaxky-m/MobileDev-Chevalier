package org.chevalierlabsas.cashier.history.domain.repository

import org.chevalierlabsas.cashier.history.domain.TransactionHistory

interface HistoryRepository {
    suspend fun getTransactions(): List<TransactionHistory>
}
