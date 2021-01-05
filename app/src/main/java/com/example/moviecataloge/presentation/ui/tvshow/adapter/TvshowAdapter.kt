package com.example.moviecataloge.presentation.ui.tvshow.adapter

import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviecataloge.R
import com.example.moviecataloge.domain.model.TvshowEntityDomain
import com.example.moviecataloge.presentation.model.TvshowEntityPresentation
import com.example.moviecataloge.presentation.ui.CastedContex
import com.example.moviecataloge.presentation.ui.detail.DetailTvshowActivity
import kotlinx.android.synthetic.main.items_tvshow.view.*

class TvshowAdapter :
    RecyclerView.Adapter<TvshowAdapter.TvshowViewHolder>() {

    private var listData=ArrayList<TvshowEntityPresentation>()

    fun setData(newListData:List<TvshowEntityPresentation>?){
        if(newListData == null)return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvshowAdapter.TvshowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_tvshow, parent, false)
        return TvshowViewHolder(view)
    }


    override fun onBindViewHolder(holder: TvshowAdapter.TvshowViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)

    }

    class TvshowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvshow: TvshowEntityPresentation) {
            with(itemView) {
                tv_title_tvShow.text = tvshow.name
                tv_date_tvshow.text = tvshow.firstAirDate
                setOnClickListener {
                    val intent = Intent(context, DetailTvshowActivity::class.java).apply {
                        putExtra(DetailTvshowActivity.EXTRA_TVSHOW, tvshow.id)
                    }
                    val option = ActivityOptions.makeSceneTransitionAnimation(
                        CastedContex.scanForActivity(context),
                        img_tv_photo,
                        resources.getString(R.string.sharedView)
                    )
                    context.startActivity(intent, option.toBundle())
                }
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500/"+tvshow.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(img_tv_photo)
            }
        }
    }

    override fun getItemCount(): Int =listData.size
}