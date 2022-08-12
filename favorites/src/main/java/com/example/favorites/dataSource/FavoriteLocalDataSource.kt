package com.example.favorites.dataSource

import com.example.favorites.dao.FavoriteDao
import com.example.favorites.dao.TYPE_CAT
import com.example.favorites.dao.TYPE_DOG
import com.example.favorites.entity.Favorite
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteLocalDataSource @Inject constructor(private val db: FavoriteDao) : LocaleDataSource {

    fun getFavoriteCats() = db.getFavoriteByType(TYPE_CAT)

    fun getFavoriteDogs() = db.getFavoriteByType(TYPE_DOG)

    fun getCountCats() = db.getCountByType(TYPE_CAT)

    fun getCountDogs() = db.getCountByType(TYPE_DOG)

    suspend fun getCountByUrl(url: String):Int = withContext(Dispatchers.IO) {
        db.getCountByUrl(url)
    }

    suspend fun addFavorite(favorite: Favorite) {
        withContext(Dispatchers.IO) {
            db.insert(listOf(favorite))
        }
    }

    suspend fun deleteFavorite(favorite: Favorite) {
        withContext(Dispatchers.IO) {
            db.deleteItem(favorite)
        }
    }
}
