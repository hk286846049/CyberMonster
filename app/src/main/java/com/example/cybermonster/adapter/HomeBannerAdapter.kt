package com.example.cybermonster.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerAdapter

class HomeBannerAdapter(private var context: Context, var mDatas: List<Int>) : BannerAdapter<Int, HomeBannerAdapter.BannerViewHolder>(mDatas) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder, data: Int, position: Int, size: Int) {
        Glide.with(context).load(context.getDrawable(mDatas[position])).into(holder.imageView)
    }

    inner class BannerViewHolder(view: ImageView) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view
    }
}
