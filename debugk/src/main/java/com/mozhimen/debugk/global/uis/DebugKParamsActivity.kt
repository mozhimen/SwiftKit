package com.mozhimen.debugk.global.uis

import android.Manifest
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.basek.BaseKActivityVBVM
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.componentk.permissionk.PermissionK
import com.mozhimen.componentk.permissionk.annors.PermissionKAnnor
import com.mozhimen.debugk.BR
import com.mozhimen.debugk.R
import com.mozhimen.debugk.databinding.DebugkActivityParamsBinding
import com.mozhimen.debugk.databinding.DebugkDialogItemBinding
import com.mozhimen.debugk.global.DebugKParams
import com.mozhimen.debugk.global.annors.DebugKParamsAnnor
import com.mozhimen.debugk.global.mos.DebugKMethodMo
import com.mozhimen.uicorek.adapterk.AdapterKRecycler

@PermissionKAnnor(permissions = [Manifest.permission.READ_PHONE_STATE])
class DebugKParamsActivity : BaseKActivityVB<DebugkActivityParamsBinding>() {
    private val _debugParams = arrayOf(DebugKParams::class.java)

    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                initView(savedInstanceState)
            } else {
                finish()
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(UtilKRes.getDrawable(R.drawable.debugk_crashk_divider)!!)
        vb.debugkParamsRecycler.addItemDecoration(itemDecoration)

        val params = mutableListOf<DebugKMethodMo>()
        val size = _debugParams.size
        for (index in 0 until size) {
            val clazz = _debugParams[index]
            val target = clazz.getConstructor().newInstance()
            val declaredMethods = target.javaClass.declaredMethods
            for (method in declaredMethods) {
                val annotation = method.getAnnotation(DebugKParamsAnnor::class.java)

                if (annotation != null) {
                    val name = annotation.title
                    method.isAccessible = true
                    val desc = method.invoke(target) as String

                    val func = DebugKMethodMo(name, desc, method, true, target)
                    params.add(func)
                }
            }
        }

        vb.debugkParamsRecycler.layoutManager = LinearLayoutManager(this)
        vb.debugkParamsRecycler.adapter = AdapterKRecycler<DebugKMethodMo, DebugkDialogItemBinding>(
            params,
            R.layout.debugk_item_params,
            BR.itemDebugKParams
        )
    }
}