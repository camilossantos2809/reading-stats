package io.readingstats.android.view.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

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