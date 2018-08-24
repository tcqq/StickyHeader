package com.example.flexibleadapterstickyheaderexample.adapter.items

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flexibleadapterstickyheaderexample.R
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFilterable
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.flexibleadapter.utils.FlexibleUtils
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_simple.view.*

/**
 * @author Alan Dreamer
 * @since 08/13/2018 Created
 */
data class SimpleItem(val id: String,
                      val text: String,
                      val isLastItemFromGroup: Boolean) : AbstractFlexibleItem<SimpleItem.ViewHolder>(),
        IFilterable<String> {

    override fun getLayoutRes(): Int {
        return R.layout.item_simple
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>, holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (adapter.hasFilter()) {
            FlexibleUtils.highlightWords(holder.text, text, adapter.getFilter(String::class.java))
        } else {
            holder.text.text = text
            holder.divider.visibility = isLastItemFromGroup.let {
                if (it) View.VISIBLE
                else View.INVISIBLE
            }
        }
    }

    override fun filter(constraint: String?): Boolean {
        for (word in constraint?.split(FlexibleUtils.SPLIT_EXPRESSION.toRegex())!!.dropLastWhile { it.isEmpty() }.toTypedArray()) {
            if (text.toLowerCase().contains(word)) {
                return true
            }
        }
        return false
    }

    class ViewHolder(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter) {
        var text: AppCompatTextView = view.text
        var divider: View = view.divider
    }
}