package com.mozhimen.uicorek.adapterk.commons

import com.mozhimen.uicorek.vhk.VHKRecyclerMultiVB
import com.mozhimen.uicorek.vhk.VHKRecyclerVB

/**
 * @ClassName IAdapterKRecyclerVBListener
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/31 13:29
 * @Version 1.0
 */
typealias IAdapterKRecyclerVBListener<DATA, VB> = (holder: VHKRecyclerVB<VB>, data: DATA, position: Int, currentSelectPosition: Int) -> Unit
typealias IAdapterKRecyclerMultiVBListener<DATA, VB> = (holder: VHKRecyclerMultiVB<VB>, data: DATA, position: Int, currentSelectPosition: Int) -> Unit
