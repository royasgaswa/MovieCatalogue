package com.example.moviecataloge.presentation.ui.tvshow

import androidx.lifecycle.*
import com.example.moviecataloge.data.vo.Resource
import com.example.moviecataloge.domain.usecase.CatalogueUseCase
import com.example.moviecataloge.presentation.model.TvshowEntityPresentation
import com.example.moviecataloge.utils.TvshowDataMapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class TvshowViewModel (val catalogueUseCase: CatalogueUseCase) :
    ViewModel() {
    private val _isLoading= MutableLiveData<Boolean>()
    private val _tvshow = MutableLiveData<List<TvshowEntityPresentation>>()
    val isLoading: LiveData<Boolean> = _isLoading
    val tvshow:LiveData<List<TvshowEntityPresentation>> = _tvshow

    fun getTvshow(){
        viewModelScope.launch {
            catalogueUseCase.getAllTvshows()
                .onStart { _isLoading.postValue(true) }
                .onCompletion { _isLoading.postValue(false) }
                .collect { tvshows->
                    when(tvshows){
                        is Resource.Loading->_isLoading.postValue(true)
                        is Resource.Success->{
                            _isLoading.postValue(false)
                            _tvshow.postValue(tvshows.data?.let { TvshowDataMapper.mapDomainToPresentation(it) })
                        }
                        is Resource.Error->_isLoading.postValue(false)
                    }
                }
        }
    }
}