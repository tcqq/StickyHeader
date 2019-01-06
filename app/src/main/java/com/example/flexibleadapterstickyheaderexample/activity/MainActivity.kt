package com.example.flexibleadapterstickyheaderexample.activity

import android.app.SearchManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.InputType
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
import com.example.flexibleadapterstickyheaderexample.items.SimpleHeaderItem
import com.example.flexibleadapterstickyheaderexample.items.SimpleItem
import com.example.flexibleadapterstickyheaderexample.utils.MenuColorize
import com.example.flexibleadapterstickyheaderexample.utils.ThemeUtils
import com.google.android.material.appbar.AppBarLayout
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(),
        SearchView.OnQueryTextListener,
        FlexibleAdapter.OnItemClickListener {

    private lateinit var adapter: FlexibleAdapter<IFlexible<*>>
    private lateinit var searchView: SearchView

    private var searchViewFirstCreate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initActionBar()
        adapter = FlexibleAdapter(getExampleItems(), this, true)
        adapter
                .setAutoCollapseOnExpand(false)
                .isAutoScrollOnExpand = true
        recycler_view.layoutManager = SmoothScrollLinearLayoutManager(this)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        adapter
                .setDisplayHeadersAtStartUp(true)
                .setStickyHeaders(true)
    }

    override fun onItemClick(view: View?, position: Int): Boolean {
        if (adapter.getItem(position) is SimpleHeaderItem) {
            val viewHolder = recycler_view.findViewHolderForAdapterPosition(position) as SimpleHeaderItem.ViewHolder
            viewHolder.apply {
                val context = itemView.context
                if (adapter.isExpanded(position)) {
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
        return false
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
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = "Choose skills"
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
        val size = 20
        val items = ArrayList<IFlexible<*>>()
        for (i in 0 until size) {
            items.add(newItem(i + 1))
        }
        return items
    }

    private fun newItem(i: Int): SimpleHeaderItem {
        val headers = 5
        val headerItem = SimpleHeaderItem("H$i", "Header $i")
        for (j in 1..headers) {
            val subItem = SimpleItem(headerItem.id + "-SB" + j, "Sub Item $j", j == headers)
            headerItem.addSubItem(subItem)
        }
        return headerItem
    }
}
