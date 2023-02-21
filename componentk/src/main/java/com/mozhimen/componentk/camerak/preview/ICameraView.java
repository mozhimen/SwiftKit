package com.mozhimen.componentk.camerak.preview;

import com.mozhimen.componentk.camerak.preview.Direction;
import com.mozhimen.componentk.camerak.preview.ScalableType;

/**
 * @ClassName ICameraView
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 21:47
 * @Version 1.0
 */
interface ICameraView {
    void resetPreviewSize(int var1, int var2);

    void setMirror(boolean var1);

    void setDisplayDir(Direction var1);

    void setStyle(ScalableType var1);

    void onStart();

    void onResume();

    void onPause();

    void onStop();
}
