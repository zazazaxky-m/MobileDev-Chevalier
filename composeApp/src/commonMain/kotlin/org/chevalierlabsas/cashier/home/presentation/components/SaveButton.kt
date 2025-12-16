package org.chevalierlabsas.cashier.home.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashier.composeapp.generated.resources.Res
import cashier.composeapp.generated.resources.save_button_label
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    onSave: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        onClick = onSave,
        shape = RoundedCornerShape(8.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(stringResource(Res.string.save_button_label))
    }
}

@Preview
@Composable
fun SaveButtonPreview() {
    Surface {
        SaveButton(
            onSave = { },
            enabled = true
        )
    }
}