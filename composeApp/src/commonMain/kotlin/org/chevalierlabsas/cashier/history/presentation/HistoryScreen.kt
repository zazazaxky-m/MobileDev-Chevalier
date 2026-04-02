package org.chevalierlabsas.cashier.history.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cashier.composeapp.generated.resources.Res
import cashier.composeapp.generated.resources.history_topbar
import cashier.composeapp.generated.resources.navigate_back
import org.chevalierlabsas.cashier.history.domain.TransactionHistory
import org.chevalierlabsas.cashier.history.presentation.components.TransactionCard
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    state: HistoryState,
    onNavigateBack: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Res.string.history_topbar))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.navigate_back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (state.todayTransactions.isNotEmpty()) {
                item {
                    SectionTitle(title = "Hari ini")
                }
                items(state.todayTransactions) { transaction ->
                    TransactionCard(transaction = transaction)
                }
            }

            if (state.thisWeekTransactions.isNotEmpty()) {
                item {
                    SectionTitle(title = "Minggu ini")
                }
                items(state.thisWeekTransactions) { transaction ->
                    TransactionCard(transaction = transaction)
                }
            }

            if (state.thisMonthTransactions.isNotEmpty()) {
                item {
                    SectionTitle(title = "Bulan ini")
                }
                items(state.thisMonthTransactions) { transaction ->
                    TransactionCard(transaction = transaction)
                }
            }

            if (state.olderTransactions.isNotEmpty()) {
                item {
                    SectionTitle(title = "Lebih lama")
                }
                items(state.olderTransactions) { transaction ->
                    TransactionCard(transaction = transaction)
                }
            }
        }
    }

}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}

@Preview
@Composable
fun HistoryScreenPreview() {
    MaterialTheme {
        HistoryScreen(
            state = HistoryState(
                todayTransactions = listOf(
                    TransactionHistory(250_000_000_000.0, 10, "26 Juli 2025"),
                    TransactionHistory(250_000_000_000.0, 10, "26 Juli 2025"),
                ),
                thisWeekTransactions = listOf(
                    TransactionHistory(250_000_000_000.0, 10, "26 Juli 2025"),
                    TransactionHistory(250_000_000_000.0, 10, "26 Juli 2025"),
                ),
                thisMonthTransactions = listOf(
                    TransactionHistory(250_000_000_000.0, 10, "26 Juli 2025"),
                    TransactionHistory(250_000_000_000.0, 10, "26 Juli 2025"),
                ),
            ),
            onNavigateBack = {}
        )
    }
}
