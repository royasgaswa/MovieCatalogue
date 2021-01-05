package com.example.moviecataloge.presentation.ui.detail.viewmodel

import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import com.example.moviecataloge.data.vo.Resource
import com.example.moviecataloge.domain.model.TvshowEntityDomain
import com.example.moviecataloge.domain.usecase.CatalogueUseCase
import com.example.moviecataloge.presentation.model.TvshowEntityPresentation
import com.example.moviecataloge.utils.TvshowDataMapper
import kotlinx.android.synthetic.main.activity_detail_tvshow.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailTvshowViewModel (val catalogueUseCase: CatalogueUseCase) :
    ViewModel() {
    private var tvshowId = MutableLiveData<Int>()
    private val _isLoading= MutableLiveData<Boolean>()
    private val _tvshow= MutableLiveData<TvshowEntityPresentation>()
    val isLoading:LiveData<Boolean> = _isLoading
    val tvshow:LiveData<TvshowEntityPresentation> = _tvshow

    fun setSelectedTvshow(tvshowId: Int?) {
        this.tvshowId.value = tvshowId
    }

    fun getDetailTvshow(){
        viewModelScope.launch {
            catalogueUseCase.getDetailTvshow(tvshowId.value)
                .onStart { _isLoading.postValue(true) }
                .onCompletion { _isLoading.postValue(false) }
                .collect { tvshow->
                    when (tvshow) {
                        is Resource.Loading -> _isLoading.postValue(true)
                        is Resource.Success -> if (tvshow.data != null) {
                            tvshow.data.let {
                                _isLoading.postValue(false)
                                val listData=ArrayList<TvshowEntityDomain>()
                                listData.add(it)
                                _tvshow.postValue(TvshowDataMapper.mapDomainToPresentation(listData)[0])
                            }
                        }
                        is Resource.Error -> {
                            _isLoading.postValue(true)
                        }
                    }
                }
        }
    }
    fun setFavorite() {
        val tvshowResource = tvshow.value
        if (tvshowResource != null) {
            val dataTvshow = TvshowDataMapper.mapPresentationToDomain(tvshowResource)
            val newstate = !dataTvshow.isFavorite
            catalogueUseCase.setFavoriteTvshow(dataTvshow, newstate)
        }
    }
}