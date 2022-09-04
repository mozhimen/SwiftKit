package com.mozhimen.app.uicorek.viewk

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityViewkViewsBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewKViewsActivity : BaseKActivity<ActivityViewkViewsBinding,BaseKViewModel>(R.layout.activity_viewk_views) {
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        Log.d(TAG, "initView: viewkColorPicker set ${vb.viewkColorPicker.areColorsSet()}")
        vb.viewkColorPicker.colorPair = Color.BLUE to Color.RED
        Log.d(TAG, "initView: viewkColorPicker set ${vb.viewkColorPicker.areColorsSet()}")
        /*val squareQRScan = findViewById<SquareQRScan>(R.id.viewk_squareQrScan)
       squareQRScan.setSquareQrScanCallback(object : SquareQRScan.SquareQrScanCallback {
           override fun onAnimEnd() {
               Log.i(TAG, "onAnimEnd : OK")
           }
       })
       GlobalScope.launch(Dispatchers.Main) {
           delay(4000)
           squareQRScan.requireSuccess()
       }*/

        /*val circleSpread = findViewById<CircleSpread>(R.id.viewk_circleSpread)
        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            circleSpread.requireStop()
            delay(3000)
            circleSpread.requireStart()
        }*/

        /*val radarRipple = findViewById<RadarRipple>(R.id.viewk_radarRipple)
        GlobalScope.launch(Dispatchers.Main) {
            delay(4000)
            radarRipple.requireStop()
            delay(2000)
            radarRipple.requireStart()
        }*/

        /*val radarWave = findViewById<RadarWave>(R.id.viewk_radarWave)
        GlobalScope.launch(Dispatchers.Main) {
            delay(4000)
            radarWave.requireStop()
            delay(2000)
            radarWave.requireStart()
        }*/
    }
}