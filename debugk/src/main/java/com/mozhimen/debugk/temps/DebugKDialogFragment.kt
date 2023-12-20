package com.mozhimen.debugk.temps

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.android.view.cons.CWinMgr
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import com.mozhimen.basick.utilk.android.view.UtilKContentView
import com.mozhimen.debugk.BR
import com.mozhimen.debugk.R
import com.mozhimen.debugk.databinding.DebugkDialogItemBinding
import com.mozhimen.debugk.cons.DebugKTools
import com.mozhimen.debugk.annors.ADebugKTool
import com.mozhimen.debugk.mos.MDebugKMethod
import com.mozhimen.uicorek.adapterk.quick.AdapterKQuickRecyclerVB

/**
 * @ClassName DebugKDialogFragment
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/26 17:37
 * @Version 1.0
 */
@OptIn(OptInApiInit_InApplication::class)
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
class DebugKDialogFragment : AppCompatDialogFragment() {
    private val _debugMethods = arrayOf(DebugKTools::class.java)

    private lateinit var _debugKRecycler: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parent = dialog?.window?.let { UtilKContentView.getAsViewGroup(it) } ?: container
        val view = inflater.inflate(R.layout.debugk_dialog, parent, false)

        dialog?.window?.setLayout((UtilKScreen.getWidthOfWindow() * 0.8f).toInt(), CWinMgr.Lp.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.debugk_crashk_dialog)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _debugKRecycler = view.findViewById(R.id.debugk_dialog_recycler)
        val itemDecoration = DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(UtilKRes.getDrawable(R.drawable.debugk_crashk_divider)!!)
        _debugKRecycler.addItemDecoration(itemDecoration)

        val methods = mutableListOf<MDebugKMethod>()
        val size = _debugMethods.size
        for (index in 0 until size) {
            val clazz = _debugMethods[index]
            val target = clazz.getConstructor().newInstance()
            val declaredMethods = target.javaClass.declaredMethods
            for (method in declaredMethods) {
                var name: String
                var desc = ""
                var enable = false
                val annotation = method.getAnnotation(ADebugKTool::class.java)

                if (annotation != null) {
                    name = annotation.title
                    desc = annotation.desc
                    enable = true
                } else {
                    method.isAccessible = true
                    name = method.invoke(target) as String
                }

                val func = MDebugKMethod(name, desc, method, enable, target)
                methods.add(func)
            }
        }

        _debugKRecycler.layoutManager = LinearLayoutManager(requireActivity())
        _debugKRecycler.adapter = AdapterKQuickRecyclerVB<MDebugKMethod, DebugkDialogItemBinding>(
            methods,
            R.layout.debugk_dialog_item,
            BR.itemDebugKDialog
        ) { holder, itemData, _, _ ->

            val descView = holder.vb.debugkDialogItemDesc
            if (TextUtils.isEmpty(itemData.desc)) {
                descView.visibility = View.GONE
            } else {
                descView.visibility = View.VISIBLE
                descView.text = itemData.desc
            }

            if (itemData.enable) {
                holder.itemView.setOnClickListener {
                    itemData.invoke(requireActivity())
                    dismiss()
                }
            }
        }
    }
}