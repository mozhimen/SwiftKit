package com.mozhimen.uicorektest.bark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityBarkBinding

class BarKActivity : BaseActivityVB<ActivityBarkBinding>() {
    override fun initView(savedInstanceState: android.os.Bundle?) {
        vb.barkRating.setOnRatingBarChangeListener { _, rating, fromUser -> Log.d(TAG, "onRatingChanged: rating $rating fromUser $fromUser") }
    }
}