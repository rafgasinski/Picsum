package com.example.picsum.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.picsum.R

@BindingAdapter("loadImageAndCrop")
fun ImageView.loadImageAndCrop(url: String?) {
    if (url?.isNotEmpty() == true) {
        GlideApp.with(context)
            .load(url)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(this)
    } else
        GlideApp.with(context)
            .load(R.drawable.ic_error)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}

@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String?) {
    if (url?.isNotEmpty() == true) {
        GlideApp.with(context)
            .load(url)
            .centerInside()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(this)
    } else
        GlideApp.with(context)
            .load(R.drawable.ic_error)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}