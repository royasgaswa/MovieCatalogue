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
import com.example.moviecatalogue.core.presentation.model.TvshowEntityPresentation
import com.example.moviecataloge.presentation.ui.detail.animation.SharedElementViewProvider
import com.example.moviecataloge.presentation.ui.detail.viewmodel.DetailTvshowViewModel
import kotlinx.android.synthetic.main.activity_detail_tvshow.*
import kotlinx.android.synthetic.main.rate_star.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailTvshowActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TVSHOW = "extra_tvshow"
    }

    private val viewModel: DetailTvshowViewModel by viewModel()
    private var menu: Menu? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        SharedElementViewProvider(
            window
        ).init()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tvshow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        progress_bar.visibility = View.VISIBLE
        if (extras != null) {
            val tvshowId = extras.getInt(EXTRA_TVSHOW)
            viewModel.setSelectedTvshow(tvshowId)
            viewModel.getDetailTvshow()
            viewModel.isLoading.observe(this,{state->
                if (!state){
                    progress_bar.visibility=View.GONE
                }
            })
            viewModel.tvshow.observe(this,{
                populateTvshow(it)
            })
        }
    }

    fun populateTvshow(tvshow: TvshowEntityPresentation) {
        Log.d("tes", tvshow.firstAirDate + " " + tvshow.name)
        tv_title_tvshow.text = tvshow.name
        date_tvshow.text = tvshow.firstAirDate

        rating.text = tvshow.rate.toString()
        tv_content_tvshow.text = tvshow.overview
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/"+tvshow.backdropPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(img_tvshow_main)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/"+tvshow.posterPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(img_tvshow_second)
        val state=tvshow.isFavorite
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