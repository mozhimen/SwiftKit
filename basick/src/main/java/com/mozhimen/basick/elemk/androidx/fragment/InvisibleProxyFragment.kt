package com.mozhimen.basick.elemk.androidx.fragment

import android.content.Intent
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.mozhimen.basick.elemk.android.app.cons.CActivity
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.utilk.commons.IUtilK

class InvisibleProxyFragment : Fragment(), IUtilK {
    companion object {
        const val REQUEST_CODE_PROXY = 1001
        const val TAG_PROXY = "InvisibleProxyFragment>>>>>"

        @JvmStatic
        fun startProxyFragment(activity: FragmentActivity, onAction: IA_Listener<Fragment>, onResult: IA_Listener<Boolean>) {
            val fragmentManager = activity.supportFragmentManager
            val existedFragment = fragmentManager.findFragmentByTag(TAG_PROXY)
            val fragment: InvisibleProxyFragment = if (existedFragment != null) {
                existedFragment as InvisibleProxyFragment
            } else {
                val invisibleProxyFragment = InvisibleProxyFragment()
                /**
                 * commitNowAllowingStateLoss(): 这个方法类似于 commitNow()，同样是同步执行的，将事务立即提交并立即执行。与 commitNow() 不同的是，commitNowAllowingStateLoss() 允许在 Activity 状态丢失的情况下提交事务。使用 commitNowAllowingStateLoss() 可以避免在恢复过程中抛出 IllegalStateException 异常。
                 */
                fragmentManager.beginTransaction().add(invisibleProxyFragment, TAG_PROXY).commitNowAllowingStateLoss()
                invisibleProxyFragment
            }
            fragment.setProxyAction(onAction, onResult)
        }
    }

    ///////////////////////////////////////////////////////////////////////

    private var _listener: IA_Listener<Boolean>? = null

    ///////////////////////////////////////////////////////////////////////

    fun setProxyAction(onAction: IA_Listener<Fragment>, onResult: IA_Listener<Boolean>) {
        _listener = onResult
        onAction.invoke(this)
    }

    ///////////////////////////////////////////////////////////////////////

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UtilKLogWrapper.d(TAG, "onActivityResult: requestCode $requestCode, resultCode $resultCode")
        if (resultCode == CActivity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PROXY -> {
                    _listener?.invoke(true)
                    return
                }
            }
        }
        _listener?.invoke(false)
    }
}