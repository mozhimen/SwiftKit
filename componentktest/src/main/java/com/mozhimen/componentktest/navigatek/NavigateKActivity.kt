package com.mozhimen.componentktest.navigatek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.componentk.navigatek.NavigateK
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ActivityNavigatekBinding

class NavigateKActivity : BaseKActivity<ActivityNavigatekBinding, BaseKViewModel>(R.layout.activity_navigatek) {

    private var _currentItemId: Int = 0

    companion object {
        private const val navigatek_saved_current_id = "navigatek_saved_current_id"
    }

    override fun initData(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            _currentItemId = savedInstanceState.getInt(navigatek_saved_current_id)
        }
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        NavigateK.buildNavGraph(this,R.id.navigatek_fragment_container)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(navigatek_saved_current_id, _currentItemId)
        super.onSaveInstanceState(outState)
    }
}