package com.pinch.codeassignment.igdb.ui.main.game_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.pinch.codeassignment.igdb.databinding.ItemSliderGameScreenshotViewBinding
import com.pinch.codeassignment.igdb.utils.loadImage
import com.smarteist.autoimageslider.SliderViewAdapter


class SliderAdapter(sliderDataArrayList: List<String>) :
    SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder>() {
    // list for storing urls of images.
    private val mSliderItems: List<String> = sliderDataArrayList

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterViewHolder {
        return SliderAdapterViewHolder(
            ItemSliderGameScreenshotViewBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    override fun onBindViewHolder(viewHolder: SliderAdapterViewHolder, position: Int) {
        val sliderItem: String = mSliderItems[position]

        loadImage(viewHolder.imageViewBackground, sliderItem)

    }

    // this method will return
    // the count of our list.
    override fun getCount(): Int {
        return mSliderItems.size
    }

    class SliderAdapterViewHolder(itemView: ItemSliderGameScreenshotViewBinding) :
        ViewHolder(itemView.root) {
        // Adapter class for initializing
        // the image of our slider view.
        val imageViewBackground: AppCompatImageView = itemView.imgSliderScreenshotItem


    }

}