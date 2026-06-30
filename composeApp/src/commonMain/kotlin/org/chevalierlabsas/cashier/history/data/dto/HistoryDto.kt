package org.chevalierlabsas.cashier.history.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostTransactionRequest(
    val userId: String,
    val total: Double,
    val items: Int
)

@Serializable
data class HistoryResponse(
    val status: Int,
    val message: String,
    val histories: List<HistoryDto> = emptyList()
)

@Serializable
data class HistoryDto(
    val id: Int? = null,
    val total: Double,
    val items: Int,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val userId: String
)
