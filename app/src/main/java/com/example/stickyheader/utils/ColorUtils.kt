package com.example.stickyheader.utils

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.NonNull

/**
 * @author Alan Perry
 * @since 2018/07/22 Created
 */
object ColorUtils {

    @ColorInt
    fun getThemeColor(@AttrRes attrResId: Int, @NonNull context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(attrResId, value, true)
        return value.data
    }

    @ColorInt
    fun getTextColorPrimary(@NonNull context: Context): Int {
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