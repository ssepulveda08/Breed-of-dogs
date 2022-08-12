package com.example.breedofdogs.di

import com.example.breedofdogs.newwork.service.CatService
import com.example.breedofdogs.newwork.service.DogService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetWordModule {

    @Singleton
    @Provides
    @DogsPath
    fun provideDogsBaseUrl(): String = "https://dog.ceo/"

    @Singleton
    @Provides
    @CatsPath
    fun provideCatsBaseUrl(): String = "https://api.thecatapi.com/v1/"

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    @DogsService
    fun provideDogsRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        @DogsPath baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    @CatsService
    fun provideCatsRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        @CatsPath baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideDogService(@DogsService retrofit: Retrofit): DogService =
        retrofit.create(DogService::class.java)

    @Singleton
    @Provides
    fun provideCatService(@CatsService retrofit: Retrofit): CatService =
        retrofit.create(CatService::class.java)

}