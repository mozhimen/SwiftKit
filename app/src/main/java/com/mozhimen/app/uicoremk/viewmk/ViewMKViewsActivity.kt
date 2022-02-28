package com.mozhimen.app.uicoremk.viewmk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityViewmkViewsBinding

class ViewMKViewsActivity : AppCompatActivity() {
    private val vb by lazy { ActivityViewmkViewsBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        /*val squareQRScan = findViewById<SquareQRScan>(R.id.viewmk_squareQrScan)
        squareQRScan.setSquareQrScanCallback(object : SquareQRScan.SquareQrScanCallback {
            override fun onAnimEnd() {
                Log.i(TAG, "onAnimEnd : OK")
            }
        })
        GlobalScope.launch(Dispatchers.Main) {
            delay(4000)
            squareQRScan.requireSuccess()
        }*/

        /*val circleSpread = findViewById<CircleSpread>(R.id.viewmk_circleSpread)
        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            circleSpread.requireStop()
            delay(3000)
            circleSpread.requireStart()
        }*/

        /*val radarRipple = findViewById<RadarRipple>(R.id.viewmk_radarRipple)
        GlobalScope.launch(Dispatchers.Main) {
            delay(4000)
            radarRipple.requireStop()
            delay(2000)
            radarRipple.requireStart()
        }*/

        /*val radarWave = findViewById<RadarWave>(R.id.viewmk_radarWave)
        GlobalScope.launch(Dispatchers.Main) {
            delay(4000)
            radarWave.requireStop()
            delay(2000)
            radarWave.requireStart()
        }*/

        /*val ringRotate = findViewById<RingRotate>(R.id.viewmk_ringRotate)
        GlobalScope.launch(Dispatchers.Main) {
            delay(4000)
            ringRotate.requireStop()
            delay(2000)
            ringRotate.requireStart()
        }*/
    }
}