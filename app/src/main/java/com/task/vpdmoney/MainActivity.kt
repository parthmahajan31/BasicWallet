package com.task.vpdmoney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.task.vpdmoney.navigation.AppNavigationGraph
import com.task.vpdmoney.ui.theme.VPDMoneyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var auth: FirebaseAuth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VPDMoneyTheme {
                AppEntryPoint(auth)
            }
        }
    }
}

@Composable
fun AppEntryPoint(
    auth: FirebaseAuth
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        AppNavigationGraph(auth = auth)
    }
}