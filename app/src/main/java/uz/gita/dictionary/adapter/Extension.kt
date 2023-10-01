package uz.gita.dictionary.adapter

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import uz.gita.dictionary.R

fun String.spannable(query: String, context: Context): SpannableString {
    val span = SpannableString(this)
    val color = ForegroundColorSpan(context.getColor(R.color.search))
    val startIndex = this.indexOf(query, 0, true)
    if (startIndex > -1) {
        span.setSpan(
            color,
            startIndex,
            startIndex + query.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return span
}