package com.task.vpdmoney.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.task.vpdmoney.R
import com.task.vpdmoney.ui.common.AppFilledButton
import com.task.vpdmoney.ui.common.AppOutlinedPasswordTextField
import com.task.vpdmoney.ui.common.AppOutlinedTextField
import com.task.vpdmoney.ui.common.AppSnackBar
import com.task.vpdmoney.ui.common.ShowAppSnackbar
import com.task.vpdmoney.ui.theme.colorBackgroundWhite

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit
) {
    val viewModel = hiltViewModel<LoginViewModel>()
    val viewState by viewModel.consumableState().collectAsState()

    val email by remember { derivedStateOf { viewState.email } }
    val password by remember { derivedStateOf { viewState.password } }
    val isUserLoggedIn by remember { derivedStateOf { viewState.isUserLoggedIn } }
    val isLoginButtonLoading by remember { derivedStateOf { viewState.isLoginButtonLoading } }

    val snackBarHostState = remember { SnackbarHostState() }

    LoginScreenContent(
        email = { email },
        password = { password },
        onEmailChanged = { viewModel.onEvent(LoginUiEvent.EmailChanged(it)) },
        onPasswordChanged = { viewModel.onEvent(LoginUiEvent.PasswordChanged(it)) },
        onLoginClick = { viewModel.onEvent(LoginUiEvent.OnLoginClick) },
        isLoginButtonLoading = { isLoginButtonLoading },
        snackbarHostState = snackBarHostState
    )

    NavigationLaunchEffect(
        isUserLoggedIn = { isUserLoggedIn },
        onNavigateHome = onNavigateToHome,
    )

    ShowAppSnackbar(
        snackbarHostState = snackBarHostState,
        snackbarMessage = { viewState.snackBarMessage },
    )
}

@Composable
fun NavigationLaunchEffect(
    isUserLoggedIn: () -> Boolean,
    onNavigateHome: () -> Unit
) {
    LaunchedEffect(isUserLoggedIn()) {
        if (isUserLoggedIn()) {
            onNavigateHome()
        }
    }
}

@Composable
fun LoginScreenContent(
    email: () -> String = { "" },
    password: () -> String = { "" },
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onLoginClick: () -> Unit = {},
    isLoginButtonLoading: () -> Boolean = { false },
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),
                contentAlignment = Alignment.TopCenter,
            ) {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { snackbarData ->
                        AppSnackBar(
                            modifier = Modifier.padding(12.dp),
                            snackbarData = snackbarData,
                        )
                    }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorBackgroundWhite)
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
            ) {
                Image(
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(R.drawable.app_icon),
                    contentDescription = null
                )
                AppOutlinedTextField(
                    modifier = Modifier.padding(top = 10.dp),
                    text = email(),
                    placeholder = "Email",
                    onValueChange = { onEmailChanged(it) }
                )

                AppOutlinedPasswordTextField(
                    modifier = Modifier.padding(top = 10.dp),
                    text = password(),
                    placeholder = "Password",
                    onValueChange = { onPasswordChanged(it) }
                )

                AppFilledButton(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Login",
                    onClick = {
                        onLoginClick()
                    },
                    isLoading = isLoginButtonLoading()
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenContent()
}
