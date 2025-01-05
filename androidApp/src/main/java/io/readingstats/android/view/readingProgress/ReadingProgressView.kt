package io.readingstats.android.view.readingProgress

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import io.readingstats.android.TAG
import io.readingstats.android.components.DatePickerDialog
import io.readingstats.android.components.textfield.DateTransformation
import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val maxChar = 8

val currentDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingProgressView(
    navController: NavController
) {
    val viewModel = viewModel { ReadingProgressViewModel() }
    var lastPage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var date by remember { mutableStateOf(TextFieldValue(currentDate)) }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Reading Progress") }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        })
    }, content = { padding ->
        DatePickerDialog(showDialog = showDialog,
            onConfirm = { selectedDate = it },
            onDismiss = { showDialog = false })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                singleLine = true,
                value = date,
                onValueChange = {
                    if (it.text.length <= maxChar) {
                        date = it
                    }
                },
                visualTransformation = DateTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text("Date read") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = lastPage,
                onValueChange = { lastPage = it },
                label = { Text("Last Page") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val lastPageInt = lastPage.toIntOrNull()
                    if (lastPageInt != null) {
//                        onSave("")
                    } else {
                        // TODO: Show error to user
                        Log.d(TAG, "Invalid last page input")
                    }
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }

    })
}
