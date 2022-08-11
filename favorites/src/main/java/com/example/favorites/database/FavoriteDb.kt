package com.example.favorites.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.favorites.dao.FavoriteDao
import com.example.favorites.entity.Favorite

@Database(
    entities = [Favorite::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteDb : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}