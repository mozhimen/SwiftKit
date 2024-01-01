//package com.mozhimen.debugk.bases
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import androidx.navigation.NavController
//import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVBVM
//import com.mozhimen.uicorek.adaptk.systembar.initAdaptKSystemBar
//import com.mozhimen.basick.utilk.android.util.et
//import com.mozhimen.componentk.navigatek.NavigateK
//import com.mozhimen.debugk.R
//import com.mozhimen.debugk.databinding.DebugkActivityNavigatekBinding
//
//
///**
// * @ClassName DebugKNavigateActivity
// * @Description TODO
// * @Author Mozhimen & Kolin Zhao
// * @Date 2022/11/22 10:55
// * @Version 1.0
// */
//abstract class BaseDebugKNavigateKActivity : BaseActivityVBVM<DebugkActivityNavigatekBinding, BaseDebugKNavigateKViewModel>() {
//    private val _fragments by lazy { getFragments() }
//
//    private lateinit var _navController: NavController
//    private var _currentItemId: Int = 0
//        set(value) {
//            if (value == -1 || !this::_navController.isInitialized) return
//            _navController.navigate(resId = value)
//            field = value
//        }
//
//    abstract fun getFragments(): List<Class<*>>
//
//    override fun initFlag() {
//        initAdaptKSystemBar()
//    }
//
//    @SuppressLint("RestrictedApi")
//    override fun initView(savedInstanceState: Bundle?) {
//        _navController = NavigateK.buildNavGraph(this, R.id.debugk_navigatek_fragment_container, _fragments, _currentItemId)
//        vm.liveFragmentId.observe(this) {
//            if (it != null && _navController.findDestination(it) != null && _navController.currentDestination?.id != it) {
//                _currentItemId = it
//            } else {
//                "please add this destination to list ".et(TAG)
//            }
//        }
//    }
//
//    override fun bindViewVM(vb: DebugkActivityNavigatekBinding) {
//    }
//}