package com.example.carrotmarket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ImageSliderAdapter(context: Context, sliderImage: Array<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder>() {
    private val context: Context = context
    private val sliderImage: Array<String> = sliderImage
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindSliderImage(sliderImage[position])
    }

    override fun getItemCount(): Int {
        return sliderImage.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mImageView: ImageView = itemView.findViewById(R.id.imageSlider)
        fun bindSliderImage(imageURL: String?) {
            Glide.with(context)
                .load(imageURL)
                .into(mImageView)
        }

    }

}