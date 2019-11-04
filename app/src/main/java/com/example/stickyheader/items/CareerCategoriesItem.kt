package com.example.stickyheader.items

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.stickyheader.R
import com.example.stickyheader.utils.ColorUtils
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder

/**
 * @author Alan Dreamer
 * @since 2019-01-06 Created
 */
data class CareerCategoriesItem(val id: String,
                                val text: String,
                                val isFooter: Boolean = false) : AbstractFlexibleItem<CareerCategoriesItem.ViewHolder>() {

    override fun getLayoutRes(): Int {
        return R.layout.item_career_categories
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>, holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        val context = holder.itemView.context
        holder.text.text = text
        holder.divider.isVisible = isFooter
        if (isFooter) {
            holder.text.setTextColor(ColorUtils.getThemeColor(R.attr.colorSecondary, context))
        } else {
            holder.text.setTextColor(ColorUtils.getThemeColor(R.attr.colorOnSurface, context))
        }
    }

    class ViewHolder(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter) {
        var text: AppCompatTextView = view.findViewById(R.id.text)
        var divider: View = view.findViewById(R.id.divider)
    }
}