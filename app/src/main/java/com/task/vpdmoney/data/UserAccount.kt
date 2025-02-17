package com.task.vpdmoney.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_accounts")
data class UserAccount(
    @PrimaryKey val userId: String,
    val username: String,
    val accountBalance: Double,
    val email: String
)
