package com.example.moviecataloge.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvshowEntityDomain(
    val id: Int? = null,
    val name: String? = null,
    val firstAirDate: String? = null,
    val rate: Double? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val isFavorite:Boolean
) : Parcelable