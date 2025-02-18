package com.task.vpdmoney.data

data class UsersList(
    val accounts: List<Account>? = null
)

data class Account(
    val accountNumber: String? = null,
    val balance: Double? = null,
    val email: String? = null,
    val name: String? = null,
    val userId: String? = null
)


