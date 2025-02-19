package com.task.vpdmoney.repo

import android.content.Context
import com.google.gson.Gson
import com.task.vpdmoney.data.UsersList
import com.task.vpdmoney.room.AccountsInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
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

    override suspend fun updateAccountsList(userList: UsersList) {
        withContext(Dispatchers.IO) {
            try {
                val jsonString =
                    context.assets.open("accounts.json").bufferedReader().use { it.readText() }
                val accountsList =
                    Gson().fromJson(jsonString, UsersList::class.java).accounts?.toMutableList()

                val updatedAccountsList = userList.accounts
                if (updatedAccountsList != null) {
                    accountsList?.clear()
                    accountsList?.addAll(updatedAccountsList)
                }

                val updatedJsonString = Gson().toJson(accountsList)

                val file = File(context.filesDir, "accounts.json")
                file.writeText(updatedJsonString)
            } catch (ioException: IOException) {
                ioException.printStackTrace()
            }
        }
    }
}