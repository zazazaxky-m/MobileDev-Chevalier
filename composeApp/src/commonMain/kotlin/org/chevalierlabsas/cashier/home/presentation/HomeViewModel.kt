package org.chevalierlabsas.cashier.home.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import org.chevalierlabsas.cashier.home.domain.repository.HomeRepository
import org.chevalierlabsas.cashier.home.domain.Item

class HomeViewModel(
    private val repository: HomeRepository
): ViewModel() {
    private var _items: List<Item> = emptyList()
    // Mengambil username dari preferences
    private val _userName = repository.getUser()
    private val _state = MutableStateFlow(HomeState())
    val state = combine(_state, _userName) { state, userName ->
        // Menyimpan username ke State.
        state.copy(userName = userName)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeState())

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnRemoveItem -> removeItem(event.item)
            is HomeEvent.OnAddItem -> addItem(event.item)
            is HomeEvent.OnAllItemVisibilityChange -> setAllItemVisibility(event.isVisible)
            is HomeEvent.OnSelectedItemVisibilityChange -> setSelectedItemVisibility(event.isVisible)
            is HomeEvent.OnSearchQueryChange -> TODO()
            HomeEvent.OnSearchQuerySubmit -> TODO()
            HomeEvent.OnSaveTransaction -> saveTransaction()
            HomeEvent.OnLoadData -> loadData()
            HomeEvent.CreateUserName -> createUser()
            is HomeEvent.OnSetItem -> setItem(event.itemId, event.itemName, event.itemPrice)
            HomeEvent.ShowItemSheet -> showItemSheet()
            HomeEvent.DismissItemSheet -> dismissItemSheet()
            is HomeEvent.OnItemNameChanged -> changeItemName(event.name)
            is HomeEvent.OnItemPriceChanged -> changeItemPrice(event.price)
            is HomeEvent.OnDelete -> deleteItem(event.itemId)
            HomeEvent.OnPostItem -> postItem()
        }
    }

    private fun loadData() {
        if (state.value.userName.isBlank()) return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.getItems(userId = state.value.userName)
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            items = result
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                }
        }
    }

    private fun setItem(itemId: Int, itemName: String, itemPrice: String) {
        _state.update {
            it.copy(
                itemId = itemId,
                itemName = itemName,
                itemPrice = itemPrice,
                itemSheetOpen = true,
                isEditing = true,
            )
        }
    }

    private fun showItemSheet() {
        _state.update { it.copy(itemSheetOpen = true) }
    }

    private fun dismissItemSheet() {
        _state.update {
            it.copy(
                itemSheetOpen = false,
                isEditing = false,
                itemName = "",
                itemPrice = ""
            )
        }
    }

    private fun changeItemName(name: String) {
        _state.update {
            it.copy(
                itemName = name
            )
        }
    }

    private fun changeItemPrice(price: String) {
        _state.update {
            it.copy(
                itemPrice = price
            )
        }
    }

    private fun deleteItem(itemId: Int) {
        viewModelScope.launch {
            repository.deleteItem(itemId)
            loadData()
        }
    }

    private fun postItem() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val request = Item(
                id = state.value.itemId,
                userId = state.value.userName,
                name = state.value.itemName,
                price = state.value.itemPrice.toDouble(),
            )
            if (state.value.isEditing) {
                repository.putItem(request)
            } else {
                repository.postItem(request)
            }
            loadData()
            _state.update { it.copy(isLoading = false, itemSheetOpen = false) }
        }
    }

    private fun createUser() {
        // Generate username unik.
        viewModelScope.launch { repository.createUser() }
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
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.postTransaction(
                total = state.value.totalPrice.toInt(),
                userId = state.value.userName,
                items = state.value.selectedItems.count()
            )
            .onSuccess { 
                _state.update { it.copy(isLoading = false) }
            }
            .onFailure { error ->
                _state.update { it.copy(isLoading = false, errorMessage = error.message) }
            }
        }
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
