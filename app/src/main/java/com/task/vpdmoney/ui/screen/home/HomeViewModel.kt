package com.task.vpdmoney.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.task.vpdmoney.data.UsersList
import com.task.vpdmoney.repo.AccountRepository
import com.task.vpdmoney.room.AccountsInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userAccountRepository: AccountRepository,
    private val accountsInterface: AccountsInterface
) : ViewModel() {

    private val uiState = MutableStateFlow(HomeState())
    fun consumableState() = uiState.asStateFlow()

    private var auth: FirebaseAuth = Firebase.auth

    private val userAccount = userAccountRepository.getUserAccountById(
        auth.currentUser?.uid.toString()
    )

    init {
        viewModelScope.launch {
            userAccount.collectLatest { user ->
                uiState.update { it.copy(userAccountData = user) }
            }
        }

        viewModelScope.launch {
            val accountsList = accountsInterface.getAccountsList()
            uiState.update { it.copy(accountsList = accountsList.accounts ?: emptyList()) }
        }
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.DismissTransfer -> {
                uiState.update { it.copy(isShowTransferSheet = false) }
            }

            is HomeUiEvent.SrcAccountChange -> {
                uiState.update { it.copy(srcAccount = event.value) }
            }

            is HomeUiEvent.DesAccountChange -> {
                uiState.update { it.copy(desAccount = event.value) }
            }

            is HomeUiEvent.AmountAccountChange -> {
                uiState.update { it.copy(amount = event.value) }
            }

            is HomeUiEvent.TransferAmount -> {
                Log.e("TAG", "onEvent:dsfgdishgfhsdgvfvjgfvasj")
                if (validateData()) {
                    transferAmount()
                }
            }

            is HomeUiEvent.TransferButtonClick -> {
                uiState.update {
                    it.copy(isShowTransferSheet = true)
                }
            }
        }
    }

    private fun transferAmount() {
        viewModelScope.launch {
            val desAccountNumber = uiState.value.desAccount
            val transferAmount = uiState.value.amount.toDouble()

            val updatedAccountsList = uiState.value.accountsList.map { account ->
                if (account.accountNumber == desAccountNumber) {
                    account.copy(balance = (account.balance ?: 0.0) + transferAmount)
                } else {
                    account
                }
            }
            val updatedUserAccount = uiState.value.userAccountData?.copy(
                accountBalance = (uiState.value.userAccountData?.accountBalance
                    ?: 0.0) - transferAmount
            )
            uiState.update {
                it.copy(
                    accountsList = updatedAccountsList,
                    userAccountData = updatedUserAccount
                )
            }
            if (updatedAccountsList.isNotEmpty()) {
                viewModelScope.launch {
                    accountsInterface.updateAccountsList(userList = UsersList(updatedAccountsList))
                }
            }
            if (updatedUserAccount != null) {
                userAccountRepository.updateUserAccount(updatedUserAccount)
            }
            showSnackBar("Transfer successful")
            uiState.update {
                it.copy(
                    isShowTransferSheet = false
                )
            }
            viewModelScope.launch {
                userAccount.collectLatest { user ->
                    uiState.update { it.copy(userAccountData = user) }
                }
            }

            viewModelScope.launch {
                val accountsList = accountsInterface.getAccountsList()
                uiState.update { it.copy(accountsList = accountsList.accounts ?: emptyList()) }
            }
        }
    }

    private fun validateData(): Boolean {
        Log.e("TAG", "validateData:${uiState.value.srcAccount}")
        if (uiState.value.srcAccount.isEmpty()) {
            showSnackBar("Source account cannot be empty")
            return false
        }
        if (uiState.value.desAccount.isEmpty()) {
            showSnackBar("Destination account cannot be empty")
            return false
        }
        if (uiState.value.srcAccount.isEmpty()) {
            showSnackBar("Amount cannot be empty")
            return false
        }
        if (uiState.value.srcAccount != uiState.value.userAccountData?.username) {
            showSnackBar("Invalid source account number")
            return false
        }
        if (uiState.value.accountsList.find { it.accountNumber == uiState.value.desAccount } == null) {
            showSnackBar("Invalid destination account number")
            return false
        }
        if (uiState.value.amount.toDouble() > (uiState.value.userAccountData?.accountBalance
                ?: 0.0)
        ) {
            showSnackBar("Invalid transfer amount")
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