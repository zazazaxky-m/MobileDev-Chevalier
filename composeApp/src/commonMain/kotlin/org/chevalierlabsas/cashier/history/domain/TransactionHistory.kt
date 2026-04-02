package org.chevalierlabsas.cashier.history.domain

data class TransactionHistory(
    val totalPrice: Double,
    val totalItems: Int,
    val date: String
)
