package com.miruni.di

import com.miruni.core.datastore.TokenDataStoreImpl
import com.miruni.core.domain.auth.TokenDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenBindModule {

    @Binds
    @Singleton
    abstract fun bindTokenDataStore(
        impl: TokenDataStoreImpl
    ): TokenDataStore
}