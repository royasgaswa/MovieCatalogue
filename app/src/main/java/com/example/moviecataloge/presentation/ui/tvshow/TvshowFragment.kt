package com.example.moviecataloge.presentation.ui.tvshow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviecataloge.R
import com.example.moviecataloge.data.vo.Resource
import com.example.moviecataloge.presentation.ui.tvshow.adapter.TvshowAdapter
import kotlinx.android.synthetic.main.fragment_tvshow.*
import org.koin.android.viewmodel.ext.android.viewModel

class TvshowFragment : Fragment() {
    private val viewModel: TvshowViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val tvshowAdapter = TvshowAdapter()
            progress_bar.visibility = View.VISIBLE
            viewModel.getTvshow.observe(viewLifecycleOwner, Observer { tvshows ->
                if (tvshows != null) {
                    when (tvshows) {
                        is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            Log.d("TES", tvshows.data?.size.toString())
                            progress_bar.visibility = View.GONE
                            tvshowAdapter.setData(tvshows.data)
                            tvshowAdapter.notifyDataSetChanged()
                            rv_tvshow.scheduleLayoutAnimation()
                        }
                        is Resource.Error -> {
                            progress_bar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(rv_tvshow) {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = tvshowAdapter
            }
        }
    }
}