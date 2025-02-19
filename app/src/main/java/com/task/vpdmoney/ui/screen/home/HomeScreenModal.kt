package com.task.vpdmoney.ui.screen.home

import androidx.compose.runtime.Composable
import com.task.vpdmoney.ui.bottomsheet.TransferMoneyBottomSheet

@Composable
fun HomeScreenModal(
    isShowTransferMoneySheet: () -> Boolean,
    onDismissTransfer: () -> Unit,
    srcAccount: () -> String,
    desAccount: () -> String,
    amount: () -> String,
    onSrcAccChange: (String) -> Unit,
    onDesAccChange: (String) -> Unit,
    onAmountChanged: (String) -> Unit,
    onTransferAmount: () -> Unit
) {
    if (isShowTransferMoneySheet()) {
        TransferMoneyBottomSheet(
            onDismissRequest = onDismissTransfer,
            srcAccount = srcAccount,
            desAccount = desAccount,
            amount = amount,
            onSrcAccChange = onSrcAccChange,
            onDesAccChange = onDesAccChange,
            onAmountChanged = onAmountChanged,
            onTransferAmount = onTransferAmount,
        )
    }
}