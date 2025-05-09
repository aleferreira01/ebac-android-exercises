package br.com.alexander.awesomemovieapp.viewBinder

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("srcUrl")
fun ImageView.bindScrUrl(url: String) {
    Glide
        .with(this)
        .load(url)
        .centerInside()
        .into(this)
}