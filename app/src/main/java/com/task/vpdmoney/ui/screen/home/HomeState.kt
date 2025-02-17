package com.task.vpdmoney.ui.screen.home

import com.task.vpdmoney.data.UserAccount

data class HomeState(
    val isLoading:Boolean = false,
    val userAccountData: UserAccount? = null
)