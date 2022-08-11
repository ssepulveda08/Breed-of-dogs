package com.example.favorites.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.favorites.entity.Favorite

const val TYPE_CAT = "CAT"
const val TYPE_DOG = "DOG"

@Dao
interface FavoriteDao {

    @Query("SELECT * from favorite")
    fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorite WHERE type= :type")
    fun getFavoriteByType(type: String): List<Favorite>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorites: List<Favorite>)

    @Delete
    fun deleteItem(favorite: Favorite)
}