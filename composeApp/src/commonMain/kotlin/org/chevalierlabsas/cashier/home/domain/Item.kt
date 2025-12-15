package org.chevalierlabsas.cashier.home.domain

data class Item(
    val id: Int,
    val userId: Int,
    val name: String,
    val price: Double
)
