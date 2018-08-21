package com.example.flexibleadapterstickyheaderexample.activity

import android.app.SearchManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.example.flexibleadapterstickyheaderexample.R
import com.example.flexibleadapterstickyheaderexample.adapter.items.SimpleHeaderItem
import com.example.flexibleadapterstickyheaderexample.adapter.items.SimpleItem
import com.example.flexibleadapterstickyheaderexample.manager.FLMFlowLayoutManager
import com.example.flexibleadapterstickyheaderexample.manager.FlexibleFlowLayoutManager
import com.example.flexibleadapterstickyheaderexample.utils.MenuColorize
import com.google.android.material.appbar.AppBarLayout
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.flexibleadapter.items.IHeader
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(),
        SearchView.OnQueryTextListener {

    private val tag = "MainActivity"

    private lateinit var adapter: FlexibleAdapter<IFlexible<*>>
    private lateinit var searchView: SearchView

    private var searchViewFirstCreate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initActionBar()
        adapter = FlexibleAdapter(getExampleItems(), this, true)
        recycler_view.layoutManager = FlexibleFlowLayoutManager(FLMFlowLayoutManager.VERTICAL)
//        recycler_view.layoutManager = SmoothScrollLinearLayoutManager(this)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        adapter
                .setDisplayHeadersAtStartUp(true)
                .setStickyHeaders(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        MenuColorize.colorMenu(this, menu, ContextCompat.getColor(this, android.R.color.white))
        initSearchView()
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (adapter.hasNewFilter(newText)) {
            adapter.setFilter(newText)
            adapter.filterItems()
        }
        return true
    }

    override fun onBackPressed() {
        if (if (!searchViewFirstCreate) !searchView.isIconified else searchView.isIconified) {
            toolbar.collapseActionView()
            return
        }
        super.onBackPressed()
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
            it.title = "Choose skills"
        }
    }

    private fun initSearchView() {
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView = toolbar.menu.findItem(R.id.action_search).actionView as SearchView

        searchView.inputType = InputType.TYPE_TEXT_VARIATION_FILTER
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE or EditorInfo.IME_FLAG_NO_FULLSCREEN
        searchView.queryHint = "Search skills"
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false) // The search_mag_icon set background color will failure, if not add this code.
        searchView.isIconified = true
        searchView.setOnQueryTextListener(this)
        searchView.setOnSearchClickListener { searchViewFirstCreate = false }
        searchViewFirstCreate = true

        val searchSrcText = searchView.findViewById(androidx.appcompat.R.id.search_src_text) as TextView
        searchSrcText.setHintTextColor(Color.parseColor("#42FFFFFF"))
        searchSrcText.setTextColor(Color.parseColor("#FFFFFF"))

        val searchMagIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon) as ImageView
        searchMagIcon.setColorFilter(Color.parseColor("#FFFFFF"))

        val searchCloseIcon = searchView.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView
        searchCloseIcon.setColorFilter(Color.parseColor("#FFFFFF"))

        val searchPlate = searchView.findViewById(androidx.appcompat.R.id.search_plate) as View
        searchPlate.background.setColorFilter(Color.parseColor("#42FFFFFF"), PorterDuff.Mode.MULTIPLY)

        // Set backIcon color of toolbar.
        app_bar_layout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, _ ->
            for (i in 0 until toolbar.childCount) {
                val view = toolbar.getChildAt(i)
                if (view is ImageButton) {
                    val drawable = view.drawable
                    drawable.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
                    view.setImageDrawable(drawable)
                }
            }
        })
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
            Log.d(tag, "HEADER: $header")
            Log.d(tag, "SIMPLE_ITEM: " + newSimpleItem(i + 1, header))
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


    /*        val header1: SimpleHeaderItem? = newHeader(1)
        val header2: SimpleHeaderItem? = newHeader(2)

        items.add(newSimpleItem(1, header1))
        items.add(newSimpleItem(2, header1))
        items.add(newSimpleItem(3, header2))
        items.add(newSimpleItem(4, header2))
        items.add(newSimpleItem(5, header2))
        items.add(newSimpleItem(6, header2))
        items.add(newSimpleItem(7, header2))
        items.add(newSimpleItem(8, header2))
        items.add(newSimpleItem(9, header2))
        items.add(newSimpleItem(9, header2))
        items.add(newSimpleItem(9, header2))
        items.add(newSimpleItem(10, header2))
        items.add(newSimpleItem(11, header2))
        items.add(newSimpleItem(12, header2))
        items.add(newSimpleItem(13, header2))
        items.add(newSimpleItem(14, header2))
        items.add(newSimpleItem(15, header2))*/
}
