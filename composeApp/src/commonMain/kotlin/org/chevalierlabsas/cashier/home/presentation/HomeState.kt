package org.chevalierlabsas.cashier.home.presentation

import org.chevalierlabsas.cashier.home.domain.Item

data class HomeState(
    val searchQuery: String = "",
    val showSelectedItem: Boolean = true,
    val showAllItem: Boolean = true,
    val items: List<Item> = emptyList(),
    val selectedItems: List<Item> = emptyList(),
    val totalPrice: Double = 0.0,
    val userName: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val itemSheetOpen: Boolean = false,
    val itemId: Int = 0,
    val itemName: String = "",
    val itemPrice: String = "",
    val isEditing: Boolean = false
)