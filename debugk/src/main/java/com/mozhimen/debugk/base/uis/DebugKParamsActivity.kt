package com.mozhimen.debugk.base.uis

import android.Manifest
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.debugk.BR
import com.mozhimen.debugk.R
import com.mozhimen.debugk.databinding.DebugkActivityParamsBinding
import com.mozhimen.debugk.databinding.DebugkDialogItemBinding
import com.mozhimen.debugk.base.DebugKParams
import com.mozhimen.debugk.base.annors.ADebugKParams
import com.mozhimen.debugk.base.mos.MDebugKMethod
import com.mozhimen.uicorek.recyclerk.RecyclerKVBAdapter

@APermissionK(permissions = [Manifest.permission.READ_PHONE_STATE])
class DebugKParamsActivity : BaseActivityVB<DebugkActivityParamsBinding>() {
    private val _debugParams = arrayOf(DebugKParams::class.java)

    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                super.initData(savedInstanceState)
            } else {
                finish()
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(UtilKRes.getDrawable(R.drawable.debugk_crashk_divider)!!)
        vb.debugkParamsRecycler.addItemDecoration(itemDecoration)

        val params = mutableListOf<MDebugKMethod>()
        val size = _debugParams.size
        for (index in 0 until size) {
            val clazz = _debugParams[index]
            val target = clazz.getConstructor().newInstance()
            val declaredMethods = target.javaClass.declaredMethods
            for (method in declaredMethods) {
                val annotation = method.getAnnotation(ADebugKParams::class.java)

                if (annotation != null) {
                    val name = annotation.title
                    method.isAccessible = true
                    val desc = method.invoke(target) as String

                    val func = MDebugKMethod(name, desc, method, true, target)
                    params.add(func)
                }
            }
        }

        vb.debugkParamsRecycler.layoutManager = LinearLayoutManager(this)
        vb.debugkParamsRecycler.adapter = RecyclerKVBAdapter<MDebugKMethod, DebugkDialogItemBinding>(
            params,
            R.layout.debugk_item_params,
            BR.itemDebugKParams
        )
    }
}