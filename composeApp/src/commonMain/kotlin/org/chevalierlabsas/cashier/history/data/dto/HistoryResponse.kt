package org.chevalierlabsas.cashier.history.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class HistoryResponse(
    val status: Int? = null,
    val message: String? = null,
    val histories: List<Histories>? = null
)
