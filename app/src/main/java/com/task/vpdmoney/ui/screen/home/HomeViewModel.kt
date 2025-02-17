package com.task.vpdmoney.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.task.vpdmoney.repo.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userAccountRepository: AccountRepository
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
    }
}