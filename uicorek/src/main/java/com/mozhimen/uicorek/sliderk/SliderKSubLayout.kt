package com.mozhimen.uicorek.sliderk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseIntArray
import android.util.TypedValue
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
import com.mozhimen.basick.extsk.loadComplex
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.itemk.ItemKViewHolder
import com.mozhimen.uicorek.sliderk.helpers.SliderKSubAttrsParser
import com.mozhimen.uicorek.sliderk.helpers.SliderKSubItemDecorator
import com.mozhimen.uicorek.sliderk.mos.*

typealias SliderKSubItemListener = (holder: ItemKViewHolder, contentMo: SliderKContentMo?) -> Unit

/**
 * @ClassName SliderKSubLayout
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 14:10
 * @Version 1.0
 */
@SuppressLint("ResourceAsColor")
class SliderKSubLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseKLayoutLinear(context, attrs, defStyleAttr) {

    private val _menuView = RecyclerView(context)
    private val _contentView = RecyclerView(context)
    private var _attr: SliderKSubAttrs = SliderKSubAttrsParser.parseMenuAttr(context, attrs)
    private val _subSpanSizeOffset = SparseIntArray()

    private var _menuLayoutId = R.layout.sliderk_menu_item
    private var _contentLayoutId = R.layout.sliderk_content_item
    private var _spanCount = 3
    private var _sliderKSubItemLayoutManager: RecyclerView.LayoutManager? = null
    private var _sliderKSubItemListener: SliderKSubItemListener? = null

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

    /**
     * ??????MenuView
     * @return RecyclerView
     */
    fun getMenuView(): RecyclerView = _menuView

    /**
     * ??????ContentView
     * @return RecyclerView
     */
    fun getContentView(): RecyclerView = _contentView

    fun bindData(
        mo: SliderKDataMo,
        menuLayoutId: Int? = null,
        contentLayoutId: Int? = null,
        spanCount: Int? = null,
        layoutManager: RecyclerView.LayoutManager? = null,
        listener: SliderKSubItemListener? = null
    ) {
        menuLayoutId?.let { _menuLayoutId = it }
        contentLayoutId?.let { _contentLayoutId = it }
        spanCount?.let {
            _spanCount = it
        }
        layoutManager?.let {
            _sliderKSubItemLayoutManager = it
        }
        listener?.let { _sliderKSubItemListener = it }
        bindDataMenuView(mo)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun bindDataContentView(
        mo: SliderKMenuMo
    ) {
        if (_contentView.layoutManager == null) {
            _contentView.layoutManager = _sliderKSubItemLayoutManager ?: GridLayoutManager(context, _spanCount)
            if (_contentView.layoutManager is GridLayoutManager) {
                (_contentView.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val spanSize: Int
                        val subName: String? = getSubNameByPosition(mo, position)
                        val nextSubName: String? = getSubNameByPosition(mo, position + 1)

                        if (TextUtils.equals(subName, nextSubName)) {
                            spanSize = 1
                        } else {
                            //??????????????? ??????????????? ?????????????????????
                            //1 .?????????????????? position ?????????????????? _subSpanSizeOffset ???????????????
                            //2 .?????? ????????????????????? ????????? spanSizeOffset ?????????
                            //3 .????????????????????????item ?????? spanSize count

                            val indexOfKey = _subSpanSizeOffset.indexOfKey(position)
                            val size = _subSpanSizeOffset.size()
                            val lastGroupOffset = if (size <= 0) 0
                            else if (indexOfKey >= 0) {
                                //??????????????????????????????????????????????????? _subSpanSizeOffset ???????????????????????????????????????
                                if (indexOfKey == 0) 0 else _subSpanSizeOffset.valueAt(indexOfKey - 1)
                            } else {
                                //?????????????????????????????????????????????????????? _subSpanSizeOffset ???????????????????????? ????????????????????????
                                //???????????????????????????????????????
                                _subSpanSizeOffset.valueAt(size - 1)
                            }
                            //3-(6+5%3)?????????=0,1,2
                            spanSize = _spanCount - (position + lastGroupOffset) % _spanCount
                            if (indexOfKey < 0) {
                                //??????????????? ?????????????????????spanSize ???????????????
                                val groupOffset = lastGroupOffset + spanSize - 1
                                _subSpanSizeOffset.put(position, groupOffset)
                            }
                        }
                        return spanSize
                    }
                }
            }
            _contentView.adapter = SliderKContentAdapter(mo)
            _contentView.addItemDecoration(
                SliderKSubItemDecorator(_spanCount, _attr.subHeight, _attr.subMarginStart, _attr.subTextSize, _attr.subTextColor) { position ->
                    getSubNameByPosition(mo, position)
                })
        }
        val contentAdapter = _contentView.adapter as SliderKContentAdapter
        contentAdapter.update(mo)
        contentAdapter.notifyDataSetChanged()
        _contentView.scrollToPosition(0)
    }

    private fun bindDataMenuView(
        mo: SliderKDataMo,
    ) {
        _menuView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        _menuView.adapter = SliderKMenuAdapter(mo, _menuLayoutId)
    }

