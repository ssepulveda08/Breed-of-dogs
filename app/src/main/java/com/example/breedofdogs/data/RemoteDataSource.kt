package com.example.breedofdogs.data

import com.example.breedofdogs.newwork.service.DogService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val dogService: DogService
) {

    suspend fun getDogsByBreed(breed: String) = dogService.getDogsByBreed(breed)
}
