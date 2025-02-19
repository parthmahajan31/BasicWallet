package com.task.vpdmoney.ui.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.task.vpdmoney.R
import com.task.vpdmoney.ui.common.AppFilledButton
import com.task.vpdmoney.ui.common.AppModalBottomSheet
import com.task.vpdmoney.ui.common.AppOutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferMoneyBottomSheet(
    onDismissRequest: () -> Unit,
    srcAccount: () -> String,
    desAccount: () -> String,
    amount: () -> String,
    onSrcAccChange: (String) -> Unit,
    onDesAccChange: (String) -> Unit,
    onAmountChanged: (String) -> Unit,
    onTransferAmount: () -> Unit
) {
    AppModalBottomSheet(
        onDismissRequest = onDismissRequest,
    ) {
        TransferMoneyContent(
            srcAccount = srcAccount,
            desAccount = desAccount,
            amount = amount,
            onSrcAccChange = onSrcAccChange,
            onDesAccChange = onDesAccChange,
            onAmountChanged = onAmountChanged,
            onTransferAmount = onTransferAmount
        )
    }
}

@Composable
fun TransferMoneyContent(
    srcAccount: () -> String = { "" },
    desAccount: () -> String = { "" },
    amount: () -> String = { "" },
    onSrcAccChange: (String) -> Unit = {},
    onDesAccChange: (String) -> Unit = {},
    onAmountChanged: (String) -> Unit = {},
    onTransferAmount: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = "Transfer Money",
                fontWeight = FontWeight.W500
            )

            Icon(
                painter = painterResource(R.drawable.ic_cancel),
                contentDescription = "Close",
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        AppOutlinedTextField(
            modifier = Modifier.padding(top = 10.dp),
            text = srcAccount(),
            placeholder = "Source Account Number",
            onValueChange = { onSrcAccChange(it) }
        )

        AppOutlinedTextField(
            modifier = Modifier.padding(top = 10.dp),
            text = desAccount(),
            placeholder = "Source Account Number",
            onValueChange = { onDesAccChange(it) }
        )

        AppOutlinedTextField(
            modifier = Modifier.padding(top = 10.dp),
            text = amount(),
            placeholder = "Amount",
            onValueChange = { onAmountChanged(it) },
            keyboardType = KeyboardType.Number
        )

        AppFilledButton(
            modifier = Modifier.padding(16.dp),
            text = "Transfer",
            onClick = {
                onTransferAmount()
            }
        )
    }
}
