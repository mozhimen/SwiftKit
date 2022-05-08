package com.mozhimen.uicorek.sliderk

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mozhimen.basicsk.basek.BaseKLayoutLinear
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.itemk.ItemKViewHolder

/**
 * @ClassName SliderKLayout
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 14:10
 * @Version 1.0
 */
@SuppressLint("ResourceAsColor")
class SliderKLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseKLayoutLinear(context, attrs, defStyleAttr) {

    private val menuView = RecyclerView(context)
    private val contentView = RecyclerView(context)

    private var menuItemAttr = SliderKParser.parseMenuAttr(context, attrs)

    init {
        initView()
    }

    override fun initView() {
        super.initView()
        orientation = HORIZONTAL

        menuView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        menuView.overScrollMode = View.OVER_SCROLL_NEVER
        menuView.itemAnimator = null

        contentView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        contentView.overScrollMode = View.OVER_SCROLL_NEVER
        contentView.itemAnimator = null

        addView(menuView)
        addView(contentView)
    }

    fun bindMenuView(
        layoutRes: Int = R.layout.sliderk_menu_item,
        itemCount: Int,
        onBindView: (ItemKViewHolder, Int) -> Unit,
        onItemClick: (ItemKViewHolder, Int) -> Unit
    ) {
        menuView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        menuView.adapter = SliderKMenuAdapter(layoutRes, itemCount, onBindView, onItemClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun bindContentView(
        layoutRes: Int = R.layout.sliderk_content,
        itemCount: Int,
        itemDecoration: RecyclerView.ItemDecoration?,
        layoutManager: RecyclerView.LayoutManager,
        onBindView: (ItemKViewHolder, Int) -> Unit,
        onItemClick: (ItemKViewHolder, Int) -> Unit
    ) {
        if (contentView.layoutManager == null) {
            contentView.layoutManager = layoutManager
            contentView.adapter = SliderKContentAdapter(layoutRes)
            itemDecoration?.let {
                contentView.addItemDecoration(it)
            }
        }
        val contentAdapter = contentView.adapter as SliderKContentAdapter
        contentAdapter.update(itemCount, onBindView, onItemClick)
        contentAdapter.notifyDataSetChanged()

        contentView.scrollToPosition(0)
    }

    inner class SliderKMenuAdapter(
        private val layoutRes: Int,
        private val count: Int,
        val onBindView: (ItemKViewHolder, Int) -> Unit,
        val onItemClick: (ItemKViewHolder, Int) -> Unit
    ) : RecyclerView.Adapter<ItemKViewHolder>() {
        //本次选中的item的位置
        private var currentSelectIndex = 0

        //上一次选中的item的位置
        private var lastSelectIndex = 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemKViewHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutRes, parent, false)
            val params = RecyclerView.LayoutParams(menuItemAttr.width, menuItemAttr.height)

            itemView.layoutParams = params
            itemView.setBackgroundColor(menuItemAttr.normalBackgroundColor)
            itemView.findViewById<TextView>(R.id.sliderk_menu_item_title)?.setTextColor(menuItemAttr.textColor)
            itemView.findViewById<ImageView>(R.id.sliderk_menu_item_indicator)?.setImageDrawable(menuItemAttr.indicator)
            return ItemKViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return count
        }

        override fun onBindViewHolder(holder: ItemKViewHolder, @SuppressLint("RecyclerView") position: Int) {
            holder.itemView.setOnClickListener {
                currentSelectIndex = position
                notifyItemChanged(position)
                notifyItemChanged(lastSelectIndex)
            }

            //applyItemAttr
            if (currentSelectIndex == position) {
                onItemClick(holder, position)
                lastSelectIndex = currentSelectIndex
            }
            applyItemAttr(position, holder)
            onBindView(holder, position)
        }

        private fun applyItemAttr(position: Int, holder: ItemKViewHolder) {
            val selected = position == currentSelectIndex
            val titleView: TextView? = holder.itemView.findViewById(R.id.sliderk_menu_item_title)
            val indicatorView: ImageView? = holder.itemView.findViewById(R.id.sliderk_menu_item_indicator)

            indicatorView?.visibility = if (selected) View.VISIBLE else View.GONE
            titleView?.textSize = (if (selected) menuItemAttr.selectTextSize else menuItemAttr.textSize).toFloat()
            holder.itemView.setBackgroundColor(
                if (selected) menuItemAttr.selectBackgroundColor else menuItemAttr.normalBackgroundColor
            )
            titleView?.isSelected = selected
        }

        override fun onViewAttachedToWindow(holder: ItemKViewHolder) {
            super.onViewAttachedToWindow(holder)
            val remainSpace: Int = width - paddingLeft - paddingRight - menuItemAttr.width
            val layoutManager: RecyclerView.LayoutManager? = contentView.layoutManager
            var spanCount = 0
            if (layoutManager is GridLayoutManager) {
                spanCount = layoutManager.spanCount
            } else if (layoutManager is StaggeredGridLayoutManager) {
                spanCount = layoutManager.spanCount
            }
            if (spanCount > 0) {
                val itemWidth: Int = remainSpace / spanCount
                //创建content itemView,设置它的layoutParams的原因,是防止图片未加载出来之前,列表滑动时上下闪动的效果
                val layoutParams: ViewGroup.LayoutParams = holder.itemView.layoutParams
                layoutParams.width = itemWidth
                layoutParams.height = itemWidth
                holder.itemView.layoutParams = layoutParams
            }
        }
    }

    inner class SliderKContentAdapter(
        private val layoutRes: Int
    ) : RecyclerView.Adapter<ItemKViewHolder>() {
        private var count = 0
        private lateinit var onItemClick: (ItemKViewHolder, Int) -> Unit
        private lateinit var onBindView: (ItemKViewHolder, Int) -> Unit

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemKViewHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutRes, parent, false)
            return ItemKViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return count
        }

        override fun onBindViewHolder(holder: ItemKViewHolder, position: Int) {
            onBindView(holder, position)
            holder.itemView.setOnClickListener {
                onItemClick(holder, position)
            }
        }

        override fun onViewAttachedToWindow(holder: ItemKViewHolder) {
            super.onViewAttachedToWindow(holder)
            val remainScope = width - paddingLeft - paddingRight - menuItemAttr.width
            val layoutManager = contentView.layoutManager
            var spanCount = 0
            if (layoutManager is GridLayoutManager) {
                spanCount = layoutManager.spanCount
            } else if (layoutManager is StaggeredGridLayoutManager) {
                spanCount = layoutManager.spanCount
            }

            if (spanCount > 0) {
                val itemWidth = remainScope / spanCount
                //创建content itemView, 设置它的layoutParams 的原因,是防止图片未加载之前, 列表滑动时,上下闪动的效果
                val layoutParams = holder.itemView.layoutParams
                layoutParams.width = itemWidth
                layoutParams.height = itemWidth
                holder.itemView.layoutParams = layoutParams
            }
        }

        fun update(
            itemCount: Int,
            onBindView: (ItemKViewHolder, Int) -> Unit,
            onItemClick: (ItemKViewHolder, Int) -> Unit
        ) {
            this.count = itemCount
            this.onBindView = onBindView
            this.onItemClick = onItemClick
        }
    }

}


