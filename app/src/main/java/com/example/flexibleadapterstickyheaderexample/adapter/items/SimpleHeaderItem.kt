package com.example.flexibleadapterstickyheaderexample.adapter.items

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flexibleadapterstickyheaderexample.R
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.*
import eu.davidea.flexibleadapter.utils.DrawableUtils
import eu.davidea.viewholders.ExpandableViewHolder
import kotlinx.android.synthetic.main.item_simple_header.view.*
import timber.log.Timber
import java.util.*

/**
 * @author Alan Dreamer
 * @since 08/13/2018 Created
 */
data class SimpleHeaderItem(val id: String,
                            val text: String) : AbstractHeaderItem<SimpleHeaderItem.ViewHolder>(),
        IFilterable<String>,
        IExpandable<SimpleHeaderItem.ViewHolder, SimpleItem>,
        IHeader<SimpleHeaderItem.ViewHolder> {

    /* Flags for FlexibleAdapter */
    private var expanded = false

    /* subItems list */
    private var subItems: MutableList<SimpleItem> = ArrayList()

    init {
        isExpanded = false//Close collapsed by default
    }

    override fun isExpanded(): Boolean {
        return expanded
    }

    override fun setExpanded(expanded: Boolean) {
        this.expanded = expanded
    }

    override fun getExpansionLevel(): Int {
        return 0
    }

    override fun getSubItems(): MutableList<SimpleItem> {
        return subItems
    }

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
        if (payloads.size > 0) {
            Timber.d("HeaderItem $text Payload $payloads")
        } else {
            val context = holder.itemView.context
            holder.text.text = text
            holder.skillIcon.setImageResource(R.drawable.ic_android_black_24dp)
            holder.actionIcon.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
            val drawable = DrawableUtils.getSelectableBackgroundCompat(
                    getWindowBackgroundColor(context),
                    0,
                    DrawableUtils.getColorControlHighlight(context))
            DrawableUtils.setBackgroundCompat(holder.root, drawable)
        }
        if (isExpanded) {
            holder.divider.visibility = View.VISIBLE
            holder.actionIcon.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
        } else {
            holder.divider.visibility = View.INVISIBLE
            holder.actionIcon.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
        }
    }

    @ColorInt
    fun getWindowBackgroundColor(context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(android.R.attr.windowBackground, value, true)
        return value.data
    }

    override fun filter(constraint: String?): Boolean {
        return text.toLowerCase().trim { it <= ' ' }.contains(constraint!!)
    }

    class ViewHolder(view: View, adapter: FlexibleAdapter<*>) : ExpandableViewHolder(view, adapter, true) {
        var skillIcon: AppCompatImageView = view.skill_icon
        var text: AppCompatTextView = view.text
        var actionIcon: AppCompatImageView = view.action_icon
        var root: LinearLayout = view.root
        var divider: View = view.divider

        override fun shouldNotifyParentOnClick(): Boolean {
            return true
        }
    }
}