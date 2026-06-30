package org.chevalierlabsas.cashier.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashier.composeapp.generated.resources.Res
import cashier.composeapp.generated.resources.add_item_fab_label
import cashier.composeapp.generated.resources.app_name
import cashier.composeapp.generated.resources.history_topbar
import org.chevalierlabsas.cashier.core.navigation.HistoryDestination
import org.chevalierlabsas.cashier.home.data.DummyDataSource
import org.chevalierlabsas.cashier.home.domain.Item
import org.chevalierlabsas.cashier.home.presentation.components.ItemCard
import org.chevalierlabsas.cashier.home.presentation.components.SaveButton
import org.chevalierlabsas.cashier.home.presentation.components.Searchbar
import org.chevalierlabsas.cashier.home.presentation.components.SectionHeader
import org.chevalierlabsas.cashier.home.presentation.components.SelectedItemChip
import org.chevalierlabsas.cashier.home.presentation.components.TotalPriceHeader
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import org.chevalierlabsas.cashier.home.presentation.components.ItemFormBottomSheet

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    onNavigate: (Any) -> Unit,
) {
    // Buat coroutineScope
    val scope = rememberCoroutineScope()
    // Snackbar untuk melihatkan hasil ID unik.
    val snackbarHostState = remember { SnackbarHostState() }
    var isAddItemSheetOpen by remember { mutableStateOf(false) }
    var itemToEdit by remember { mutableStateOf<Item?>(null) }

    if (isAddItemSheetOpen || itemToEdit != null) {
        val isEditMode = itemToEdit != null
        ItemFormBottomSheet(
            title = if (isEditMode) "Edit Barang" else "Tambah Barang",
            initialName = itemToEdit?.name ?: "",
            initialPrice = if (isEditMode) itemToEdit?.price?.toInt()?.toString() ?: "" else "",
            onDismissRequest = { 
                isAddItemSheetOpen = false 
                itemToEdit = null
            },
            onSave = { name, price ->
                if (isEditMode) {
                    onEvent(HomeEvent.OnUpdateItem(itemToEdit!!.id, name, price))
                } else {
                    onEvent(HomeEvent.OnPostItem(name, price))
                }
                isAddItemSheetOpen = false
                itemToEdit = null
            }
        )
    }

    LaunchedEffect(state.errorMessage) {
        if (!state.errorMessage.isNullOrEmpty()) {
            snackbarHostState.showSnackbar(
                message = state.errorMessage,
                withDismissAction = true
            )
        }
    }

    // Cek state username
    LaunchedEffect(state.userName) {
        delay(500) // Beri waktu Preferences untuk load value.
        if (state.userName.isBlank()) {
            // Jika kosong maka generate.
            onEvent(HomeEvent.CreateUserName)
        } else {
            // Jika tidak, maka tampilkan di Snackbar
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Username telah dibuat: " + state.userName,
                    withDismissAction = true
                )
            }
            if (state.items.isEmpty()) {
                onEvent(HomeEvent.OnLoadData)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(Res.string.app_name))
                },
                actions = {
                    IconButton(
                        onClick = { onNavigate(HistoryDestination) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.History,
                            contentDescription = stringResource(Res.string.history_topbar)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { isAddItemSheetOpen = true },
                containerColor = MaterialTheme.colorScheme.tertiary,
                text = { Text(text = stringResource(Res.string.add_item_fab_label)) },
                icon = { Icon(Icons.Filled.Add, contentDescription = stringResource(Res.string.add_item_fab_label)) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues, // Tambahkan paddingValues kedalam contentPadding.
        ) {
            item {
                TotalPriceHeader(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    totalPrice = state.totalPrice
                )
            }
            item {
                SaveButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onSave = { onEvent(HomeEvent.OnSaveTransaction) },
                    enabled = state.selectedItems.isNotEmpty() && state.totalPrice > 0.00
                )
            }
            item {
                SectionHeader(
                    modifier = Modifier.padding(start = 16.dp, end = 4.dp),
                    title = "Barang terpilih", // String temp
                    visible = state.showSelectedItem,
                    onAction = { visible ->
                        onEvent(HomeEvent.OnSelectedItemVisibilityChange(visible))
                    }
                )
            }

            item{
                AnimatedVisibility(
                    visible = state.showSelectedItem,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    FlowRow(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        content = {
                            state.selectedItems.map { item ->
                                SelectedItemChip(
                                    onRemove = {
                                        onEvent(HomeEvent.OnRemoveItem(item))
                                    },
                                    item = item,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                        }
                    )
                }
            }

            item {
                SectionHeader(
                    modifier = Modifier.padding(start = 16.dp, end = 4.dp),
                    title ="Semua barang",
                    visible = state.showAllItem,
                    onAction = { visible ->
                        onEvent(HomeEvent.OnAllItemVisibilityChange(visible))
                    }
                )
            }

            item {
                Searchbar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    value = state.searchQuery,
                    onValueChange = { newValue ->
                        onEvent(HomeEvent.OnSearchQueryChange(newValue))
                    }
                )
            }

            items(state.items) { item ->
                AnimatedVisibility(
                    visible = state.showAllItem,
                    enter = fadeIn(),
                    exit = fadeOut()
                ){
                    ItemCard(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        name = item.name,
                        price = item.price,
                        onEdit = { itemToEdit = item },
                        onAdd = {
                            onEvent(HomeEvent.OnAddItem(item))
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        state = HomeState(),
        onEvent = {},
        onNavigate = {}
    )
}