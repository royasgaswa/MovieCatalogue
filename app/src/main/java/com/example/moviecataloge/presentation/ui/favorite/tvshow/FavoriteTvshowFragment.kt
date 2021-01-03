package com.example.moviecataloge.presentation.ui.favorite.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviecataloge.R
import com.example.moviecataloge.presentation.ui.tvshow.adapter.TvshowAdapter
import kotlinx.android.synthetic.main.fragment_favorite_tvshow.*
import org.koin.android.viewmodel.ext.android.viewModel


class FavoriteTvshowFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tvshow, container, false)
    }

    private val viewModel: FavoriteTvshowViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val tvshowFavoriteAdapter = TvshowAdapter()
            progress_bar.visibility = View.VISIBLE
            viewModel.getFavoriteTvshow.observe(viewLifecycleOwner, Observer { tvshows ->
                progress_bar.visibility = View.GONE
                tvshowFavoriteAdapter.setData(tvshows)
                tvshowFavoriteAdapter.notifyDataSetChanged()
                rv_favorite_tvshow.scheduleLayoutAnimation()
            })


            with(rv_favorite_tvshow) {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = tvshowFavoriteAdapter
            }
        }
    }
}