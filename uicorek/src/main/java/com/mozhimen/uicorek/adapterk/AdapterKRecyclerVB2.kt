package com.mozhimen.uicorek.adapterk

import androidx.databinding.ViewDataBinding
import com.mozhimen.uicorek.recyclerk.IRecyclerKVBAdapterListener


/**
 * @ClassName AdapterKRecyclerVB2
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/31 17:35
 * @Version 1.0
 */
class AdapterKRecyclerVB2<BEAN, VB : ViewDataBinding>(
    private var _itemDatas: List<BEAN>,
    private val _defaultLayout: Int,
    private val _brId: Int,
    private val _listener: IRecyclerKVBAdapterListener<BEAN, VB>? = null
) : AdapterKRecyclerStuffed() {

}