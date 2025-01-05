package io.readingstats.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.readingstats.android.view.book.BookView
import io.readingstats.android.view.home.HomeView
import io.readingstats.android.view.login.LoginView
import io.readingstats.android.view.readingProgress.ReadingProgressView

enum class Screens(val route: String) {
    Login("login"),
    Home("home"),
    Book("book/{bookId}"),
    ReadingProgress("readingProgress")
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
        startDestination = Screens.Login.route,
    ) {
        composable(route = Screens.Login.route) {
            LoginView(navController)
        }
        composable(route = Screens.Home.route) {
            HomeView(navController)
        }
        composable(
            route = Screens.Book.route,
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            BookView(navController, backStackEntry.arguments?.getString("bookId"))
        }
        composable(route = Screens.ReadingProgress.route) {
            ReadingProgressView(navController)
        }
    }
}
