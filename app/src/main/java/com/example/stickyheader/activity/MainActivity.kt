package com.example.stickyheader.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import com.example.stickyheader.R
import com.example.stickyheader.items.CareerCategoriesHeaderItem
import com.example.stickyheader.items.CareerCategoriesItem
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.Payload
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(),
        FlexibleAdapter.OnItemClickListener {

    private val exampleItems: List<IFlexible<*>>
        get() {
            val items = arrayListOf<IFlexible<*>>()

            val header1: CareerCategoriesHeaderItem = newHeader(1, "开发人员", R.drawable.ic_code_braces_black_24dp)
            header1.apply {
                addSubItem(newItem(header1, 1, "Android开发人员"))
                addSubItem(newItem(header1, 2, "AngularJS开发人员"))
                addSubItem(newItem(header1, 3, "后端开发人员"))
                addSubItem(newItem(header1, 4, "C++开发人员"))
                addSubItem(newItem(header1, 5, "数据分析师"))
                addSubItem(newItem(header1, 6, "查看更多$text", true))
            }

            val header2: CareerCategoriesHeaderItem = newHeader(2, "设计师", R.drawable.ic_bursh_black_24dp)
            header2.apply {
                addSubItem(newItem(header2, 1, "创意总监"))
                addSubItem(newItem(header2, 2, "自由设计师"))
                addSubItem(newItem(header2, 3, "平面设计师"))
                addSubItem(newItem(header2, 4, "插画"))
                addSubItem(newItem(header2, 5, "Logo设计师"))
                addSubItem(newItem(header2, 6, "移动应用程序设计师"))
                addSubItem(newItem(header2, 7, "产品设计师"))
                addSubItem(newItem(header2, 8, "UI设计师"))
                addSubItem(newItem(header2, 9, "UX设计师"))
                addSubItem(newItem(header2, 10, "视觉设计师"))
                addSubItem(newItem(header2, 11, "网页设计师"))
                addSubItem(newItem(header2, 12, "查看更多$text", true))
            }

            val header3: CareerCategoriesHeaderItem = newHeader(3, "金融专家", R.drawable.ic_trending_up_black_24dp)
            header3.apply {
                addSubItem(newItem(header3, 1, "区块链顾问"))
                addSubItem(newItem(header3, 2, "商业模式顾问"))
                addSubItem(newItem(header3, 3, "企业融资顾问"))
                addSubItem(newItem(header3, 4, "能源部门专家"))
                addSubItem(newItem(header3, 5, "股票研究分析师"))
                addSubItem(newItem(header3, 6, "查看更多$text", true))
            }

            val header4: CareerCategoriesHeaderItem = newHeader(4, "项目经理", R.drawable.ic_calendar_check_black_24dp)
            header4.apply {
                addSubItem(newItem(header4, 1, "敏捷项目经理"))
                addSubItem(newItem(header4, 2, "敏捷教练"))
                addSubItem(newItem(header4, 3, "敏捷团队领导"))
                addSubItem(newItem(header4, 4, "区块链项目经理"))
                addSubItem(newItem(header4, 5, "数字项目经理"))
                addSubItem(newItem(header4, 6, "查看更多$text", true))
            }

            val header5: CareerCategoriesHeaderItem = newHeader(5, "产品经理", R.drawable.ic_cube_outline_black_24dp)
            header5.apply {
                addSubItem(newItem(header5, 1, "敏捷产品经理"))
                addSubItem(newItem(header5, 2, "业务分析师"))
                addSubItem(newItem(header5, 3, "合同产品经理"))
                addSubItem(newItem(header5, 4, "数字产品经理"))
                addSubItem(newItem(header5, 5, "企业产品经理"))
                addSubItem(newItem(header5, 6, "查看更多$text", true))
            }

            items.add(header1)
            items.add(header2)
            items.add(header3)
            items.add(header4)
            items.add(header5)
            return items
        }

    private lateinit var adapter: FlexibleAdapter<IFlexible<*>>

    companion object {
        private const val STATE_EXPANDED_POSITIONS = "expanded_positions"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putIntegerArrayList(STATE_EXPANDED_POSITIONS, adapter.expandedPositions as ArrayList<Int>)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = "Choose skills"
        }

        adapter = FlexibleAdapter(exampleItems, this, true)
        adapter.isAutoScrollOnExpand = true
        recycler_view.layoutManager = SmoothScrollLinearLayoutManager(recycler_view.context)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        adapter
                .setDisplayHeadersAtStartUp(true)
                .setStickyHeaders(true)
                .setStickyHeaderElevation(2)
                .isAutoCollapseOnExpand = true

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                val expandedPositions = getIntegerArrayList(STATE_EXPANDED_POSITIONS)
                if (!expandedPositions.isNullOrEmpty()) {
                    for (position in expandedPositions) {
                        adapter.expand(position)
                    }
                }
            }
        }
    }

    override fun onItemClick(view: View?, position: Int): Boolean {
        when (adapter.getItem(position)) {
            is CareerCategoriesHeaderItem -> {
                val item = adapter.getItem(position) as CareerCategoriesHeaderItem?
                if (item != null) {
                    adapter.updateItem(position, item, Payload.EXPANDED)
                }
            }
            is CareerCategoriesItem -> {
                val holder = recycler_view.findViewHolderForAdapterPosition(position) as CareerCategoriesItem.ViewHolder?
                holder?.apply {
                    val item = adapter.getItem(position) as CareerCategoriesItem
                    if (item.isFooter) {
                        val headerItem = adapter.getSectionHeader(position) as CareerCategoriesHeaderItem
                        Toast.makeText(applicationContext, "Item: ${headerItem.text}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "Item: ${item.text}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return false
    }

    private fun newItem(
            headerItem: CareerCategoriesHeaderItem,
            id: Int,
            text: String,
            isFooter: Boolean = false
    ): CareerCategoriesItem {
        return CareerCategoriesItem("${headerItem.id}I$id", text, isFooter)
    }

    private fun newHeader(id: Int, text: String, @DrawableRes icon: Int): CareerCategoriesHeaderItem {
        return CareerCategoriesHeaderItem("H$id", text, icon)
    }
}
