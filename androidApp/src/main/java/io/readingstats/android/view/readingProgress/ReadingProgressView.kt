package io.readingstats.android.view.readingProgress

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import io.readingstats.android.components.textfield.DateTransformation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingProgressView(
    navController: NavController,
    bookId:String?
) {
    val viewModel = viewModel { ReadingProgressViewModel() }
    val formData by viewModel.formData.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text("Reading Progress") }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        })
    }, content = { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                singleLine = true,
                value = formData.date,
                onValueChange = viewModel::updateDate,
                visualTransformation = DateTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text("Date read") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = formData.lastPage,
                onValueChange = viewModel::updateLastPage,
                label = { Text("Last Page") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )
            if (!formData.errorMessage.isNullOrEmpty()) {
                Text(text = formData.errorMessage ?: "Unknown Error", color = Color.Red)
            }
            Button(
                onClick = {
                    viewModel.saveProgress(bookId)
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }

    })
}
