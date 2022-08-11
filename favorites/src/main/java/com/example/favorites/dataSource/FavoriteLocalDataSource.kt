package com.example.favorites.dataSource

import com.example.favorites.dao.FavoriteDao
import com.example.favorites.dao.TYPE_CAT
import com.example.favorites.dao.TYPE_DOG
import com.example.favorites.entity.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteLocalDataSource(private val db: FavoriteDao): LocaleDataSource{

    private fun getFavoriteCars() = db.getFavoriteByType(TYPE_CAT)

    private fun getFavoriteDogs() = db.getFavoriteByType(TYPE_DOG)

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