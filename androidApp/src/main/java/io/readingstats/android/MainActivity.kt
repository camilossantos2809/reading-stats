package io.readingstats.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class Screens {
    Login, Home
}

const val TAG = "readingStats"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainRoutes()
                }
            }
        }
    }

}

@Composable
fun MainRoutes(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screens.Login.name,
    ) {
        composable(route = Screens.Login.name) {
            LoginView(navController)
        }
        composable(route = Screens.Home.name) {
            HomeView(navController)
        }
    }
}

@Composable
fun LoginView(navController: NavHostController) {
    val viewModel = viewModel { LoginViewModel(navController) }
    val formState by viewModel.form.collectAsState()

    Column {
        TextField(value = formState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email") })
        TextField(value = formState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Password") })
        Button(onClick = { viewModel.signIn() }) {
            Text(text = "Sign In")
        }
        Text(text = viewModel.status.toString())
    }
}

@Composable
fun HomeView(navController: NavHostController) {
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
                        modifier = Modifier.padding(16.dp)
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

//@Preview
//@Composable
//fun DefaultPreview() {
//    MyApplicationTheme {
//        MainRoutes("Hello, Android!")
//    }
//}
