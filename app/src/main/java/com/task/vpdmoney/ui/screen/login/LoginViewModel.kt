package com.task.vpdmoney.ui.screen.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth
import com.task.vpdmoney.data.UserAccount
import com.task.vpdmoney.repo.AccountRepository
import com.task.vpdmoney.util.RandomUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userAccountRepository: AccountRepository
) : ViewModel() {

    private val uiState = MutableStateFlow(LoginState())
    fun consumableState() = uiState.asStateFlow()
    private var auth: FirebaseAuth = Firebase.auth

    init {
        uiState.update { it.copy(isUserLoggedIn = auth.currentUser != null) }
    }

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> {
                uiState.update { it.copy(email = event.email) }
            }

            is LoginUiEvent.PasswordChanged -> {
                uiState.update { it.copy(password = event.password) }
            }

            is LoginUiEvent.OnLoginClick -> {
                if (validateFields()) {
                    createUser()
                }
            }
        }
    }

    private fun createUser() {
        uiState.update { it.copy(isLoginButtonLoading = true) }
        auth.createUserWithEmailAndPassword(uiState.value.email, uiState.value.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    insertUserData()
                    uiState.update {
                        it.copy(
                            isUserLoggedIn = true,
                            isLoginButtonLoading = false
                        )
                    }
                } else {
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        loginUser()
                    } else {
                        showSnackBar(task.exception?.message.toString())
                        uiState.update { it.copy(isLoginButtonLoading = false) }
                    }
                }
            }
    }

    private fun loginUser() {
        auth.signInWithEmailAndPassword(uiState.value.email, uiState.value.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    uiState.update {
                        it.copy(
                            isUserLoggedIn = true,
                            isLoginButtonLoading = false
                        )
                    }
                } else {
                    showSnackBar(task.exception?.message.toString())
                    uiState.update { it.copy(isLoginButtonLoading = false) }
                }
            }
    }

    private fun insertUserData() {
        viewModelScope.launch {
            if (auth.currentUser != null) {
                userAccountRepository.insertUserAccount(
                    UserAccount(
                        userId = auth.currentUser?.uid.toString(),
                        username = RandomUtils.generateRandomUsername(),
                        accountBalance = RandomUtils.generateRandomAccountBalance(),
                        email = auth.currentUser?.email.toString()
                    )
                )
            }
        }
    }

    private fun validateFields(): Boolean {
        val email = uiState.value.email
        val password = uiState.value.password
        if (email.isBlank()) {
            showSnackBar("Email cannot be empty")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showSnackBar("Invalid email format")
            return false
        }
        if (password.isBlank()) {
            showSnackBar("Password cannot be empty")
            return false
        }
        if (password.length < 6) {
            showSnackBar("Password must be at least 6 characters")
            return false
        }
        return true
    }

    private fun showSnackBar(snackBarMessage: String) {
        uiState.update { it.copy(snackBarMessage = snackBarMessage) }
        dismissSnackBar()
    }

    private fun dismissSnackBar() {
        viewModelScope.launch {
            delay(1000)
            uiState.update { it.copy(snackBarMessage = "") }
        }
    }
}