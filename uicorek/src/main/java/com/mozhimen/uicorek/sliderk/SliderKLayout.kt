package com.mozhimen.uicorek.sliderk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
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
import com.mozhimen.basick.basek.BaseKLayoutLinear
import com.mozhimen.basick.extsk.fontStyle
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.itemk.ItemKViewHolder
import com.mozhimen.uicorek.sliderk.mos.SliderKAttrs

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

    private val _menuView = RecyclerView(context)
    private val _contentView = RecyclerView(context)
    private var _attr: SliderKAttrs = SliderKAttrsParser.parseMenuAttr(context, attrs)

    init {
        initView()
    }

    override fun initView() {
        orientation = HORIZONTAL

        _menuView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        _menuView.overScrollMode = View.OVER_SCROLL_NEVER
        _menuView.itemAnimator = null

        _contentView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        _contentView.overScrollMode = View.OVER_SCROLL_NEVER
        _contentView.itemAnimator = null

        addView(_menuView)
        addView(_contentView)
    }

    fun bindMenuView(
        itemCount: Int,
        onBindView: (holder: ItemKViewHolder, position: Int) -> Unit,
        onItemClick: (holder: ItemKViewHolder, position: Int) -> Unit,
        layoutId: Int = R.layout.sliderk_item_menu
    ) {
        _menuView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        _menuView.adapter = SliderKMenuAdapter(layoutId, itemCount, onBindView, onItemClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun bindContentView(
        itemCount: Int,
        itemDecoration: RecyclerView.ItemDecoration?,
        layoutManager: RecyclerView.LayoutManager,
        onBindView: (ItemKViewHolder, Int) -> Unit,
        onItemClick: (ItemKViewHolder, Int) -> Unit,
        layoutRes: Int = R.layout.sliderk_item_content
    ) {
        if (_contentView.layoutManager == null) {
            _contentView.layoutManager = layoutManager
            _contentView.adapter = SliderKContentAdapter(layoutRes)
            itemDecoration?.let {
                _contentView.addItemDecoration(it)
            }
        }
        val contentAdapter = _contentView.adapter as SliderKContentAdapter
        contentAdapter.update(itemCount, onBindView, onItemClick)
        contentAdapter.notifyDataSetChanged()
        _contentView.scrollToPosition(0)
    }

    /**
     * 获取MenuView
     * @return RecyclerView
     */
    fun getMenuView(): RecyclerView = _menuView

    /**
     * 获取ContentView
     * @return RecyclerView
     */
    fun getContentView(): RecyclerView = _contentView

    inner class SliderKMenuAdapter(
        private val _layoutId: Int,
        private val _count: Int,
        val onBindView: (ItemKViewHolder, Int) -> Unit,
        val onItemClick: (ItemKViewHolder, Int) -> Unit
    ) : RecyclerView.Adapter<ItemKViewHolder>() {
        private var _currentSelectIndex = 0//本次选中的item的位置
        private var _lastSelectIndex = 0//上一次选中的item的位置

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemKViewHolder {
            val itemView = LayoutInflater.from(context).inflate(_layoutId, parent, false)
            val params = RecyclerView.LayoutParams(_attr.menuWidth, _attr.menuHeight)

            itemView.layoutParams = params
            itemView.setBackgroundColor(_attr.menuItemBgColor)
            itemView.findViewById<TextView>(R.id.sliderk_item_menu_title)?.setTextColor(_attr.menuItemTextColor)
            itemView.findViewById<ImageView>(R.id.sliderk_item_menu_indicator)?.setImageDrawable(_attr.menuItemIndicator)
            return ItemKViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return _count
        }

        override fun onBindViewHolder(holder: ItemKViewHolder, @SuppressLint("RecyclerView") position: Int) {
            holder.itemView.setOnClickListener {
                _currentSelectIndex = position
                notifyItemChanged(position)
                notifyItemChanged(_lastSelectIndex)
            }

            //applyItemAttr
            if (_currentSelectIndex == position) {
                onItemClick(holder, position)
                _lastSelectIndex = _currentSelectIndex
            }
            applyItemAttr(position, holder)
            onBindView(holder, position)
        }

        private fun applyItemAttr(position: Int, holder: ItemKViewHolder) {
            val selected = position == _currentSelectIndex
            val titleView: TextView? = holder.itemView.findViewById(R.id.sliderk_item_menu_title)
            val indicatorView: ImageView? = holder.itemView.findViewById(R.id.sliderk_item_menu_indicator)

            indicatorView?.visibility = if (selected) View.VISIBLE else View.GONE
            titleView?.apply {
                textSize = (if (selected) _attr.menuItemTextSize else _attr.menuItemTextSize).toFloat()
                if (selected) fontStyle(Typeface.BOLD) else fontStyle()
            }
            holder.itemView.setBackgroundColor(
                if (selected) _attr.menuItemBgColorSelect else _attr.menuItemBgColor
            )
            titleView?.isSelected = selected
        }

        override fun onViewAttachedToWindow(holder: ItemKViewHolder) {
            super.onViewAttachedToWindow(holder)
            val remainSpace: Int = width - paddingLeft - paddingRight - _attr.menuWidth
            val layoutManager: RecyclerView.LayoutManager? = _contentView.layoutManager
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
            val remainScope = width - paddingLeft - paddingRight - _attr.menuWidth
            val layoutManager = _contentView.layoutManager
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


