package com.example.breedofdogs.viewModels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.breedofdogs.newwork.models.BreedCat
import com.example.breedofdogs.repositories.CatRepository
import com.example.breedofdogs.ui.models.Queries
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: CatRepository,
    application: Application
) : AndroidViewModel(application) {

    var cacheQueries by mutableStateOf<List<Queries>>(listOf())

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
                    list.add(
                        Queries(
                            breed,
                            it.map { img -> img.url }
                        )
                    )
                }
                cacheQueries = list
            }
        }
    }

}
