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

    fun getMenusView(): RecyclerView = _menuView

    fun getContentsView(): RecyclerView = _contentView

    private var _menuItemAttr: SliderKParser.SliderKMenuItemAttrs = SliderKParser.parseMenuAttr(context, attrs)

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
        onBindView: (ItemKViewHolder, Int) -> Unit,
        onItemClick: (ItemKViewHolder, Int) -> Unit,
        layoutRes: Int = R.layout.sliderk_item_menu
    ) {
        _menuView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        _menuView.adapter = SliderKMenuAdapter(layoutRes, itemCount, onBindView, onItemClick)
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
            val params = RecyclerView.LayoutParams(_menuItemAttr.width, _menuItemAttr.height)

            itemView.layoutParams = params
            itemView.setBackgroundColor(_menuItemAttr.bgColor)
            itemView.findViewById<TextView>(R.id.sliderk_item_menu_title)?.setTextColor(_menuItemAttr.textColor)
            itemView.findViewById<ImageView>(R.id.sliderk_item_menu_indicator)?.setImageDrawable(_menuItemAttr.indicator)
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
            val titleView: TextView? = holder.itemView.findViewById(R.id.sliderk_item_menu_title)
            val indicatorView: ImageView? = holder.itemView.findViewById(R.id.sliderk_item_menu_indicator)

            indicatorView?.visibility = if (selected) View.VISIBLE else View.GONE
            titleView?.apply {
                textSize = (if (selected) _menuItemAttr.textSize else _menuItemAttr.textSize).toFloat()
                if (selected) fontStyle(Typeface.BOLD) else fontStyle()
            }
            holder.itemView.setBackgroundColor(
                if (selected) _menuItemAttr.bgColorSelect else _menuItemAttr.bgColor
            )
            titleView?.isSelected = selected
        }

        override fun onViewAttachedToWindow(holder: ItemKViewHolder) {
            super.onViewAttachedToWindow(holder)
            val remainSpace: Int = width - paddingLeft - paddingRight - _menuItemAttr.width
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
            val remainScope = width - paddingLeft - paddingRight - _menuItemAttr.width
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


