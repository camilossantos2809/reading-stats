package io.readingstats.android.view.book

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import io.readingstats.android.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookView(navController: NavController, bookId: String?) {
    val viewModel = viewModel { BookViewModel(bookId = bookId) }
    val book by viewModel.book.collectAsState()
    val readingProgress by viewModel.readingProgress.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text("Book details") }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        })
    }) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            Text(text = book.title, style = MaterialTheme.typography.titleLarge)
            Text(text = book.author, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "$bookId - ${book.pages} pages", style = MaterialTheme.typography.bodySmall
            )
            IconButton(onClick = { navController.navigate(Screens.ReadingProgress.route) }) {
                Icon(Icons.Default.AddCircle, contentDescription = "New progress")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            LazyColumn {
                items(readingProgress) { progress ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextContent(
                            text = progress.date.formatToDate(),
                        )
                        TextContent(
                            text = "${progress.pagesRead} pages read",
                        )
                        TextContent(
                            text = "Last page: ${progress.lastPage}",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TextContent(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}