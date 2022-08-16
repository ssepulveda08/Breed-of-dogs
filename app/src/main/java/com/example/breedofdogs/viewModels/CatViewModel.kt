package com.example.breedofdogs.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.breedofdogs.newwork.models.BreedCat
import com.example.breedofdogs.repositories.CatRepository
import com.example.breedofdogs.ui.models.ItemImage
import com.example.breedofdogs.ui.models.Queries
import com.example.favorites.dao.TYPE_CAT
import com.example.favorites.entity.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: CatRepository,
    application: Application
) : AndroidViewModel(application) {

    var cacheQueries by mutableStateOf<List<Queries>>(listOf())

    var favorites by mutableStateOf<List<Favorite>>(listOf())

    var breeds by mutableStateOf<List<BreedCat>>(listOf())

    init {
        getBreedsCat()
    }

    private fun getBreedsCat() = viewModelScope.launch {
        if (breeds.isEmpty()) {
            repository.getBreedsCat().collect { values ->
                values.data?.let {
                    breeds = it
                }
            }
        }
    }

    fun updateBreeds(breed: String) {
        viewModelScope.launch {
            fetchCatByBreed(breed)
        }
    }

    fun fetchCatByBreed(breed: String) = viewModelScope.launch {
        val dataBread = cacheQueries.firstOrNull { it.breed == breed }
        if (dataBread == null) {
            repository.getImagesCatByBreed(breed).collect { values ->
                val list: MutableList<Queries> = cacheQueries.toMutableList()
                values.data?.let {
                    val listItems = mutableListOf<ItemImage>()
                    it.forEach { img ->
                        val count = repository.getCountByUrl(img.url)
                        listItems.add(
                            ItemImage(
                                img.url,
                                count > 0
                            )
                        )
                    }
                    list.add(
                        Queries(
                            breed,
                            listItems
                        )
                    )
                }
                cacheQueries = list
            }
        }
    }

    fun addFavoriteCat(img: String, breed: String) {
        viewModelScope.launch {
            cacheQueries.firstOrNull { it.breed == breed }?.items?.firstOrNull {
                it.imgUrl == img
            }?.apply {
                isFavorite = true
            }
            repository.addFavoriteCat(
                Favorite(
                    id = 0,
                    url = img,
                    type = TYPE_CAT,
                    breed = breed
                )
            )
        }
    }

    fun getFavoriteCats() = viewModelScope.launch {
        repository.getFavorite().collect {
            if (it != null) {
                favorites = it
            }
        }
    }

    val onCountFavorites: LiveData<Int> = repository.countFavorite()

}
