package com.task.vpdmoney.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.vpdmoney.data.UserAccount

@Database(entities = [UserAccount::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userAccountDao(): UserDao
}