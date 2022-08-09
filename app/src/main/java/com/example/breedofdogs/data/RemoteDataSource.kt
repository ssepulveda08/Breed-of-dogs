package com.example.breedofdogs.data

import com.example.breedofdogs.newwork.service.CatService
import com.example.breedofdogs.newwork.service.DogService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val dogService: DogService,
    private val catService: CatService
) {

    suspend fun getDogsByBreed(breed: String) = dogService.getDogsByBreed(breed)

    suspend fun getBreadsCat() = catService.getBreed()

    suspend fun getImagesCatByBreed(breed: String) = catService.getImagesCatByBreed(breedId = breed)
}
