package com.example.moviecataloge.presentation.ui.detail.viewmodel

import androidx.lifecycle.*
import com.example.moviecataloge.data.vo.Resource
import com.example.moviecataloge.domain.model.TvshowEntityDomain
import com.example.moviecataloge.domain.usecase.CatalogueUseCase

class DetailTvshowViewModel (val catalogueUseCase: CatalogueUseCase) :
    ViewModel() {
    private var tvshowId = MutableLiveData<Int>()

    fun setSelectedTvshow(tvshowId: Int?) {
        this.tvshowId.value = tvshowId
    }

    var getTvshow: LiveData<Resource<TvshowEntityDomain>> =
        Transformations.switchMap(tvshowId) { mTvshowId ->
            catalogueUseCase.getDetailTvshow(mTvshowId).asLiveData()
        }

    fun setFavorite() {
        val tvshowResource = getTvshow.value
        if (tvshowResource != null) {
            val dataTvshow = tvshowResource.data
            if (dataTvshow != null) {
                val newstate = !dataTvshow.isFavorite
                catalogueUseCase.setFavoriteTvshow(dataTvshow, newstate)
            }
        }
    }
}