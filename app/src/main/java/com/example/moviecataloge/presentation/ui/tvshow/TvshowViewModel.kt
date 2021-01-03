package com.example.moviecataloge.presentation.ui.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecataloge.domain.usecase.CatalogueUseCase

class TvshowViewModel (catalogueUseCase: CatalogueUseCase) :
    ViewModel() {
    val getTvshow = catalogueUseCase.getAllTvshows().asLiveData()
}