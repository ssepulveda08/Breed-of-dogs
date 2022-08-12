package com.example.breedofdogs.repositories

import com.example.breedofdogs.data.RemoteDataSource
import com.example.breedofdogs.newwork.BaseApiResponse
import com.example.breedofdogs.newwork.models.BreedCat
import com.example.breedofdogs.newwork.models.CatImage
import com.example.breedofdogs.newwork.models.NetworkResult
import com.example.favorites.dataSource.FavoriteLocalDataSource
import com.example.favorites.dataSource.LocaleDataSource
import com.example.favorites.entity.Favorite
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dataSource: FavoriteLocalDataSource,
) : BaseApiResponse() {

    suspend fun getBreedsCat() : Flow<NetworkResult<List<BreedCat>>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getBreadsCat() })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getImagesCatByBreed(breed: String) : Flow<NetworkResult<List<CatImage>>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getImagesCatByBreed(breed) })
        }.flowOn(Dispatchers.IO)
    }

    fun countFavorite() = dataSource.getCountCats()

    fun getFavorite() = dataSource.getFavoriteCats()

    suspend fun addFavoriteCat(favorite: Favorite) {
        dataSource.addFavorite(favorite)
    }

}
