package org.chevalierlabsas.cashier.home.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.chevalierlabsas.cashier.home.data.DummyDataSource
import kotlinx.coroutines.flow.update
import org.chevalierlabsas.cashier.home.domain.Item

class HomeViewModel: ViewModel() {
    private val _items = DummyDataSource().getData()
    private val _state = MutableStateFlow(HomeState(items = _items))
    val state = _state.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnRemoveItem -> removeItem(event.item)
            is HomeEvent.OnAddItem -> addItem(event.item)
            is HomeEvent.OnAllItemVisibilityChange -> setAllItemVisibility(event.isVisible)
            is HomeEvent.OnSelectedItemVisibilityChange -> setSelectedItemVisibility(event.isVisible)
            is HomeEvent.OnSearchQueryChange -> TODO()
            HomeEvent.OnSearchQuerySubmit -> TODO()
            HomeEvent.OnSaveTransaction -> saveTransaction()
        }
    }

    private fun removeItem(item: Item) {
        _state.update {
            it.copy(
                selectedItems = it.selectedItems - item,
                totalPrice = it.totalPrice - item.price
            )
        }
    }

    private fun saveTransaction() {
        TODO("Save Data to API.")
    }

    private fun addItem(item: Item) {
        _state.update {
            it.copy(
                selectedItems = it.selectedItems + item,
                totalPrice = it.totalPrice + item.price
            )
        }
    }

    private fun setAllItemVisibility(visible: Boolean) {
        _state.update { it.copy(showAllItem = visible) }
    }

    private fun setSelectedItemVisibility(visible: Boolean) {
        _state.update { it.copy(showSelectedItem = visible) }
    }

    private fun search() {
        if (state.value.searchQuery.isNotBlank()) {
            _state.update {
                it.copy(
                    items = _state.value.items.filter { item ->
                        item.name.contains(_state.value.searchQuery, ignoreCase = true)
                    }
                )
            }
        } else {
            _state.update { it.copy(items = _items) }
        }
    }

    private fun updateQuery(query: String) {
        _state.update { it.copy(searchQuery = query) }
        if (state.value.searchQuery.isBlank()) {
            _state.update { it.copy(items = _items) }
        }
    }
}
