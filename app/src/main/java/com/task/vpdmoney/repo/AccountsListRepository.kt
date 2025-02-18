package com.task.vpdmoney.repo

import android.content.Context
import com.google.gson.Gson
import com.task.vpdmoney.data.UsersList
import com.task.vpdmoney.room.AccountsInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class AccountsListRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : AccountsInterface {
    override suspend fun getAccountsList(): UsersList =
        withContext(Dispatchers.IO) {
            val jsonString = try {
                context.assets.open("accounts.json").bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return@withContext UsersList(accounts = emptyList())
            }
            Gson().fromJson(jsonString, UsersList::class.java)
        }
}