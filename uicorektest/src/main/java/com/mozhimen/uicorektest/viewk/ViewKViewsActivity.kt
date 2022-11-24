package com.mozhimen.uicorektest.viewk

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.uicorektest.databinding.ActivityViewkViewsBinding

class ViewKViewsActivity : BaseActivityVB<ActivityViewkViewsBinding>() {

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

        /*val viewKCircleSpread = findViewById<CircleSpread>(R.id.viewk_viewKCircleSpread)
        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            viewKCircleSpread.requireStop()
            delay(3000)
            viewKCircleSpread.requireStart()
        }*/

        /*val viewKRadarRipple = findViewById<RadarRipple>(R.id.viewk_viewKRadarRipple)
        GlobalScope.launch(Dispatchers.Main) {
            delay(4000)
            viewKRadarRipple.requireStop()
            delay(2000)
            viewKRadarRipple.requireStart()
        }*/

        /*val viewKRadarWave = findViewById<RadarWave>(R.id.viewk_viewKRadarWave)
        GlobalScope.launch(Dispatchers.Main) {
            delay(4000)
            viewKRadarWave.requireStop()
            delay(2000)
            viewKRadarWave.requireStart()
        }*/
    }
}