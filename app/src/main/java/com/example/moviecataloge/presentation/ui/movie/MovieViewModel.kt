package com.example.moviecataloge.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecataloge.domain.usecase.CatalogueUseCase

class MovieViewModel (catalogueUseCase: CatalogueUseCase) :
    ViewModel() {
    val getMovies =catalogueUseCase.getAllMovies().asLiveData()
}