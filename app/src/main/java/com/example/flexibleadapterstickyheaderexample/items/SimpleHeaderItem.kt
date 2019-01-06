package com.example.flexibleadapterstickyheaderexample.items

import android.graphics.PorterDuff
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flexibleadapterstickyheaderexample.R
import com.example.flexibleadapterstickyheaderexample.utils.ThemeUtils
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.*
import eu.davidea.flexibleadapter.utils.DrawableUtils
import eu.davidea.viewholders.ExpandableViewHolder
import kotlinx.android.synthetic.main.item_simple_header.view.*
import java.util.*

/**
 * @author Alan Dreamer
 * @since 08/13/2018 Created
 */
data class SimpleHeaderItem(val id: String,
                            val name: String) : AbstractHeaderItem<SimpleHeaderItem.ViewHolder>(),
        IFilterable<String>,
        IExpandable<SimpleHeaderItem.ViewHolder, SimpleItem>,
        IHeader<SimpleHeaderItem.ViewHolder> {

    private var expanded = false
    private var subItems: MutableList<SimpleItem> = ArrayList()

    init {
        isExpanded = false//Close collapsed by default
    }

    override fun isExpanded(): Boolean = expanded

    override fun setExpanded(expanded: Boolean) {
        this.expanded = expanded
    }

    override fun getExpansionLevel(): Int = 0

    override fun getSubItems(): MutableList<SimpleItem> = subItems

    fun addSubItem(subItem: SimpleItem) {
        subItems.add(subItem)
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_simple_header
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>, holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        holder.apply {
            val context = itemView.context
            if (payloads.size == 0) {
                // Background, when bound the first time
                val drawable = DrawableUtils.getSelectableBackgroundCompat(
                        ThemeUtils.getThemeValue(android.R.attr.windowBackground, context),
                        0,
                        DrawableUtils.getColorControlHighlight(context))
                DrawableUtils.setBackgroundCompat(list, drawable)
            }
            text.text = name
            skillIcon.setImageResource(R.drawable.ic_android_black_24dp)
            actionIcon.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
            if (isExpanded) {
                actionIcon.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
                skillIcon.setColorFilter(ThemeUtils.getThemeValue(R.attr.colorSecondary, context), PorterDuff.Mode.SRC_IN)
                text.setTextColor(ThemeUtils.getThemeValue(R.attr.colorSecondary, context))
            } else {
                actionIcon.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
                skillIcon.setColorFilter(ContextCompat.getColor(context, R.color.secondary_color), PorterDuff.Mode.SRC_IN)
                text.setTextColor(ThemeUtils.getPrimaryTextColor(context))
            }
        }
    }

    override fun filter(constraint: String?): Boolean {
        return name.toLowerCase().trim { it <= ' ' }.contains(constraint!!)
    }

    class ViewHolder(view: View, adapter: FlexibleAdapter<*>) : ExpandableViewHolder(view, adapter, true) {
        var list: ConstraintLayout = view.list
        var text: AppCompatTextView = view.text
        var skillIcon: AppCompatImageView = view.skill_icon
        var actionIcon: AppCompatImageView = view.action_icon
    }
}