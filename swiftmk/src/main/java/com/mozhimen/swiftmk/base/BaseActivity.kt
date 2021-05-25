package com.mozhimen.swiftmk.base

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.mozhimen.swiftmk.helper.activity.ActivityCollector
import com.mozhimen.swiftmk.helper.permission.PermissionApplier
import com.mozhimen.swiftmk.helper.statusbar.StatusBarIniter

/**
 * @ClassName BaseActivity
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:03
 * @Version 1.0
 */
abstract class BaseActivity : AppCompatActivity() {

    /**
     * 作用: 打印日志
     */
    val tag = this.javaClass.canonicalName.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getViewBinding().root)

        //回收相关
        ActivityCollector.addActivity(this)
        //状态栏相关,需要注解生效
        StatusBarIniter.initStatusBar(this)
        //权限相关,需要注解
        PermissionApplier.initPermissions(this, getPermissions()) { allGranted, _ ->
            if (allGranted) {
                initData()
                initView()
            }
        }
    }

    //region #抽象函数集合
    /**
     * 作用: 回调ViewBinding
     * 用法: private lateinit var viewBinding: Activity???Binding(申明)
     * override fun getViewBinding(): ViewBinding {
     * viewBinding = Activity???Binding.inflate(layoutInflater)
     * return viewBinding}
     */
    abstract fun getViewBinding(): ViewBinding

    /**
     * 作用: 回调权限数组
     * 用法: override fun getPermissions(): Array<String> = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,...)
     */
    abstract fun getPermissions(): Array<String>

    /**
     * 作用: 初始化数据
     */
    abstract fun initData()

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