package com.example.flexibleadapterstickyheaderexample.utils

import android.content.Context
import android.util.TypedValue
import androidx.annotation.ColorInt


/**
 * @author Alan Dreamer
 * @since 2018/07/22 Created
 */
object ThemeUtils {

    fun getThemeValue(resId: Int, context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(resId, value, true)
        return value.data
    }

    @ColorInt
    fun getPrimaryTextColor(context: Context): Int {
        // Get the primary text color of the theme
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true)
        val arr = context.obtainStyledAttributes(typedValue.data, intArrayOf(android.R.attr.textColorPrimary))
        val primaryColor = arr.getColor(0, -1)
        arr.recycle()
        return primaryColor
    }
}