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
import com.example.moviecataloge.data.vo.Resource
import com.example.moviecataloge.domain.model.TvshowEntityDomain
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
            viewModel.getTvshow.observe(this, Observer { tvshow ->
                if (tvshow != null) {
                    when (tvshow) {
                        is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                        is Resource.Success -> if (tvshow.data != null) {
                            progress_bar.visibility = View.GONE
                            populateTvshow(tvshow.data)
                        }
                        is Resource.Error -> {
                            progress_bar.visibility = View.GONE
                            Toast.makeText(
                                applicationContext,
                                "Terjadi kesalahan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
        }
    }

    fun populateTvshow(tvshow: TvshowEntityDomain) {
        Log.d("tes", tvshow.firstAirDate + " " + tvshow.name)
        tv_title_tvshow.text = tvshow.name
        date_tvshow.text = tvshow.firstAirDate

        rating.text = tvshow.rate.toString()
        tv_content_tvshow.text = tvshow.overview
        Glide.with(this)
            .load(tvshow.backdropPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(img_tvshow_main)
        Glide.with(this)
            .load(tvshow.posterPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(img_tvshow_second)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        viewModel.getTvshow.observe(this, Observer { tvshow ->
            if (tvshow != null) {
                when (tvshow) {
                    is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                    is Resource.Success -> if (tvshow.data != null) {
                        progress_bar.visibility = View.GONE
                        populateTvshow(tvshow.data)
                    }
                    is Resource.Error -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(
                            applicationContext,
                            "Terjadi kesalahan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
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