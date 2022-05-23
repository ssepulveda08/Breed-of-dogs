package com.example.breedofdogs.repositories

import com.example.breedofdogs.data.RemoteDataSource
import com.example.breedofdogs.newwork.BaseApiResponse
import com.example.breedofdogs.newwork.models.NetworkResult
import com.example.breedofdogs.newwork.models.ResponseService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@ActivityRetainedScoped
class DogRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    suspend fun getDogsByBreed(breed: String): Flow<NetworkResult<ResponseService>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getDogsByBreed(breed) })
        }.flowOn(Dispatchers.IO)
    }
}
