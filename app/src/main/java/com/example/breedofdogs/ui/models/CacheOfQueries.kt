package com.example.breedofdogs.ui.models

data class Queries(
    val breed: String,
    var items: List<ItemImage>,
)

data class ItemImage(
    val imgUrl: String,
    var isFavorite: Boolean = false
)