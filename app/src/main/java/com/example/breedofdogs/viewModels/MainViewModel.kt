package com.example.breedofdogs.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.breedofdogs.repositories.DogRepository
import com.example.breedofdogs.ui.BREEDS
import com.example.breedofdogs.ui.models.Queries
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DogRepository,
    application: Application
) : AndroidViewModel(application) {

    var cacheQueries by mutableStateOf<List<Queries>>(listOf())

    init {
        fetchDogsByBreed(BREEDS[0])
    }

    fun updateBreeds(breed: String) {
        viewModelScope.launch {
            fetchDogsByBreed(breed)
        }
    }

    fun fetchDogsByBreed(breed: String) = viewModelScope.launch {
        val dataBread = cacheQueries.firstOrNull { it.breed == breed }
        if (dataBread == null) {
            repository.getDogsByBreed(breed).collect { values ->
                val list: MutableList<Queries> = cacheQueries.toMutableList()
                values.data?.message?.let {
                    list.add(
                        Queries(
                            breed,
                            it
                        )
                    )
                }
                cacheQueries = list
            }
        }
    }
}
