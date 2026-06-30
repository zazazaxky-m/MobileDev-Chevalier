package org.chevalierlabsas.cashier.home.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateTransactionRequest(
    val total: Int,
    val userId: String,
    val items: Int
)
