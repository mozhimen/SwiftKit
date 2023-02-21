package com.mozhimen.componentk.camerak.mos;

import com.mozhimen.componentk.camerak.cons.CameraFlash;
import com.mozhimen.componentk.camerak.cons.CameraFocus;

import java.util.List;

/**
 * @ClassName IAttributes
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 22:22
 * @Version 1.0
 */
public class IAttributes {
    public CameraFacing facing;
    public int orientation = 0;
    public List<CameraSize> previewSize;
    public List<CameraSize> photoSize;
    public List<CameraFlash> flashes;
    public List<CameraFocus> focusList;

    public IAttributes() {
    }
}