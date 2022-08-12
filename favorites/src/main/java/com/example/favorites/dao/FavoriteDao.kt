package com.example.favorites.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.favorites.entity.Favorite
import kotlinx.coroutines.flow.MutableStateFlow

const val TYPE_CAT = "CAT"
const val TYPE_DOG = "DOG"

@Dao
interface FavoriteDao {

    @Query("SELECT * from favorite")
    fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorite WHERE type= :type")
    fun getFavoriteByType(type: String): List<Favorite>

    @Query("SELECT COUNT(id) FROM favorite WHERE type= :type")
    fun getCountByType(type: String): LiveData<Int>

    @Query("SELECT COUNT(id) FROM favorite WHERE url= :url")
    fun getCountByUrl(url: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorites: List<Favorite>)

    @Delete
    fun deleteItem(favorite: Favorite)
}