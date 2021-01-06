package com.example.moviecataloge.presentation.ui.movie

import android.util.Log
import androidx.lifecycle.*
import com.example.moviecatalogue.core.data.vo.Resource
import com.example.moviecatalogue.core.domain.usecase.CatalogueUseCase
import com.example.moviecatalogue.core.presentation.model.MovieEntityPresentation
import com.example.moviecatalogue.core.utils.MovieDataMapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MovieViewModel (val catalogueUseCase: CatalogueUseCase) :
    ViewModel() {
    private val _isLoading=MutableLiveData<Boolean>()
    private val _movie=MutableLiveData<List<MovieEntityPresentation>>()
    val isLoading:LiveData<Boolean> = _isLoading
    val movie:LiveData<List<MovieEntityPresentation>> = _movie
    fun getMovies(){
        viewModelScope.launch {
            catalogueUseCase.getAllMovies()
                .onStart { _isLoading.postValue(true) }
                .onCompletion { _isLoading.postValue(false) }
                .collect {movies->
                    when(movies){
                        is Resource.Loading->_isLoading.postValue(true)
                        is Resource.Success->{
                            Log.d("Data3", movies.data?.size.toString())
                            _isLoading.postValue(false)
                            _movie.postValue(movies.data?.let { MovieDataMapper.mapDomainToPresentation(it) })
                        }
                        is Resource.Error->{
                            _isLoading.postValue(true)
                        }
                    }
                }
        }
    }
}