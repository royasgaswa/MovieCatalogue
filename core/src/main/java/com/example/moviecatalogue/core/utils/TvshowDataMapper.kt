package com.example.moviecatalogue.core.utils

import com.example.moviecatalogue.core.data.source.local.entity.TvshowEntity
import com.example.moviecatalogue.core.data.source.remote.response.tvshow.TvshowResponse
import com.example.moviecatalogue.core.domain.model.TvshowEntityDomain


object TvshowDataMapper {
    fun mapResponsesToEntities(input: List<TvshowResponse?>): List<TvshowEntity> {
        val tvshowList=ArrayList<TvshowEntity>()
        input.map {
            val tvshow= TvshowEntity(
                id = it?.id,
                title = it?.name,
                date = it?.firstAirDate,
                rate = it?.rate.toString(),
                overview = it?.overview,
                path = it?.posterPath,
                backdropPath = it?.backdropPath,
                favorite = false
            )
            tvshowList.add(tvshow)
        }
        return tvshowList
    }

    fun mapEntitiesToDomain(input: List<TvshowEntity>):List<TvshowEntityDomain> =
        input.map {
            TvshowEntityDomain(
                id = it.id,
                name = it.title,
                firstAirDate = it.date,
                rate = it.rate?.toDouble(),
                overview = it.overview,
                posterPath = it.path,
                backdropPath = it.backdropPath,
                isFavorite = it.favorite
            )
        }
    fun mapDomainToPresentation(input:List<TvshowEntityDomain>):List<com.example.moviecatalogue.base.presentation.model.TvshowEntityPresentation> =
        input.map {
            com.example.moviecatalogue.base.presentation.model.TvshowEntityPresentation(
                id = it.id,
                name = it.name,
                firstAirDate = it.firstAirDate,
                rate = it.rate,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                isFavorite = it.isFavorite
            )
        }
    fun mapDomainToEntity(input: TvshowEntityDomain)= TvshowEntity(
        id =input.id,
        title = input.name,
        date = input.firstAirDate,
        rate = input.rate.toString(),
        overview = input.overview,
        path = input.posterPath,
        backdropPath = input.backdropPath,
        favorite = input.isFavorite
    )
    fun mapPresentationToDomain(input: com.example.moviecatalogue.base.presentation.model.TvshowEntityPresentation) = TvshowEntityDomain(
        id = input.id,
        name = input.name,
        firstAirDate = input.firstAirDate,
        rate = input.rate,
        overview = input.overview,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        isFavorite = input.isFavorite
    )
}