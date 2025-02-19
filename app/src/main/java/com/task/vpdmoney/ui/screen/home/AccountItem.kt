package com.task.vpdmoney.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.task.vpdmoney.data.Account
import com.task.vpdmoney.ui.theme.colorBackgroundWhite
import com.task.vpdmoney.ui.theme.colorTextBlack
import com.task.vpdmoney.util.formatValue

@Composable
fun AccountItem(account: () -> Account) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = colorTextBlack,
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Account Number : ${account().accountNumber}",
                color = colorBackgroundWhite,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Email : ${account().email}",
                color = colorBackgroundWhite,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Balance : ₹${account().balance?.formatValue()}",
                color = colorBackgroundWhite,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}