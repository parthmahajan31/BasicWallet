package com.task.vpdmoney.room

import com.task.vpdmoney.data.Account
import com.task.vpdmoney.data.UsersList

interface AccountsInterface {
    suspend fun getAccountsList(): UsersList
    suspend fun updateAccountsList(userList: UsersList)
}