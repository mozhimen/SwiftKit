package com.mozhimen.app.uicorek.navk

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityNavkBinding
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.sp2px
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.showToast

class NavKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityNavkBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        vb.navkNavbar3.addLeftImage(UtilKRes.getDrawable(R.mipmap.navk_back)!!, 40f.dp2px(), 2f.dp2px()) {
            setOnClickListener {
                finish()
            }
        }
        vb.navkNavbar3.addLeftBtnKIconFont(
            UtilKRes.getString(R.string.icon_mine),
            40f.dp2px(),
            30f.sp2px(),
            Color.BLACK,
            2f.dp2px(),
            "fonts/iconfont.ttf"
        ) {
            setOnClickListener {
                "你点击了图标".showToast()
            }
        }
    }
}