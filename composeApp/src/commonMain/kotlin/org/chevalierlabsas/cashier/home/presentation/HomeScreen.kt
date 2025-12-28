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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashier.composeapp.generated.resources.Res
import cashier.composeapp.generated.resources.add_item_fab_label
import cashier.composeapp.generated.resources.app_name
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val allItems = DummyDataSource().getData()
    var totalPrice by remember { mutableStateOf(0.00) }
    val selectedItems = remember { mutableStateListOf<Item>() }
    var showSelectedItem by remember { mutableStateOf(true) }
    var showAllItem by remember { mutableStateOf(true) }
    
    // State untuk Search Bar
    var searchQuery by remember { mutableStateOf("") }
    var filteredItems by remember { mutableStateOf(allItems) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(Res.string.app_name))
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { TODO("Add Item.") },
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
                    totalPrice = totalPrice
                )
            }
            item {
                SaveButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onSave = { TODO("Save data.") },
                    enabled = selectedItems.isNotEmpty() && totalPrice > 0.00 // Opsional
                )
            }
            item {
                SectionHeader(
                    modifier = Modifier.padding(start = 16.dp, end = 4.dp),
                    title = "Barang terpilih",
                    visible = showSelectedItem,
                    onAction = { visible ->
                        showSelectedItem = visible
                    }
                )
            }

            item{
                AnimatedVisibility(
                    visible = showSelectedItem,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    FlowRow(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        content = {
                            selectedItems.map { item ->
                                SelectedItemChip(
                                    onRemove = {
                                        selectedItems.remove(item)
                                        totalPrice -= item.price
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
                    visible = showAllItem,
                    onAction = { visible ->
                        showAllItem = visible
                    }
                )
            }

            item {
                Searchbar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    value = searchQuery,
                    onValueChange = { newValue ->
                        searchQuery = newValue
                        filteredItems = if (newValue.isEmpty()) {
                            allItems
                        } else {
                            allItems.filter { item ->
                                item.name.contains(newValue, ignoreCase = true)
                            }
                        }
                    }
                )
            }

            items(filteredItems) { item ->
                AnimatedVisibility(
                    visible = showAllItem,
                    enter = fadeIn(),
                    exit = fadeOut()
                ){
                    ItemCard(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        name = item.name,
                        price = item.price,
                        onEdit = { },
                        onAdd = {
                            selectedItems.add(item)
                            totalPrice += item.price
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
    HomeScreen()
}