package com.example.breedofdogs.newwork.models

import com.google.gson.annotations.SerializedName

data class DogResponse(
    @SerializedName("message") val message: List<String>,
    @SerializedName("status") val status: Boolean,
)

data class BreedCat(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: String,
    @SerializedName("description") val description: String,
)

data class CatImage(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
)
