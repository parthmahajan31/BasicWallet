package com.task.vpdmoney.repo

import com.task.vpdmoney.data.Account
import com.task.vpdmoney.data.UserAccount
import com.task.vpdmoney.room.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val userAccountDao: UserDao
) {
    suspend fun insertUserAccount(userAccount: UserAccount) {
        userAccountDao.insertUserAccount(userAccount)
    }

    fun getUserAccountById(userId: String): Flow<UserAccount?> {
        return userAccountDao.getUserAccountById(userId)
    }

}