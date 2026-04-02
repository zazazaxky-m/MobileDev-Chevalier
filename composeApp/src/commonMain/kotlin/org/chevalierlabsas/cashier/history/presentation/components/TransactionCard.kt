package org.chevalierlabsas.cashier.history.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.chevalierlabsas.cashier.history.domain.TransactionHistory
import org.jetbrains.compose.ui.tooling.preview.Preview
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun TransactionCard(
    transaction: TransactionHistory,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TransactionRow(label = "Total Harga", value = formatRupiah(transaction.totalPrice))
            TransactionRow(label = "Total Barang", value = transaction.totalItems.toString())
            TransactionRow(label = "Tanggal", value = transaction.date)
        }
    }
}

@Composable
private fun TransactionRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private fun formatRupiah(amount: Double): String {
    val symbols = DecimalFormatSymbols(Locale("id", "ID")).apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }
    val formatter = DecimalFormat("#,##0.00", symbols)
    return "Rp. ${formatter.format(amount)}"
}

@Preview
@Composable
fun TransactionCardPreview() {
    MaterialTheme {
        TransactionCard(
            transaction = TransactionHistory(
                totalPrice = 250_000_000_000.0,
                totalItems = 10,
                date = "26 Juli 2025"
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
