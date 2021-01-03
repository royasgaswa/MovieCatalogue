package com.example.moviecataloge.domain.usecase

import com.example.moviecataloge.data.vo.Resource
import com.example.moviecataloge.domain.model.MovieEntityDomain
import com.example.moviecataloge.domain.model.TvshowEntityDomain
import com.example.moviecataloge.domain.repository.ICatalogueRepository
import kotlinx.coroutines.flow.Flow

class CatalogueInteractor(private val catalogueRepository: ICatalogueRepository):CatalogueUseCase {
    override fun getAllMovies(): Flow<Resource<List<MovieEntityDomain>>> {
        return catalogueRepository.getAllMovies()
    }

    override fun getDetailMovie(movieId: Int?): Flow<Resource<MovieEntityDomain>> {
        return catalogueRepository.getDetailMovie(movieId)
    }

    override fun getAllTvshows(): Flow<Resource<List<TvshowEntityDomain>>> {
        return catalogueRepository.getAllTvshows()
    }

    override fun getDetailTvshow(tvshowId: Int?): Flow<Resource<TvshowEntityDomain>> {
        return catalogueRepository.getDetailTvshow(tvshowId)
    }

    override fun getFavoriteMovie(): Flow<List<MovieEntityDomain>> {
        return catalogueRepository.getFavoriteMovie()
    }

    override fun getFavoriteTvshow(): Flow<List<TvshowEntityDomain>> {
        return catalogueRepository.getFavoriteTvshow()
    }

    override fun setFavoriteMovie(movie: MovieEntityDomain, state: Boolean) {
        return catalogueRepository.setFavoriteMovie(movie,state)
    }

    override fun setFavoriteTvshow(tvshow: TvshowEntityDomain, state: Boolean) {
        return catalogueRepository.setFavoriteTvshow(tvshow, state)
    }
}