package com.example.breedofdogs.newwork.service

import com.example.breedofdogs.newwork.models.ResponseService
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogService {

    @GET("api/breed/{breed}/images/random/15")
    suspend fun getDogsByBreed(
        @Path("breed") breed: String,
    ): Response<ResponseService>

}