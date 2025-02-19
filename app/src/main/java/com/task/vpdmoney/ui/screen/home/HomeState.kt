package com.task.vpdmoney.ui.screen.home

import com.task.vpdmoney.data.Account
import com.task.vpdmoney.data.UserAccount

data class HomeState(
    val isLoading: Boolean = false,
    val userAccountData: UserAccount? = null,
    val accountsList: List<Account> = emptyList(),

    val isShowTransferSheet: Boolean = false,
    val srcAccount: String = "",
    val desAccount: String = "",
    val amount: String = "",
    val snackBarMessage: String = ""
)

sealed class HomeUiEvent {
    data object DismissTransfer : HomeUiEvent()
    data class SrcAccountChange(val value: String) : HomeUiEvent()
    data class DesAccountChange(val value: String) : HomeUiEvent()
    data class AmountAccountChange(val value: String) : HomeUiEvent()
    data object TransferAmount : HomeUiEvent()
    data object TransferButtonClick : HomeUiEvent()
}