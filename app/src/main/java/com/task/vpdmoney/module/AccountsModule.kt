package com.task.vpdmoney.module

import com.task.vpdmoney.repo.AccountsListRepository
import com.task.vpdmoney.room.AccountsInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AccountsModule {
    @Binds
    @Singleton
    abstract fun bindUsersListRepository(productRepositoryImpl: AccountsListRepository): AccountsInterface
}