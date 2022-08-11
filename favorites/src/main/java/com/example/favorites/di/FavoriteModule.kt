package com.example.favorites.di

import android.app.Application
import androidx.room.Room
import com.example.favorites.database.FavoriteDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        FavoriteDb::class.java,
        "favorites"
    ).build()
}
