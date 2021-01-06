package com.example.moviecatalogue.core.di

import androidx.room.Room
import com.example.moviecatalogue.core.data.CatalogueRepository
import com.example.moviecatalogue.core.data.source.local.LocalDataSource
import com.example.moviecatalogue.core.data.source.local.room.CatalogueDatabase
import com.example.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.example.moviecatalogue.core.data.source.remote.network.ApiService
import com.example.moviecatalogue.core.domain.repository.ICatalogueRepository
import com.example.moviecatalogue.core.utils.AppExecutors
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
    }
    factory {
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
    single { RemoteDataSource(apiService =  get()) }
    factory { AppExecutors() }
    single<ICatalogueRepository> {
        CatalogueRepository(
            remoteDataSource = get(),
            localDataSource = get(),
            appExecutors = get()
        )
    }
}