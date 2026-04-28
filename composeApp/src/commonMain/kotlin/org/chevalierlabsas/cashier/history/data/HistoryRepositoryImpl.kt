package org.chevalierlabsas.cashier.history.data

import org.chevalierlabsas.cashier.history.domain.TransactionHistory
import org.chevalierlabsas.cashier.history.domain.repository.HistoryRepository

class HistoryRepositoryImpl(private val dataSource: DummyDataSource) : HistoryRepository {
    override suspend fun getTransactions(): List<TransactionHistory> {
        return dataSource.getData()
    }
}
