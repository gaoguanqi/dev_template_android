package com.maple.commonlib.ext

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

fun ImageView.load(@DrawableRes resId: Int?) {
    this.load(resId)
}

fun ImageView.load(url: String?) {
    this.load(url)
}

fun ImageView.loadCircle(url: String?) {
    this.load(url) {
        transformations(CircleCropTransformation())
    }
}