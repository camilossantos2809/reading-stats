package io.readingstats.android.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController) {
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
                    onClick = {
                        navController.navigate("book/${book.id}")
                    }
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

