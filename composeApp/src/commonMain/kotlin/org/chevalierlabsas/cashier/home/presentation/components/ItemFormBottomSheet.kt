package org.chevalierlabsas.cashier.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemFormBottomSheet(
    itemName: String,
    itemPrice: String,
    isEditing: Boolean,
    onDismissRequest: () -> Unit,
    onItemNameChange: (String) -> Unit,
    onItemPriceChange: (String) -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp, top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (isEditing) "Edit Barang" else "Tambah Barang",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Nama barang",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = itemName,
                    onValueChange = onItemNameChange,
                    placeholder = { Text("Beri nama barang") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Title, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    )
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Harga barang",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = itemPrice,
                    onValueChange = onItemPriceChange,
                    placeholder = { Text("000,00") },
                    leadingIcon = { 
                        Text(
                            text = "Rp.", 
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        ) 
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (isEditing) {
                    OutlinedButton(
                        onClick = onDelete,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus")
                        Text(text = "Hapus", modifier = Modifier.padding(start = 8.dp))
                    }
                }
                
                SaveButton(
                    modifier = Modifier.weight(if (isEditing) 1f else 2f),
                    enabled = itemName.isNotBlank() && itemPrice.isNotBlank(),
                    onSave = onSave
                )
            }
        }
    }
}
