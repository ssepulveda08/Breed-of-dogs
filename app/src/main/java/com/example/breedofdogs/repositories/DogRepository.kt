package com.example.breedofdogs.repositories

import com.example.breedofdogs.data.RemoteDataSource
import com.example.breedofdogs.newwork.BaseApiResponse
import com.example.breedofdogs.newwork.models.DogResponse
import com.example.breedofdogs.newwork.models.NetworkResult
import com.example.favorites.dataSource.FavoriteLocalDataSource
import com.example.favorites.entity.Favorite
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@ActivityRetainedScoped
class DogRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dataSource: FavoriteLocalDataSource,
) : BaseApiResponse() {

    suspend fun getDogsByBreed(breed: String): Flow<NetworkResult<DogResponse>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getDogsByBreed(breed) })
        }.flowOn(Dispatchers.IO)
    }

    fun countFavorite() = dataSource.getCountDogs()

    suspend fun getCountByUrl(url: String) = dataSource.getCountByUrl(url)

    suspend fun addFavoriteDog(favorite: Favorite) {
        dataSource.addFavorite(favorite)
    }
}
