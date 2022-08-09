package com.example.breedofdogs.repositories

import com.example.breedofdogs.data.RemoteDataSource
import com.example.breedofdogs.newwork.BaseApiResponse
import com.example.breedofdogs.newwork.models.BreedCat
import com.example.breedofdogs.newwork.models.CatImage
import com.example.breedofdogs.newwork.models.NetworkResult
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
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
}
