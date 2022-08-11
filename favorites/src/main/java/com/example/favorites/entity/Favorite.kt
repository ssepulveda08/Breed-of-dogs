package com.example.favorites.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val url: String,
    val breed: String,
    val type: String,
    val description: String? = null
)