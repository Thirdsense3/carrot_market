package com.example.carrotmarket

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_post_board.view.*
import kotlinx.android.synthetic.main.item_slider.view.*

class ViewPagerAdapter(context: Context, images : ArrayList<Bitmap>)
    : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {
    private val context: Context = context;
    private val slideImage : ArrayList<Bitmap> = images;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bindSliderImage(slideImage[position])
    }

    override fun getItemCount(): Int {
        return slideImage.size
    }

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mImageView : ImageView = itemView.findViewById(R.id.imageSlider)

        fun bindSliderImage(image : Bitmap) {
            mImageView.setImageBitmap(image)
        }
    }
}