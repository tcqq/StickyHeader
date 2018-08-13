package com.example.flexibleadapterstickyheaderexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.flexibleadapter.items.IHeader
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter: FlexibleAdapter<IFlexible<*>> = FlexibleAdapter(getExampleItems(), this, true)
        recycler_view.layoutManager = SmoothScrollLinearLayoutManager(this)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        adapter
                .setDisplayHeadersAtStartUp(true)
                .setStickyHeaders(true)
    }

    private fun getExampleItems(): List<IFlexible<*>> {
        val items = ArrayList<IFlexible<*>>()
        var lastHeaderId = 0
        val size = 400
        val headers = 30
        var header: SimpleHeaderItem? = null

        for (i in 0 until size) {
            header = if (i % Math.round((size / headers).toFloat()) == 0) newHeader(++lastHeaderId) else header
            items.add(newSimpleItem(i + 1, header))
        }
        return items
    }

    /*
     * Creates a Header item.
     */
    private fun newHeader(i: Int): SimpleHeaderItem {
        return SimpleHeaderItem("H$i", "Header $i")
    }

    /*
     * Creates a normal item with a Header linked.
     */
    private fun newSimpleItem(i: Int, header: IHeader<*>?): SimpleItem {
        return SimpleItem("I$i", "Simple Item $i", header as SimpleHeaderItem)
    }
}
