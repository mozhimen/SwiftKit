package com.mozhimen.uicorektest.viewk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.uicorektest.databinding.ActivityViewkViewsBinding

class ViewKViewsActivity : BaseKActivityVB<ActivityViewkViewsBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
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