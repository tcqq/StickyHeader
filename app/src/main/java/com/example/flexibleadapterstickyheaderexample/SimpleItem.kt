package com.example.flexibleadapterstickyheaderexample

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.flexibleadapter.items.ISectionable
import eu.davidea.viewholders.FlexibleViewHolder

/**
 * @author Alan Dreamer
 * @since 08/13/2018 Created
 */
data class SimpleItem(val id: String,
                      val text: String,
                      var headerItem: SimpleHeaderItem) : AbstractFlexibleItem<SimpleItem.ViewHolder>(),
        ISectionable<SimpleItem.ViewHolder, SimpleHeaderItem> {

    override fun setHeader(header: SimpleHeaderItem) {
        this.headerItem = header
    }

    override fun getHeader(): SimpleHeaderItem {
        return headerItem
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_simple
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>, holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        holder.text.text = text
    }

    class ViewHolder(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter) {
        var text: AppCompatTextView = view.findViewById(R.id.text)
    }
}