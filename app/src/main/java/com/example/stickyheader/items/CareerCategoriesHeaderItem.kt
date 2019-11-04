package com.example.stickyheader.items

import android.content.Context
import android.graphics.PorterDuff
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.stickyheader.R
import com.example.stickyheader.utils.ColorUtils
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.Payload
import eu.davidea.flexibleadapter.items.AbstractHeaderItem
import eu.davidea.flexibleadapter.items.IExpandable
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.ExpandableViewHolder
import kotlinx.android.synthetic.main.item_career_categories_header.view.*

/**
 * @author Alan Dreamer
 * @since 2019-01-06 Created
 */
data class CareerCategoriesHeaderItem(
        val id: String,
        val text: String,
        @DrawableRes val icon: Int
) : AbstractHeaderItem<CareerCategoriesHeaderItem.ViewHolder>(),
        IExpandable<CareerCategoriesHeaderItem.ViewHolder, CareerCategoriesItem> {

    private var expanded = false

    private var categoriesItem: MutableList<CareerCategoriesItem> = arrayListOf()

    init {
        isExpanded = false //Close collapsed by default
    }

    fun addSubItem(subItem: CareerCategoriesItem) {
        categoriesItem.add(subItem)
    }

    override fun getExpansionLevel(): Int = 0

    override fun isExpanded(): Boolean = expanded

    override fun setExpanded(expanded: Boolean) {
        this.expanded = expanded
    }

    override fun getSubItems(): MutableList<CareerCategoriesItem> = categoriesItem

    override fun getLayoutRes(): Int {
        return R.layout.item_career_categories_header
    }

    override fun createViewHolder(
            view: View,
            adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun bindViewHolder(
            adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
            holder: ViewHolder,
            position: Int,
            payloads: MutableList<Any>
    ) {
        val context = holder.itemView.context
        if (payloads.isNotEmpty()) {
            when (payloads[0]) {
                Payload.EXPANDED -> updateView(context, holder)
            }
        }
        holder.text.text = text
        holder.skillIcon.setImageResource(icon)
        holder.actionIcon.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp)

        updateView(context, holder)
    }

    private fun updateView(context: Context, holder: ViewHolder) {
        if (isExpanded) {
            holder.divider.isVisible = true
            holder.actionIcon.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
            holder.skillIcon.setColorFilter(
                    ColorUtils.getThemeColor(R.attr.colorSecondary, context),
                    PorterDuff.Mode.SRC_IN
            )
            holder.text.setTextColor(ColorUtils.getThemeColor(R.attr.colorSecondary, context))
        } else {
            holder.divider.isVisible = false
            holder.actionIcon.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
            holder.skillIcon.setColorFilter(
                    ContextCompat.getColor(context, R.color.secondary_color),
                    PorterDuff.Mode.SRC_IN
            )
            holder.text.setTextColor(ColorUtils.getTextColorPrimary(context))
        }
    }

    class ViewHolder(view: View, adapter: FlexibleAdapter<*>) : ExpandableViewHolder(view, adapter, true) {
        var divider: View = view.divider
        var text: AppCompatTextView = view.text
        var skillIcon: AppCompatImageView = view.skill_icon
        var actionIcon: AppCompatImageView = view.action_icon
    }
}