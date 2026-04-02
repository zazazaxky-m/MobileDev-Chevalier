package org.chevalierlabsas.cashier.history.presentation

import org.chevalierlabsas.cashier.history.domain.TransactionHistory

data class HistoryState(
    val todayTransactions: List<TransactionHistory> = emptyList(),
    val thisWeekTransactions: List<TransactionHistory> = emptyList(),
    val thisMonthTransactions: List<TransactionHistory> = emptyList(),
    val olderTransactions: List<TransactionHistory> = emptyList()
)
