package io.readingstats.android.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.readingstats.android.MyApplicationTheme

@Composable
fun HomeView() {
    val viewModel = viewModel { HomeViewModel() }
    val goal by viewModel.goal.collectAsState()
    val books by viewModel.books.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Goal pages: ${goal.pages}", style = MaterialTheme.typography.titleLarge)
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(books) { book ->
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = book.title, style = MaterialTheme.typography.titleLarge)
                        Text(text = book.author, style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = "${book.pages} pages", style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        HomeView()
    }
}