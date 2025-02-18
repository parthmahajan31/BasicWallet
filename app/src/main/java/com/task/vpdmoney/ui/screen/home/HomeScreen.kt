package com.task.vpdmoney.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.task.vpdmoney.R
import com.task.vpdmoney.data.UserAccount
import com.task.vpdmoney.ui.common.AppFilledButton
import com.task.vpdmoney.ui.theme.Purple80
import com.task.vpdmoney.ui.theme.colorBackgroundWhite
import com.task.vpdmoney.util.formatValue

@Composable
fun HomeScreen(
    onNavigateLogin: () -> Unit
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val viewState by viewModel.consumableState().collectAsState()

    val userAccountData by remember { derivedStateOf { viewState.userAccountData } }

    HomeScreenContent(
        onNavigateLogin = onNavigateLogin,
        userAccountData = { userAccountData }
    )
}

@Composable
fun HomeScreenContent(
    onNavigateLogin: () -> Unit = {},
    userAccountData: () -> UserAccount? = { null },
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                userAccountData().let {
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent,
                        ),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Purple80,
                                            Color.Blue
                                        )
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(16.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_wallet),
                                        contentDescription = "Wallet Icon",
                                        tint = colorBackgroundWhite,
                                        modifier = Modifier.size(40.dp),
                                    )
                                    Text(
                                        text = "₹${it?.accountBalance?.formatValue()}",
                                        color = colorBackgroundWhite,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "User Name : ${it?.username}",
                                    color = colorBackgroundWhite,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Email : ${it?.email}",
                                    color = colorBackgroundWhite,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                AppFilledButton(
                                    modifier = Modifier
                                        .padding(top = 16.dp)
                                        .align(alignment = Alignment.CenterHorizontally)
                                        .width(150.dp)
                                        .padding(5.dp),
                                    text = "Transfer",
                                    textColor = Purple80,
                                    backgroundColor = colorBackgroundWhite
                                )
                            }
                        }
                    }
                }
            }
            AppFilledButton(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                text = "Sign Out",
                onClick = {
                    Firebase.auth.signOut()
                    onNavigateLogin()
                }
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenContent()
}
