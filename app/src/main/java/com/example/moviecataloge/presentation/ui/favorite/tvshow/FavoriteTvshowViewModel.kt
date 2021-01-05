package com.example.moviecataloge.presentation.ui.favorite.tvshow

import androidx.lifecycle.*
import com.example.moviecataloge.domain.usecase.CatalogueUseCase
import com.example.moviecataloge.presentation.model.TvshowEntityPresentation
import com.example.moviecataloge.utils.TvshowDataMapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FavoriteTvshowViewModel (val catalogueUseCase: CatalogueUseCase) :
    ViewModel() {
    private val _isLoading= MutableLiveData<Boolean>()
    private val _tvshow = MutableLiveData<List<TvshowEntityPresentation>>()
    val isLoading: LiveData<Boolean> = _isLoading
    val tvshows : LiveData<List<TvshowEntityPresentation>> = _tvshow
    fun getFavoriteTvshow(){
        viewModelScope.launch {
            catalogueUseCase.getFavoriteTvshow()
                .onStart { _isLoading.postValue(true) }
                .onCompletion { _isLoading.postValue(false) }
                .collect {
                    _isLoading.postValue(false)
                    _tvshow.postValue(TvshowDataMapper.mapDomainToPresentation(it))
                }
        }
    }
}