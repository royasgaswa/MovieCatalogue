package com.example.moviecataloge.presentation.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.moviecataloge.data.vo.Resource
import com.example.moviecataloge.domain.model.MovieEntityDomain
import com.example.moviecataloge.domain.usecase.CatalogueUseCase

class DetailMovieViewModel (val catalogueUseCase: CatalogueUseCase) :
    ViewModel() {
    private var movieId = MutableLiveData<Int>()

    fun setSelectedMovie(movieId: Int?) {
        this.movieId.value = movieId
    }

    var getMovie: LiveData<Resource<MovieEntityDomain>> = Transformations.switchMap(movieId) { mMovieId ->
        catalogueUseCase.getDetailMovie(mMovieId).asLiveData()
    }

    fun setFavorite() {
        val movieResource = getMovie.value
        Log.d("data2", movieResource.toString())
        if (movieResource != null) {
            val dataMovie = movieResource.data
            if (dataMovie != null) {
                val newstate = !dataMovie.isFavorite
                catalogueUseCase.setFavoriteMovie(dataMovie, newstate)
            }
        }
    }
}