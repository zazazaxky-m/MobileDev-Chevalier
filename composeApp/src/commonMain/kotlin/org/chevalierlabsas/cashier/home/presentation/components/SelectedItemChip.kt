package org.chevalierlabsas.cashier.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.chevalierlabsas.cashier.home.domain.Item
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SelectedItemChip(
    modifier: Modifier = Modifier,
    onRemove: () -> Unit,
    item: Item
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(start = 16.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Text(text = item.name, style = MaterialTheme.typography.bodyMedium)
            IconButton(
                onClick = { onRemove() },
                content = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = item.name
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun SelectedItemChipPreview() {
    Surface {
        SelectedItemChip(
            item = Item(
                id = 1,
                userId = "1",
                name = "Telur 0.5 KG",
                price = 12000.0
            ),
            onRemove = { }
        )
    }
}