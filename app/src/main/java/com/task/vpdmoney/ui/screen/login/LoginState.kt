package com.task.vpdmoney.ui.screen.login

data class LoginState(
    val isLoginButtonLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isUserLoggedIn: Boolean = false,
    val snackBarMessage: String = "",
)

sealed class LoginUiEvent {
    data class EmailChanged(val email: String) : LoginUiEvent()
    data class PasswordChanged(val password: String) : LoginUiEvent()
    data object OnLoginClick : LoginUiEvent()
}