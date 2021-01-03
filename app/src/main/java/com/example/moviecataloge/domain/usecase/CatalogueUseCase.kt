package com.example.moviecataloge.domain.usecase

import com.example.moviecataloge.data.vo.Resource
import com.example.moviecataloge.domain.model.MovieEntityDomain
import com.example.moviecataloge.domain.model.TvshowEntityDomain
import kotlinx.coroutines.flow.Flow

interface CatalogueUseCase {
    fun getAllMovies(): Flow<Resource<List<MovieEntityDomain>>>
    fun getDetailMovie(movieId: Int?): Flow<Resource<MovieEntityDomain>>
    fun getAllTvshows(): Flow<Resource<List<TvshowEntityDomain>>>
    fun getDetailTvshow(tvshowId: Int?): Flow<Resource<TvshowEntityDomain>>
    fun getFavoriteMovie(): Flow<List<MovieEntityDomain>>
    fun getFavoriteTvshow(): Flow<List<TvshowEntityDomain>>
    fun setFavoriteMovie(movie: MovieEntityDomain, state: Boolean)
    fun setFavoriteTvshow(tvshow: TvshowEntityDomain, state: Boolean)
}