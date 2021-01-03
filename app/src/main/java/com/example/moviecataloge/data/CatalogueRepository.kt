package com.example.moviecataloge.data

import com.example.moviecataloge.data.source.local.LocalDataSource
import com.example.moviecataloge.data.source.local.entity.MovieEntity
import com.example.moviecataloge.data.source.local.entity.TvshowEntity
import com.example.moviecataloge.data.source.remote.RemoteDataSource
import com.example.moviecataloge.data.source.remote.network.ApiResponse
import com.example.moviecataloge.data.source.remote.response.movie.MovieResponse
import com.example.moviecataloge.data.source.remote.response.tvshow.TvshowResponse
import com.example.moviecataloge.data.vo.Resource
import com.example.moviecataloge.domain.model.MovieEntityDomain
import com.example.moviecataloge.domain.model.TvshowEntityDomain
import com.example.moviecataloge.domain.repository.ICatalogueRepository
import com.example.moviecataloge.utils.AppExecutors
import com.example.moviecataloge.utils.MovieDataMapper
import com.example.moviecataloge.utils.TvshowDataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class CatalogueRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ICatalogueRepository {

    override fun getAllMovies(): Flow<Resource<List<MovieEntityDomain>>> =
        object:NetworkBoundResource<List<MovieEntityDomain>,List<MovieResponse?>>(){
            override fun loadFromDB(): Flow<List<MovieEntityDomain>> {
                return localDataSource.getAllMovies().map {
                    MovieDataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieEntityDomain>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse?>>> =
                remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<MovieResponse?>) {
                val movieList=MovieDataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getDetailMovie(movieId: Int?): Flow<Resource<MovieEntityDomain>> =
        object :NetworkBoundResource<MovieEntityDomain,List<MovieResponse?>>(){
            override fun loadFromDB(): Flow<MovieEntityDomain> {
                return localDataSource.getMovieById(movieId).map {
                    val data =ArrayList<MovieEntity>()
                    data.add(it)
                    MovieDataMapper.mapEntitiesToDomain(data)[0]
                }
            }

            override fun shouldFetch(data: MovieEntityDomain?): Boolean = data!=null

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse?>>> =
                remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<MovieResponse?>) {
                val movieList=MovieDataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getAllTvshows(): Flow<Resource<List<TvshowEntityDomain>>> =
        object : NetworkBoundResource<List<TvshowEntityDomain>,List<TvshowResponse?>>(){
            override fun loadFromDB(): Flow<List<TvshowEntityDomain>> {
                return localDataSource.getAllTvshows().map {
                    TvshowDataMapper.mapEntitiesToDomain(it)
                }
            }
            override fun shouldFetch(data: List<TvshowEntityDomain>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TvshowResponse?>>> =
                remoteDataSource.getTvshow()


            override suspend fun saveCallResult(data: List<TvshowResponse?>) {
                val tvshowList=TvshowDataMapper.mapResponsesToEntities(data)
                localDataSource.insertTvshow(tvshowList)
            }
        }.asFlow()

    override fun getDetailTvshow(tvshowId: Int?): Flow<Resource<TvshowEntityDomain>> =
        object : NetworkBoundResource<TvshowEntityDomain,List<TvshowResponse?>>(){
            override fun loadFromDB(): Flow<TvshowEntityDomain> {
                return localDataSource.getTvshowById(tvshowId).map {
                    val data=ArrayList<TvshowEntity>()
                    data.add(it)
                    TvshowDataMapper.mapEntitiesToDomain(data)[0]
                }
            }

            override fun shouldFetch(data: TvshowEntityDomain?): Boolean = data!=null

            override suspend fun createCall(): Flow<ApiResponse<List<TvshowResponse?>>> =
                remoteDataSource.getTvshow()

            override suspend fun saveCallResult(data: List<TvshowResponse?>) {
                val tvshowList=TvshowDataMapper.mapResponsesToEntities(data)
                localDataSource.insertTvshow(tvshowList)
            }
        }.asFlow()

    override fun getFavoriteMovie(): Flow<List<MovieEntityDomain>> {
        return localDataSource.getFavoriteMovies().map {
            MovieDataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getFavoriteTvshow(): Flow<List<TvshowEntityDomain>> {
        return localDataSource.getFavoriteTvshow().map {
            TvshowDataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: MovieEntityDomain, state: Boolean) {
        val movieEntity=MovieDataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute{localDataSource.setFavoriteMovie(movieEntity,state)}
    }

    override fun setFavoriteTvshow(tvshow: TvshowEntityDomain, state: Boolean) {
        val tvshowEntity = TvshowDataMapper.mapDomainToEntity(tvshow)
        appExecutors.diskIO().execute{localDataSource.setFavoriteTvshow(tvshowEntity,state)}
    }


}