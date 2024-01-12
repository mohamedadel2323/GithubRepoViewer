package com.example.githubrepoviewer.utils

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.githubrepoviewer.R

@BindingAdapter("count")
fun setStarsCount(textView: TextView, starCount: Int?) {
    textView visibleIf (starCount != null)
    starCount?.let {
        textView.text = it.toString()
    }
}

@BindingAdapter("visibleIf")
fun showIfThereIsValue(textView: TextView, starCount: Int?) {
    textView visibleIf (starCount != null)
}

@BindingAdapter("hideIf")
fun hideIfThereIsValue(textView: TextView, starCount: Int?) {
    textView visibleIf (starCount == null)
}

@BindingAdapter("hideTextIf")
fun hideTextIfEmpty(textView: TextView, text: String?) {
    text?.let {
        textView visibleIf (it.isNotEmpty())
    }
}

@BindingAdapter("setColor")
fun setColorToStateField(textView: TextView, text: String?) {
    text?.let {
        when (text) {
            "open" -> textView.setTextColor(ContextCompat.getColor(textView.context, R.color.red))
            "closed" -> textView.setTextColor(
                ContextCompat.getColor(
                    textView.context,
                    R.color.green
                )
            )
        }
    }
}