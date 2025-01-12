package io.readingstats.android.view.book

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.readingstats.android.Screens
import io.readingstats.android.domain.formatToDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookView(navController: NavController, bookId: String?) {
    val viewModel = viewModel { BookViewModel(bookId = bookId) }
    val book by viewModel.book.collectAsState()
    val readingProgress by viewModel.readingProgress.collectAsState()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val isScreenFocused = currentBackStackEntry?.destination?.route == Screens.Book.route
    val loading = viewModel.loading.collectAsState()

    LaunchedEffect(isScreenFocused) {
        if (isScreenFocused) {
            viewModel.fetchBook()
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Book details") }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { navController.navigate("readingProgress/${bookId}") }) {
                    Icon(
                        Icons.Default.AddCircle,
                        contentDescription = "New progress",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            if (loading.value) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(readingProgress) { progress ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
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
                            IconButton(
                                onClick = {
                                    viewModel.deleteProgress(progress.id)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Red
                                )
                            }
                        }
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