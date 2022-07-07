package com.maple.commonlib.ext

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

@BindingAdapter("bind:loadRes")
fun ImageView.load(@DrawableRes resId: Int?) {
    this.load(resId)
}

@BindingAdapter("bind:loadUrl")
fun ImageView.load(url: String?) {
    this.load(url)
}

@BindingAdapter("bind:loadCircleUrl")
fun ImageView.loadCircle(url: String?) {
    this.load(url) {
        transformations(CircleCropTransformation())
    }
}