package com.mozhimen.uicorek.recyclerk.load.commons


/**
 * @ClassName IRecyclerKLoad
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/4/6 10:19
 * @Version 1.0
 */
interface IRecyclerKLoad {
    val TAG get() = "${this.javaClass.simpleName}>>>>>"

    /**
     * 设置底部加载视图
     * @param layoutId Int
     */
    fun setFooterView(layoutId: Int)

    /**
     * 打开加载更多
     * @param prefetchSize Int 加载个数
     * @param listener Function0<Unit>
     */
    fun enableLoad(prefetchSize: Int, listener: IRecyclerKLoadListener?)

    /**
     * 关闭加载更多
     */
    fun disableLoad()

    /**
     * 是否正在加载
     * @return Boolean
     */
    fun isLoading(): Boolean

    /**
     * 加载结束
     */
    fun finishLoad()
}