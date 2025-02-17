package com.task.vpdmoney.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.vpdmoney.data.UserAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAccount(userAccount: UserAccount)

    @Query("SELECT * FROM user_accounts WHERE userId = :userId")
    fun getUserAccountById(userId: String): Flow<UserAccount?>
}