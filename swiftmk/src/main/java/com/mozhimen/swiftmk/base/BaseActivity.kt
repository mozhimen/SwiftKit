package com.mozhimen.swiftmk.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.mozhimen.swiftmk.helper.activity.ActivityCollector
import com.mozhimen.swiftmk.helper.permission.PermissionApplier
import com.mozhimen.swiftmk.helper.statusbar.StatusBarIniter
import com.mozhimen.swiftmk.helper.toast.showToast
import java.lang.StringBuilder

/**
 * @ClassName BaseActivity
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:03
 * @Version 1.0
 */
abstract class BaseActivity() : AppCompatActivity() {

    /**
     * 作用: 打印日志
     */
    val TAG = this.javaClass.canonicalName.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFlag()
        setContentView(getViewBinding().root)

        //回收相关
        ActivityCollector.addActivity(this)
        //状态栏相关,需要注解生效
        StatusBarIniter.initStatusBar(this)
        //权限相关,需要注解
        PermissionApplier.initPermissions(this, getPermissions()) { allGranted, deniedList ->
            if (allGranted) {
                initData(savedInstanceState)
                initView()
            } else {
                val deniedStr = StringBuilder()
                deniedList.forEach {
                    deniedStr.append(it).append(",")
                }
                deniedStr.removeRange(deniedStr.length - 1, deniedStr.length).trim()
                "请在设置中打开${deniedStr}权限".showToast(this)

                //打开设置
                applySetts()
            }
        }
    }

    /**
     * 设置申请权限
     */
    private fun applySetts() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", packageName, null)
        startActivity(intent)
    }

    //region #抽象函数集合
    /**
     * 作用: 回调ViewBinding
     * 用法: private lateinit var viewBinding: Activity???Binding(申明)
     * override fun getViewBinding(): ViewBinding {
     *   viewBinding = Activity???Binding.inflate(layoutInflater)
     *   return viewBinding}
     */
    abstract fun getViewBinding(): ViewBinding

    /**
     * 作用: 回调权限数组
     * 用法: override fun getPermissions(): Array<String> = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,...)
     */
    open fun getPermissions(): Array<String> = emptyArray()

    /**
     * 作用: 特殊标志声明
     */
    open fun initFlag() {}

    /**
     * 作用: 初始化数据
     */
    open fun initData(savedInstanceState: Bundle?) {}

    /**
     * 作用: 初始化界面
     */
    abstract fun initView()
    //endregion

    /**
     * 作用: 销毁Activity
     */
    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}