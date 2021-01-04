package com.example.moviecataloge.di

import com.example.moviecataloge.domain.usecase.CatalogueInteractor
import com.example.moviecataloge.domain.usecase.CatalogueUseCase
import com.example.moviecataloge.presentation.ui.detail.viewmodel.DetailMovieViewModel
import com.example.moviecataloge.presentation.ui.detail.viewmodel.DetailTvshowViewModel
import com.example.moviecataloge.presentation.ui.favorite.movie.FavoriteMovieViewModel
import com.example.moviecataloge.presentation.ui.favorite.tvshow.FavoriteTvshowViewModel
import com.example.moviecataloge.presentation.ui.movie.MovieViewModel
import com.example.moviecataloge.presentation.ui.tvshow.TvshowViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module



val useCaseModule = module {
    factory<CatalogueUseCase>{ CatalogueInteractor(get()) }
}

val viewModelModule= module {
    viewModel { MovieViewModel(catalogueUseCase =  get()) }
    viewModel { TvshowViewModel(get()) }
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { FavoriteTvshowViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
    viewModel { DetailTvshowViewModel(get()) }
}