package com.example.moviecataloge.presentation.ui.favorite.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecataloge.domain.usecase.CatalogueUseCase

class FavoriteTvshowViewModel (catalogueUseCase: CatalogueUseCase) :
    ViewModel() {
    val getFavoriteTvshow = catalogueUseCase.getFavoriteTvshow().asLiveData()
}