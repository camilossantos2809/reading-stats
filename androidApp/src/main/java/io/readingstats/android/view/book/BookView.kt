package io.readingstats.android.view.book

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookView(navController: NavController, bookId: String?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Text(text = "Book: $bookId")
        }

//        book?.let { bookDetails ->
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text(text = book.title, style = MaterialTheme.typography.titleLarge)
//                Text(text = book.author, style = MaterialTheme.typography.bodyMedium)
//                Text(
//                    text = "${book.pages} pages", style = MaterialTheme.typography.bodySmall
//                )
//            }
//        } ?: Text(text = "No book details available", style = MaterialTheme.typography.bodySmall)
    }
}