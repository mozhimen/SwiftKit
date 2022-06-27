package com.mozhimen.abilityk.camera2k.helpers

import com.mozhimen.abilityk.camera2k.commons.AFilter

/**
 * @ClassName NoFilter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/27 18:01
 * @Version 1.0
 */
class NoFilter : AFilter() {
    override fun onFilterCreate() {
        createProgramByAssetsFile(
            "shader/base_vertex.sh",
            "shader/base_fragment.sh"
        )
    }

    override fun onFilterSizeChanged(width: Int, height: Int) {}
}