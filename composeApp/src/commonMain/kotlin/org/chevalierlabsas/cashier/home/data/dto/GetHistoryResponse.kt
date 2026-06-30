package org.chevalierlabsas.cashier.home.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetHistoryResponse(
    val status: Int,
    val message: String,
    val histories: List<HistoryItem> = emptyList()
)

@Serializable
data class HistoryItem(
    val id: Int? = null,
    val total: Double,
    val items: Int,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val userId: String
)
