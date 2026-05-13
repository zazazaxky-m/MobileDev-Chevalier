package org.chevalierlabsas.cashier.home.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostItemRequest(
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Double,
    @SerialName("userId")
    val userId: String
)
