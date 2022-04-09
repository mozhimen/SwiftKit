package com.mozhimen.app.uicorek.tabk

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityTabkTopLayoutBinding
import com.mozhimen.uicorek.tabk.top.mos.TabKTopInfo

/**
 * @ClassName TabTopLayoutActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/4 17:17
 * @Version 1.0
 */
class TabKTopLayoutActivity : AppCompatActivity() {
    private val vb by lazy { ActivityTabkTopLayoutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initTabTop()
    }

    private val tabStr = arrayListOf(
        "测试",
        "数码",
        "鞋子",
        "零食",
        "家电",
        "汽车",
        "百货",
        "家居",
        "热门",
        "装修",
        "运动"
    )

    private fun initTabTop() {
        val infoList = ArrayList<TabKTopInfo<*>>()
        val defaultColor = ContextCompat.getColor(this, R.color.tabKTopDefaultColor)
        val tintColor = ContextCompat.getColor(this, R.color.tabKTopTintColor)
        tabStr.forEach {
            val info = TabKTopInfo(it, defaultColor, tintColor)
            infoList.add(info)
        }
        vb.tabTopLayout.inflateInfo(infoList)
        vb.tabTopLayout.addTabSelectedChangeListener { _, _, nextInfo ->
            Toast.makeText(this, nextInfo.name, Toast.LENGTH_SHORT).show()
        }
        vb.tabTopLayout.defaultSelected(infoList[0])
    }
}
