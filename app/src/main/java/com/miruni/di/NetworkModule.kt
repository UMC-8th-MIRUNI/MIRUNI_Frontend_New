package com.miruni.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.miruni.core.domain.auth.TokenDataStore
import com.miruni.core.network.AuthAuthenticator
import com.miruni.network.AuthInterceptor
import com.miruni.network.TokenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://miruni.site/"

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

    @Provides
    @Singleton
    fun provideAuthenticator(
        tokenDataStore: TokenDataStore
    ): AuthAuthenticator =
        AuthAuthenticator(
            tokenDataStore = tokenDataStore
        )

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenProvider: TokenProvider,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(AuthInterceptor(tokenProvider))
            .authenticator(authAuthenticator)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


}