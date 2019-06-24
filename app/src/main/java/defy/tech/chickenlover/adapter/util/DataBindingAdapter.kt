package defy.tech.chickenlover.adapter.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter

class DataBindingAdapter {
    companion object {
        @JvmStatic @BindingAdapter("bind_image")
        fun imageLoad(imageView: ImageView, resource: Int) {
            Log.d("하하", "d"+resource)
            imageView.setImageResource(resource)
        }
    }
}