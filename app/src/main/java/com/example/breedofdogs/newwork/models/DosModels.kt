package com.example.breedofdogs.newwork.models

import com.google.gson.annotations.SerializedName

data class ResponseService(
    @SerializedName("message") val message: List<String>,
    @SerializedName("status") val status: Boolean,
)