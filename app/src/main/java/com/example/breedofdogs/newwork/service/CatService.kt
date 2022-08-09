package com.example.breedofdogs.newwork.service

import com.example.breedofdogs.BuildConfig
import com.example.breedofdogs.newwork.models.BreedCat
import com.example.breedofdogs.newwork.models.CatImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatService {

    @GET("breeds")
    suspend fun getBreed(): Response<List<BreedCat>>

    @GET("images/search/")
    suspend fun getImagesCatByBreed(
        @Query("limit") limit: Int = 20,
        @Query("breed_ids") breedId: String,
        @Query("api_key") apiKey: String = BuildConfig.CAT_API_KEY,
    ): Response<List<CatImage>>

}