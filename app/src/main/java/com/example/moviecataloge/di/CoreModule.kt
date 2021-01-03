package com.example.moviecataloge.di

import androidx.room.Room
import com.example.moviecataloge.data.CatalogueRepository
import com.example.moviecataloge.data.source.local.LocalDataSource
import com.example.moviecataloge.data.source.local.room.CatalogueDatabase
import com.example.moviecataloge.data.source.remote.RemoteDataSource
import com.example.moviecataloge.data.source.remote.network.ApiService
import com.example.moviecataloge.domain.repository.ICatalogueRepository
import com.example.moviecataloge.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule= module {
    factory {
        get<CatalogueDatabase>().movieDao()
        get<CatalogueDatabase>().tvshowDao()
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            CatalogueDatabase::class.java,"Catalogue.db"
        ).fallbackToDestructiveMigration().build()
    }
}
val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}
val repositoryModule = module {
    single { LocalDataSource(mMovieDao =  get(),mTvshowDao= get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<ICatalogueRepository> {
        CatalogueRepository(
            get(),
            get(),
            get()
        )
    }
}