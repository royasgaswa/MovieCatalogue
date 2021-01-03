package com.example.moviecataloge.presentation.ui.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecataloge.domain.usecase.CatalogueUseCase

class FavoriteMovieViewModel (catalogueUseCase: CatalogueUseCase) :
    ViewModel() {
    val getFavoriteMovies = catalogueUseCase.getFavoriteMovie().asLiveData()
}