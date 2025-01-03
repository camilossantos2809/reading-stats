package io.readingstats.android.view.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import io.readingstats.android.BuildConfig
import io.readingstats.android.Screens
import io.readingstats.android.TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class Form(val email: String, val password: String)

enum class Status {
    None,
    Loading,
    Success,
    Error
}


class LoginViewModel(private val navController: NavHostController) : ViewModel() {
    private val _form = MutableStateFlow(
        Form(
            email = BuildConfig.DEV_EMAIL,
            password = BuildConfig.DEV_PASSWORD
        )
    )
    val form get() = _form.asStateFlow()
    private val auth: FirebaseAuth = Firebase.auth
    var status by mutableStateOf(Status.None)
        private set
    private var user by mutableStateOf(auth.currentUser)

    init {
        if (user != null) {
            status = Status.Success
            navController.navigate(Screens.Home.name)
        }
    }

    fun signIn() {
        viewModelScope.launch {
            try {
                status = Status.Loading
                auth.signInWithEmailAndPassword(_form.value.email, _form.value.password).await()
                user = auth.currentUser
                status = Status.Success
                navController.navigate(Screens.Home.name)
            } catch (e: Exception) {
                Log.w(TAG, "signInWithEmail:failure", e)
                status = Status.Error
            }
        }
    }

    fun onEmailChange(email: String) {
        _form.value = _form.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _form.value = _form.value.copy(password = password)
    }
}