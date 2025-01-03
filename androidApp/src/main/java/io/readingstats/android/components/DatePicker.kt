package io.readingstats.android.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    showDialog: Boolean, onDismiss: () -> Unit, onConfirm: (LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState()

    if (showDialog) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        DatePicker(
                            state = datePickerState, modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { onDismiss() }) {
                            Text("Cancel")
                        }
                        TextButton(onClick = {
                            val selectedLocalDate = datePickerState.selectedDateMillis?.let {
                                Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            } ?: LocalDate.now()
                            onConfirm(selectedLocalDate)
                            onDismiss()
                        }) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }
}

