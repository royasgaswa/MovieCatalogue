package com.example.moviecataloge.utils

import com.example.moviecataloge.data.source.local.entity.TvshowEntity
import com.example.moviecataloge.data.source.remote.response.tvshow.TvshowResponse
import com.example.moviecataloge.domain.model.TvshowEntityDomain


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
}