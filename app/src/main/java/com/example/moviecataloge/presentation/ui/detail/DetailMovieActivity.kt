package com.example.moviecataloge.presentation.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviecataloge.R
import com.example.moviecataloge.data.source.local.entity.MovieEntity
import com.example.moviecataloge.data.vo.Resource
import com.example.moviecataloge.domain.model.MovieEntityDomain
import com.example.moviecataloge.presentation.model.MovieEntityPresentation
import com.example.moviecataloge.presentation.ui.detail.animation.SharedElementViewProvider
import com.example.moviecataloge.presentation.ui.detail.viewmodel.DetailMovieViewModel
import com.example.moviecataloge.utils.MovieDataMapper
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.rate_star.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private val viewModel: DetailMovieViewModel by viewModel()
    private var menu: Menu? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        SharedElementViewProvider(
            window
        ).init()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail_movie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        progress_bar.visibility = View.VISIBLE
        if (extras != null) {
            val movieId = extras.getInt(EXTRA_MOVIE)
            viewModel.setSelectedMovie(movieId)
            viewModel.getDetailMovie()
            viewModel.isLoading.observe(this,{state->
                if (!state){
                    progress_bar.visibility=View.GONE
                }
            })
            viewModel.movie.observe(this,{
                populateMovie(it)
            })
        }
    }

    fun populateMovie(movie: MovieEntityPresentation) {
        tv_title_movie.text = movie.title
        rating.text = movie.rate
        tv_date_movie.text = movie.releaseDate
        tv_content_movie.text = movie.overview
        Log.d("back", movie.backdropPath)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/"+movie.backdropPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(img_movie_main)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/"+movie.posterPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(img_movie_photo)
        val state=movie.isFavorite
        setFavoriteState(state)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            viewModel.setFavorite()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavoriteState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_unfavorite)
        }
    }
}