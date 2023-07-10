package com.mozhimen.uicorek.recyclerk

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.lintk.optin.annors.AOptInInitByLazy
import com.mozhimen.basick.elemk.androidx.lifecycle.commons.IDefaultLifecycleObserver
import com.mozhimen.basick.lintk.optin.annors.AOptInNeedCallBindLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * @ClassName RecyclerKLifecycle
 * @Description 这边RecyclerView持有Adapter会造成内存泄漏, 特别是在fragment中, 所以在onPause要置空
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/23 11:42
 * @Version 1.0
 */
@OptIn(AOptInInitByLazy::class)
@AOptInNeedCallBindLifecycle
class RecyclerKLifecycle @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr), IDefaultLifecycleObserver {

    override fun bindLifecycle(owner: LifecycleOwner) {
        owner.lifecycleScope.launch(Dispatchers.Main) {
            owner.lifecycle.removeObserver(this@RecyclerKLifecycle)
            owner.lifecycle.addObserver(this@RecyclerKLifecycle)
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        this.adapter = null
        owner.lifecycle.removeObserver(this@RecyclerKLifecycle)
    }
}