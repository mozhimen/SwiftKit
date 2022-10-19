package com.mozhimen.basicktest.prefabk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.start
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityPrefabkBinding

class PrefabKActivity : BaseKActivity<ActivityPrefabkBinding, BaseKViewModel>(R.layout.activity_prefabk) {
    fun goPrefabKAudio(view: View) {
        start<PrefabKAudioActivity>()
    }
}