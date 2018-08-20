package com.example.flexibleadapterstickyheaderexample.adapter.items

import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flexibleadapterstickyheaderexample.R
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractHeaderItem
import eu.davidea.flexibleadapter.items.IFilterable
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder

/**
 * @author Alan Dreamer
 * @since 08/13/2018 Created
 */
data class SimpleHeaderItem(val id: String,
                            val text: String) : AbstractHeaderItem<SimpleHeaderItem.ViewHolder>(),
        IFilterable<String> {

    override fun getLayoutRes(): Int {
        return R.layout.item_simple_header
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>, holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.size > 0) {
            Log.d(this.javaClass.simpleName, "HeaderItem $text Payload $payloads")
        } else {
            holder.text.text = text
        }
    }

    override fun filter(constraint: String?): Boolean {
        return text.toLowerCase().trim { it <= ' ' }.contains(constraint!!)
    }

    class ViewHolder(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter, true) {
        var text: AppCompatTextView = view.findViewById(R.id.text)
    }
}