package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.kotlin.UtilKString
import com.mozhimen.basick.utilk.kotlin.getSplitFirstIndexToStart
import com.mozhimen.basick.utilk.kotlin.text.checkStrUrl
import com.mozhimen.basick.utilk.kotlin.printlog
import org.junit.Test

class TestUtilKString {
    @Test
    fun findFirst() {
        val index = UtilKString.findFirst("5a1fbe000000000000f5", "5a")
        UtilKString.subStr("5a1fbe000000000000f5", index, 20).printlog()
        UtilKString.isNotEmpty(",", ".").printlog()

        val str = "http://www.sq.com/construction-sites-images"
        UtilKString.getSplitFirstIndexToStart(str, "/").printlog()
        str.checkStrUrl().printlog()
    }

    @Test
    fun getSplitFirstIndexToStart() {
        "https://lele-res.lelejoy.com/xt_20231009160759/fltb_20231009160835/js_20231123162006.png?q-sign-algorithm=sha1&q-ak=AKID3Ci-SLEsv1XVlBIP47X8stqtw5r5lpEgzelNW3faUZnpROXknS3xLwBmei85Yqgo&q-sign-time=1703992356;1704292356&q-key-time=1703992356;1703999556&q-header-list=&q-url-param-list=&q-signature=4e17332cc90959c188d6e64a67a137d719a40166&x-cos-security-token=XGHEWc5XjRLpTxiwO9bkXmL9twwPr38a66ffd38a2955dc9386202b3d1aebf0f1-QQ0sm9tJ_mwUfehSH1Ub02HdTjuyjYGPyCt3lnCA4k3RBIXVgXN4QEHOcReD0AE4I8S35HgFIjEsui2WMJOIAiNi9QOHuhCi5452XnIxOXX2FDlEW_RfOAHpatDUZ7pagPmegqqGV1c-78HXRfP9b9zMVgLzz6VhTbPoFGDwMziQxwPIDCcMq2b0Cyxh0B8zElv0QdcZKd_j9i4FreumP8HfuqFuP5u2YdVgDl3wA0OtqTY9ib6T7J1OTJzio1smxezSGd3uCVGfU8a5PPI9rEU3Mp9IjmT6WdDHUFxRdfWVGd031NC0lqeWNwHj23pRBiiQSBGLq19jKFepOF28Ij5998YFnZ1ZXWIid1u-ggnS9sm7jwkwa0BHHEjCaSi_jXiG84uuYQutCLw3c_liUa2phMBm6Y8rVt-O27r_QJPYeILVGXclh0NCypR4Gvu0FdZIC9jlekS4QSJrT0WAJqfIBXH-YFIG_wSc6G6pxo7514nOKv9sX3MVCEWzwzNV2TATypaf3CjgmQxbfsCijBuzEp3HxpCsWKTqKTAt8_NPfrr0tMPFE-u5K1gZtXSOT39LXyVQbCZlLOVPENC07Sp2jgEV2MCqYcLRiRdlRJL5VzPNhpC4dySB7-EL3LLkjUswihWVJMBBnU9fYDFACOcVLar3Nr0HG2XfGRtsPLCjM2zgabqVJlRxJKpmYD101QToCHwQJhecickL61C-UGzqkWH7_FIIFBuItItCI1IDpvikkkCEhAOcFNOYac2ZCpdlqJYOOhI-awgB4olrg"
            .replace("https://lele-res.lelejoy.com","").getSplitFirstIndexToStart("?q-sign-algorithm").printlog()
    }
}