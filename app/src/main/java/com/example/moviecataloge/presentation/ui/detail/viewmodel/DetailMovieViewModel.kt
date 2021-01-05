package com.example.moviecataloge.presentation.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.moviecataloge.data.vo.Resource
import com.example.moviecataloge.domain.model.MovieEntityDomain
import com.example.moviecataloge.domain.usecase.CatalogueUseCase
import com.example.moviecataloge.presentation.model.MovieEntityPresentation
import com.example.moviecataloge.utils.MovieDataMapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailMovieViewModel (val catalogueUseCase: CatalogueUseCase) :
    ViewModel() {
    private var movieId = MutableLiveData<Int>()
    private val _isLoading= MutableLiveData<Boolean>()
    private val _movie= MutableLiveData<MovieEntityPresentation>()
    val isLoading: LiveData<Boolean> = _isLoading
    val movie: LiveData<MovieEntityPresentation> = _movie

    fun setSelectedMovie(movieId: Int?) {
        this.movieId.value = movieId
    }
    fun getDetailMovie(){
        viewModelScope.launch {
            catalogueUseCase.getDetailMovie(movieId.value)
                .onStart { _isLoading.postValue(true) }
                .onCompletion { _isLoading.postValue(false) }
                .collect {movies->
                    when(movies){
                        is Resource.Loading->_isLoading.postValue(true)
                        is Resource.Success->{
                            _isLoading.postValue(false)
                            _movie.postValue(movies.data?.let {
                                val listData =ArrayList<MovieEntityDomain>()
                                listData.add(it)
                                MovieDataMapper.mapDomainToPresentation(listData)[0]
                            })
                        }
                        is Resource.Error->{
                            _isLoading.postValue(true)
                        }
                    }
                }
        }
    }

    fun setFavorite() {
        val movieResource = movie.value
        Log.d("data2", movieResource.toString())
        if (movieResource != null) {
            val dataMovie = MovieDataMapper.mapPresentationToDomain(movieResource)
            val newstate = !dataMovie.isFavorite
            catalogueUseCase.setFavoriteMovie(dataMovie, newstate)
        }
    }
}