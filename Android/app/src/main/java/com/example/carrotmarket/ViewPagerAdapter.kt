package com.example.carrotmarket

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_post_board.view.*
import kotlinx.android.synthetic.main.item_slider.view.*
import okhttp3.MultipartBody

class ViewPagerAdapter(context: Context, images : ArrayList<Bitmap>, data : ArrayList<MultipartBody.Part>, filename : ArrayList<String>)
    : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {
    private val context: Context = context;
    private val slideImage : ArrayList<Bitmap> = images;
    private val data = data
    private val filename = filename

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bindSliderImage(slideImage[position])
        holder.itemView.setOnClickListener() {
            showAlert(context, slideImage, holder.adapterPosition, data, filename)
        }
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

    private fun showAlert(context: Context, images: ArrayList<Bitmap>, index : Int, data: ArrayList<MultipartBody.Part>, filename : ArrayList<String>) {
        val myAlert = AlertDialog.Builder(context)
        myAlert.setTitle("사진 선택")
        myAlert.setMessage("사진을 삭제하시겠습니까?")
        myAlert.setPositiveButton("예") {
                dialogInterface : DialogInterface, i : Int ->
            data.removeAt(index)
            images.removeAt(index)
            filename.removeAt(index)
            this.notifyDataSetChanged()
        }
        myAlert.setNegativeButton("취소") {
                dialogInterface : DialogInterface, i : Int ->
            Toast.makeText(context, "canceled", Toast.LENGTH_SHORT).show()
        }
        myAlert.show()
    }
}