package com.mozhimen.app.uicorek.sliderk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivitySliderkBinding

class SliderKActivity : AppCompatActivity() {
    private val vb: ActivitySliderkBinding by lazy { ActivitySliderkBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sliderk)

        initData()
        initView()
    }

    private val _menus = ArrayList<SliderKMenu>()
    private val _itemListCache = mutableMapOf<Int, List<SliderKItem>>()

    private fun initData() {
        _menus.addAll(listOf(SliderKMenu("你的", 0), SliderKMenu("我的", 1), SliderKMenu("他的", 2)))
    }

    fun initView() {
        vb.sliderkSlider.bindMenuView(
            itemCount = _menus.size,
            onBindView = { holder, position ->
                val menu = _menus[position]
                holder.findViewById<TextView>(R.id.sliderk_item_menu_title)?.text = menu.itemName
            }, onItemClick = { holder, position ->
                val menuId = _menus[position].itemId
                if (_itemListCache.containsKey(menuId)) {
                    onQueryItemListSuccess(_itemListCache[menuId]!!)
                } else {
                    //queryItemList(categoryId)
                }
            })
    }

    private fun onQueryItemListSuccess(list: List<SliderKItem>) {

    }

    data class SliderKMenu(
        val itemName: String,
        val itemId: Int
    )

    data class SliderKItem(
        val itemTitle: String,
        val itemUrl: String
    )
}