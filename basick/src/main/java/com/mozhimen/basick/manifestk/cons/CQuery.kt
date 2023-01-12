package com.mozhimen.basick.manifestk.cons


/**
 * @ClassName Queries
 * @Description

 * AndroidManifest.xml (example)

<queries>
<intent>
<action android:name="android.intent.action.TTS_SERVICE" />
</intent>
</queries>

 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/11 18:40
 * @Version 1.0
 */
object CQuery {
    const val TTS_SERVICE = """
<intent>
<action android:name="android.intent.action.TTS_SERVICE" />
</intent>
    """
}