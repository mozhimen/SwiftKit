package com.mozhimen.basick.stackk.cons

import android.os.Bundle

/**
 * @ClassName LifecycleCallback
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/7/23
 * @Version 1.0
 */
sealed class SLifecycleCallbackEvent {
    data class ON_CREATE(val savedInstanceState: Bundle?) : SLifecycleCallbackEvent()
    object ON_START : SLifecycleCallbackEvent()
    object ON_RESUME : SLifecycleCallbackEvent()
    object ON_PAUSE : SLifecycleCallbackEvent()
    object ON_STOP : SLifecycleCallbackEvent()
    data class ON_SAVEINSTANCESTATE(val outState: Bundle) : SLifecycleCallbackEvent()
    object ON_DESTROY : SLifecycleCallbackEvent()
}