    inner class SliderKMenuAdapter(
        private val _mo: SliderKDataMo,
        private val _layoutId: Int,
    ) : RecyclerView.Adapter<ItemKViewHolder>() {
        private var _currentSelectIndex = 0//???????????????item?????????
        private var _lastSelectIndex = 0//??????????????????item?????????

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemKViewHolder {
            val itemView = LayoutInflater.from(context).inflate(_layoutId, parent, false)
            val layoutParams = RecyclerView.LayoutParams(_attr.menuWidth, _attr.menuHeight)

            itemView.layoutParams = layoutParams
            itemView.setBackgroundColor(_attr.menuItemBgColor)
            itemView.findViewById<TextView>(R.id.sliderk_menu_item_title)?.setTextColor(_attr.menuItemTextColor)
            itemView.findViewById<ImageView>(R.id.sliderk_menu_item_indicator)?.setImageDrawable(_attr.menuItemIndicator)
            return ItemKViewHolder(itemView)
        }

        override fun getItemCount(): Int = _mo.menus.size

        override fun onBindViewHolder(holder: ItemKViewHolder, @SuppressLint("RecyclerView") position: Int) {
            holder.itemView.setOnClickListener {
                _currentSelectIndex = position
                notifyItemChanged(position)
                notifyItemChanged(_lastSelectIndex)
            }

            //applyItemAttr
            if (_currentSelectIndex == position) {
                bindDataContentView(_mo.menus[_currentSelectIndex])
                _lastSelectIndex = _currentSelectIndex
            }
            applyMenuItemAttr(position, holder)
            bindMenuView(holder, position)
        }

        private fun bindMenuView(holder: ItemKViewHolder, position: Int) {
            if (position in _mo.menus.indices)
                holder.findViewById<TextView>(R.id.sliderk_menu_item_title)?.text = _mo.menus[position].menuName
        }

        private fun applyMenuItemAttr(position: Int, holder: ItemKViewHolder) {
            val selected = position == _currentSelectIndex
            val titleView: TextView? = holder.itemView.findViewById(R.id.sliderk_menu_item_title)
            val indicatorView: ImageView? = holder.itemView.findViewById(R.id.sliderk_menu_item_indicator)

            indicatorView?.visibility = if (selected) View.VISIBLE else View.GONE
            titleView?.apply {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, (if (selected) _attr.menuItemTextSizeSelect else _attr.menuItemTextSize).toFloat())
                if (selected) fontStyle(Typeface.BOLD) else fontStyle()
            }
            holder.itemView.setBackgroundColor(if (selected) _attr.menuItemBgColorSelect else _attr.menuItemBgColor)
            titleView?.isSelected = selected
        }
    }

    inner class SliderKContentAdapter(
        private var _mo: SliderKMenuMo
    ) : RecyclerView.Adapter<ItemKViewHolder>() {

        fun update(mo: SliderKMenuMo) {
            this._mo = mo
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemKViewHolder {
            val itemView = LayoutInflater.from(context).inflate(_contentLayoutId, parent, false)
            itemView.findViewById<TextView>(R.id.sliderk_content_item_txt)?.apply {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, _attr.contentTextSize.toFloat())
                setTextColor(_attr.contentTextColor)
            }
            return ItemKViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            var count = 0
            _mo.menuSubs.forEach {
                count += it.subContents.size
            }
            return count
        }

        override fun onBindViewHolder(holder: ItemKViewHolder, position: Int) {
            bindContentView(holder, position)
            holder.itemView.setOnClickListener {
                _sliderKSubItemListener?.invoke(holder, getContentByPosition(_mo, position))
            }
        }

        private fun bindContentView(holder: ItemKViewHolder, position: Int) {
            val contentMo = getContentByPosition(_mo, position)
            holder.findViewById<TextView>(R.id.sliderk_content_item_txt)?.text = contentMo?.contentName ?: "????????????"
            holder.findViewById<ImageView>(R.id.sliderk_content_item_img)
                ?.loadComplex(contentMo?.contentImageUrl ?: "", placeholder = R.mipmap.refreshk_loading, error = R.mipmap.layoutk_empty)
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
                //??????content itemView, ????????????layoutParams ?????????,??????????????????????????????, ???????????????,?????????????????????
                val layoutParams = holder.itemView.layoutParams
                layoutParams.width = itemWidth
                layoutParams.height = (itemWidth * _attr.contentImgRatio).toInt()
                holder.itemView.layoutParams = layoutParams
            }
        }
    }

    private fun getSubNameByPosition(mo: SliderKMenuMo, position: Int): String? {
        val sliderKContentMo = getContentByPosition(mo, position)
        return sliderKContentMo?.let { contentMo ->
            mo.menuSubs.find { it.subId == contentMo.fatherId }?.subName
        }
    }

    private fun getContentByPosition(mo: SliderKMenuMo, position: Int): SliderKContentMo? {
        val lengthList = arrayListOf<Int>()//3,4,6
        mo.menuSubs.forEach {
            lengthList.add(it.subContents.size)
        }
        if (lengthList.isNotEmpty()) {
            var totalSize = lengthList[0]
            lengthList.forEachIndexed { index, item ->
                if (index != 0) totalSize += item
                if (position in 0 until totalSize) {
                    var preSize = 0
                    if (index > 0)
                        for (i in 0 until index) preSize += lengthList[i]
                    return mo.menuSubs[index].subContents[position - preSize]
                }
            }
        }
        return null
    }
}


