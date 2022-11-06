package com.mozhimen.uicorektest.recyclerk

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.start
import com.mozhimen.uicorektest.databinding.ActivityRecyclerkBinding

/**
 * @ClassName RecyclerKActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/7 1:17
 * @Version 1.0
 */
class RecyclerKActivity : BaseKActivityVB<ActivityRecyclerkBinding>() {
    override fun initData(savedInstanceState: Bundle?) {

    }

    fun goRecyclerKAdapter(view: View) {
        start<RecyclerKAdapterActivity>()
    }

    fun goRecyclerKLoad(view: View) {
        start<RecyclerKLoadActivity>()
    }
}