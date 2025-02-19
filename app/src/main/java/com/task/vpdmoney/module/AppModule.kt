package com.task.vpdmoney.module

import android.content.Context
import androidx.room.Room
import com.task.vpdmoney.room.AppDatabase
import com.task.vpdmoney.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideUserAccountDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userAccountDao()
    }
}