package org.chevalierlabsas.cashier.home.presentation

import org.chevalierlabsas.cashier.home.domain.Item

sealed interface HomeEvent {
    data class OnSearchQueryChange(val query: String): HomeEvent
    data object OnSearchQuerySubmit: HomeEvent
    data class OnSelectedItemVisibilityChange(val isVisible: Boolean): HomeEvent
    data class OnAllItemVisibilityChange(val isVisible: Boolean): HomeEvent
    data class OnAddItem(val item: Item): HomeEvent
    data class OnRemoveItem(val item: Item): HomeEvent
    data object OnSaveTransaction: HomeEvent
}