package com.mozhimen.debugk.temps

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.debugk.BR
import com.mozhimen.debugk.R
import com.mozhimen.debugk.databinding.DebugkActivityParamsBinding
import com.mozhimen.debugk.databinding.DebugkDialogItemBinding
import com.mozhimen.debugk.cons.DebugKParams
import com.mozhimen.debugk.annors.ADebugKParams
import com.mozhimen.debugk.mos.MDebugKMethod
import com.mozhimen.uicorek.adapterk.quick.AdapterKQuickRecyclerVB

@AManifestKRequire(
    CPermission.READ_PHONE_STATE, CPermission.READ_PRIVILEGED_PHONE_STATE, CPermission.CAMERA,
    CPermission.ACCESS_NETWORK_STATE, CPermission.ACCESS_WIFI_STATE, CPermission.INTERNET
)
@APermissionCheck(
    CPermission.READ_PHONE_STATE, CPermission.CAMERA,
    CPermission.ACCESS_NETWORK_STATE, CPermission.ACCESS_WIFI_STATE, CPermission.INTERNET
)
class DebugKParamsActivity : BaseActivityVB<DebugkActivityParamsBinding>() {
    private val _debugParams = arrayOf(DebugKParams::class.java)

    override fun initData(savedInstanceState: Bundle?) {
        ManifestKPermission.requestPermissions(this) {
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
        vb.debugkParamsRecycler.adapter = AdapterKQuickRecyclerVB<MDebugKMethod, DebugkDialogItemBinding>(
            params,
            R.layout.debugk_item_params,
            BR.itemDebugKParams
        )
    }
}