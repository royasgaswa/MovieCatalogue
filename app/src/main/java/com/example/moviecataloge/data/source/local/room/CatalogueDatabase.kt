package com.example.moviecataloge.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviecataloge.data.source.local.entity.MovieEntity
import com.example.moviecataloge.data.source.local.entity.TvshowEntity

@Database(
    entities = [MovieEntity::class, TvshowEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CatalogueDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvshowDao(): TvshowDao

}