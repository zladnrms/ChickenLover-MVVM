package defy.tech.chickenlover.adapter.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import defy.tech.chickenlover.R

class DataBindingAdapter {
    companion object {
        @JvmStatic @BindingAdapter("bind_image")
        fun imageLoad(imageView: ImageView, type_number: Int) {
            imageView.setImageResource(setImage(type_number))
        }

        fun setImage(type_number: Int): Int {
            when(type_number)
            {
                0 -> return R.drawable.fried
                1 -> return R.drawable.seasoned_fried
                2 -> return R.drawable.cheese_fried
                3 -> return R.drawable.soy_fried
                4 -> return R.drawable.green_onion_fried
                5 -> return R.drawable.garlic_fried
                6 -> return R.drawable.peoper_fried
                else -> return R.drawable.fried
            }
            return R.drawable.fried
        }
    }
}