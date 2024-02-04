package com.mozhimen.basick.lintk.optins.intent_filter

import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CIntentFilter

/**
 * @ClassName OApplication_REQUEST_LEGACY_EXTERNAL_STORAGE
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/4
 * @Version 1.0
 */
@AManifestKRequire(CIntentFilter.ACTION_VIEW_CATEGORY_DEFAULT_BROWSABLE)
@RequiresOptIn("The api is must add this IntentFilte to your AndroidManifest.xml. 需要声明此IntentFilter到你的AndroidManifest.xml application下", RequiresOptIn.Level.WARNING)
annotation class OIntentFilter_ACTION_VIEW_CATEGORY_DEFAULT_BROWSABLE