package org.chevalierlabsas.cashier.home.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.chevalierlabsas.cashier.home.domain.Item

@Serializable
data class ItemsItem(
    @SerialName("createdAt")
    val createdAt: String,
    
    @SerialName("price")
    val price: Int,
    
    @SerialName("name")
    val name: String,
    
    @SerialName("id")
    val id: Int,
    
    @SerialName("userId")
    val userId: String,
    
    @SerialName("updatedAt")
    val updatedAt: String
) {
    fun toDomain(): Item {
        return Item(
            id = id,
            userId = "",
            name = name,
            price = price.toDouble()
        )
    }
}
