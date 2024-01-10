package com.example.githubrepoviewer.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("count")
fun setStarsCount(textView: TextView, starCount:Int?){
    textView visibleIf (starCount != null)
    starCount?.let {
        textView.text = it.toString()
    }
}

@BindingAdapter("visibleIf")
fun showIfThereIsValue(textView: TextView, starCount:Int?){
    textView visibleIf (starCount != null)
}