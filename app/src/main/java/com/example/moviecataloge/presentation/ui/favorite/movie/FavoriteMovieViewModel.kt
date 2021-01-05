package com.example.moviecataloge.presentation.ui.favorite.movie

import android.util.Log
import androidx.lifecycle.*
import com.example.moviecataloge.data.vo.Resource
import com.example.moviecataloge.domain.usecase.CatalogueUseCase
import com.example.moviecataloge.presentation.model.MovieEntityPresentation
import com.example.moviecataloge.utils.MovieDataMapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FavoriteMovieViewModel (val catalogueUseCase: CatalogueUseCase) :
    ViewModel() {
    private val _isLoading= MutableLiveData<Boolean>()
    private val _movie= MutableLiveData<List<MovieEntityPresentation>>()
    val isLoading: LiveData<Boolean> = _isLoading
    val movie: LiveData<List<MovieEntityPresentation>> = _movie
    fun getFavoriteMovies(){
        viewModelScope.launch {
            catalogueUseCase.getFavoriteMovie()
                .onStart { _isLoading.postValue(true) }
                .onCompletion { _isLoading.postValue(false) }
                .collect {movies->
                    _movie.postValue(movies.let { MovieDataMapper.mapDomainToPresentation(it) })
                }
        }
    }
}