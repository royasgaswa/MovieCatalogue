package com.example.moviecataloge.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tvshowentities")
data class TvshowEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "date")
    var date: String?,

    @ColumnInfo(name = "rate")
    var rate: String?,

    @ColumnInfo(name = "overview")
    var overview: String?,

    @ColumnInfo(name = "path")
    var path: String?,

    @ColumnInfo(name = "backdropPath")
    var backdropPath: String?,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
) : Parcelable