package com.mozhimen.abilityk.hotfixk.commons

/**
 * @ClassName IHotFixKListener
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/7/22 13:21
 * @Version 1.0
 */
interface IHotFixKListener {
    fun onMoveHotFixFilesFinished(moveFilesNum: Int)
    fun onFixFinished(totalDexNum: Int, fixedDexNum: Int)
}