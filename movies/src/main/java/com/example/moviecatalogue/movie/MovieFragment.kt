package com.example.moviecatalogue.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class MovieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    private val viewModel: MovieViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadKoinModules(movieModule)
        if (activity != null) {
            val movieAdapter = com.example.moviecatalogue.base.presentation.adapter.MovieAdapter()
            progress_bar.visibility = View.VISIBLE
            viewModel.getMovies()
            viewModel.movie.observe(viewLifecycleOwner,{movie->
                movieAdapter.setData(movie)
                movieAdapter.notifyDataSetChanged()
                rv_movie.scheduleLayoutAnimation()
            })
            viewModel.isLoading.observe(viewLifecycleOwner, { state ->
                if (!state) {
                    progress_bar.visibility = View.GONE
                }

            })



            with(rv_movie) {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }
}