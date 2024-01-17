package com.mozhimen.basick.utilk.android.os

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName UtilKBuildVersion
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/7/1 11:22
 * @Version 1.0
 */
fun Int.isAfterVersion(): Boolean =
    UtilKBuildVersion.isAfterVersion(this)

fun Int.isBeforeVersion(): Boolean =
    UtilKBuildVersion.isBeforeVersion(this)

object UtilKBuildVersion {

    @JvmStatic
    fun getSDKInt(): Int =
        Build.VERSION.SDK_INT

    //构建Release版本号
    @JvmStatic
    fun getRelease(): String =
        Build.VERSION.RELEASE

    //构建版本名称
    @JvmStatic
    fun getCodeName(): String =
        Build.VERSION.CODENAME

    @JvmStatic
    fun getSDKStr(): String =
        getSDKInt().toString()

    @JvmStatic
    fun isAfterVersion(versionInt: Int): Boolean =
        getSDKInt() >= versionInt

    @JvmStatic
    fun isBeforeVersion(versionInt: Int): Boolean =
        getSDKInt() < versionInt

    ///////////////////////////////////////////////////////////////////////

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_1_1_B)
    fun isAfterV_1_1_B(): Boolean =
        isAfterVersion(CVersCode.V_1_1_B)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_2_11_B11)
    fun isAfterV_2_11_B11(): Boolean =
        isAfterVersion(CVersCode.V_2_11_B11)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_3_15_C)
    fun isAfterV_3_15_C(): Boolean =
        isAfterVersion(CVersCode.V_3_15_C)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_4_16_D)
    fun isAfterV_4_16_D(): Boolean =
        isAfterVersion(CVersCode.V_4_16_D)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_5_2_E)
    fun isAfterV_5_2_E(): Boolean =
        isAfterVersion(CVersCode.V_5_2_E)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_6_201_E01)
    fun isAfterV_6_201_E01(): Boolean =
        isAfterVersion(CVersCode.V_6_201_E01)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_7_21_E1)
    fun isAfterV_7_21_E1(): Boolean =
        isAfterVersion(CVersCode.V_7_21_E1)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_8_22_F)
    fun isAfterV_8_22_F(): Boolean =
        isAfterVersion(CVersCode.V_8_22_F)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_9_23_G)
    fun isAfterV_9_23_G(): Boolean =
        isAfterVersion(CVersCode.V_9_23_G)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_10_233_G1)
    fun isAfterV_10_233_G1(): Boolean =
        isAfterVersion(CVersCode.V_10_233_G1)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_11_3_H)
    fun isAfterV_11_3_H(): Boolean =
        isAfterVersion(CVersCode.V_11_3_H)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_12_31_H1)
    fun isAfterV_12_31_H1(): Boolean =
        isAfterVersion(CVersCode.V_12_31_H1)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_13_32_H2)
    fun isAfterV_13_32_H2(): Boolean =
        isAfterVersion(CVersCode.V_13_32_H2)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_14_4_I)
    fun isAfterV_14_4_I(): Boolean =
        isAfterVersion(CVersCode.V_14_4_I)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_15_403_I1)
    fun isAfterV_15_403_I1(): Boolean =
        isAfterVersion(CVersCode.V_15_403_I1)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_16_41_J)
    fun isAfterV_16_41_J(): Boolean =
        isAfterVersion(CVersCode.V_16_41_J)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_17_42_J1)
    fun isAfterV_17_42_J1(): Boolean =
        isAfterVersion(CVersCode.V_17_42_J1)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_18_43_J2)
    fun isAfterV_18_43_J2(): Boolean =
        isAfterVersion(CVersCode.V_18_43_J2)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_19_44_K)
    fun isAfterV_19_44_K(): Boolean =
        isAfterVersion(CVersCode.V_19_44_K)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_20_44W_KW)
    fun isAfterV_20_44W_KW(): Boolean =
        isAfterVersion(CVersCode.V_20_44W_KW)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_21_5_L)
    fun isAfterV_21_5_L(): Boolean =
        isAfterVersion(CVersCode.V_21_5_L)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_22_51_L1)
    fun isAfterV_22_51_L1(): Boolean =
        isAfterVersion(CVersCode.V_22_51_L1)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_23_6_M)
    fun isAfterV_23_6_M(): Boolean =
        isAfterVersion(CVersCode.V_23_6_M)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_24_7_N)
    fun isAfterV_24_7_N(): Boolean =
        isAfterVersion(CVersCode.V_24_7_N)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_25_71_N1)
    fun isAfterV_25_71_N1(): Boolean =
        isAfterVersion(CVersCode.V_25_71_N1)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_26_8_O)
    fun isAfterV_26_8_O(): Boolean =
        isAfterVersion(CVersCode.V_26_8_O)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_27_81_O1)
    fun isAfterV_27_81_O1(): Boolean =
        isAfterVersion(CVersCode.V_27_81_O1)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_28_9_P)
    fun isAfterV_28_9_P(): Boolean =
        isAfterVersion(CVersCode.V_28_9_P)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_29_10_Q)
    fun isAfterV_29_10_Q(): Boolean =
        isAfterVersion(CVersCode.V_29_10_Q)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_30_11_R)
    fun isAfterV_30_11_R(): Boolean =
        isAfterVersion(CVersCode.V_30_11_R)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_31_12_S)
    fun isAfterV_31_11_S(): Boolean =
        isAfterVersion(CVersCode.V_31_12_S)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_32_12_S_V2)
    fun isAfterV_32_11_S_V2(): Boolean =
        isAfterVersion(CVersCode.V_32_12_S_V2)

    @JvmStatic
    @ChecksSdkIntAtLeast(api = CVersCode.V_33_13_TIRAMISU)
    fun isAfterV_33_11_TIRAMISU(): Boolean =
        isAfterVersion(CVersCode.V_33_13_TIRAMISU)

    ///////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isBeforeV_1_1_B(): Boolean =
        isBeforeVersion(CVersCode.V_1_1_B)

    @JvmStatic
    fun isBeforeV_2_11_B11(): Boolean =
        isBeforeVersion(CVersCode.V_2_11_B11)

    @JvmStatic
    fun isBeforeV_3_15_C(): Boolean =
        isBeforeVersion(CVersCode.V_3_15_C)

    @JvmStatic
    fun isBeforeV_4_16_D(): Boolean =
        isBeforeVersion(CVersCode.V_4_16_D)

    @JvmStatic
    fun isBeforeV_5_2_E(): Boolean =
        isBeforeVersion(CVersCode.V_5_2_E)

    @JvmStatic
    fun isBeforeV_6_201_E01(): Boolean =
        isBeforeVersion(CVersCode.V_6_201_E01)

    @JvmStatic
    fun isBeforeV_7_21_E1(): Boolean =
        isBeforeVersion(CVersCode.V_7_21_E1)

    @JvmStatic
    fun isBeforeV_8_22_F(): Boolean =
        isBeforeVersion(CVersCode.V_8_22_F)

    @JvmStatic
    fun isBeforeV_9_23_G(): Boolean =
        isBeforeVersion(CVersCode.V_9_23_G)

    @JvmStatic
    fun isBeforeV_10_233_G1(): Boolean =
        isBeforeVersion(CVersCode.V_10_233_G1)

    @JvmStatic
    fun isBeforeV_11_3_H(): Boolean =
        isBeforeVersion(CVersCode.V_11_3_H)

    @JvmStatic
    fun isBeforeV_12_31_H1(): Boolean =
        isBeforeVersion(CVersCode.V_12_31_H1)

    @JvmStatic
    fun isBeforeV_13_32_H2(): Boolean =
        isBeforeVersion(CVersCode.V_13_32_H2)

    @JvmStatic
    fun isBeforeV_14_4_I(): Boolean =
        isBeforeVersion(CVersCode.V_14_4_I)

    @JvmStatic
    fun isBeforeV_15_403_I1(): Boolean =
        isBeforeVersion(CVersCode.V_15_403_I1)

    @JvmStatic
    fun isBeforeV_16_41_J(): Boolean =
        isBeforeVersion(CVersCode.V_16_41_J)

    @JvmStatic
    fun isBeforeV_17_42_J1(): Boolean =
        isBeforeVersion(CVersCode.V_17_42_J1)

    @JvmStatic
    fun isBeforeV_18_43_J2(): Boolean =
        isBeforeVersion(CVersCode.V_18_43_J2)

    @JvmStatic
    fun isBeforeV_19_44_K(): Boolean =
        isBeforeVersion(CVersCode.V_19_44_K)

    @JvmStatic
    fun isBeforeV_20_44W_KW(): Boolean =
        isBeforeVersion(CVersCode.V_20_44W_KW)

    @JvmStatic
    fun isBeforeV_21_5_L(): Boolean =
        isBeforeVersion(CVersCode.V_21_5_L)

    @JvmStatic
    fun isBeforeV_22_51_L1(): Boolean =
        isBeforeVersion(CVersCode.V_22_51_L1)

    @JvmStatic
    fun isBeforeV_23_6_M(): Boolean =
        isBeforeVersion(CVersCode.V_23_6_M)

    @JvmStatic
    fun isBeforeV_24_7_N(): Boolean =
        isBeforeVersion(CVersCode.V_24_7_N)

    @JvmStatic
    fun isBeforeV_25_71_N1(): Boolean =
        isBeforeVersion(CVersCode.V_25_71_N1)

    @JvmStatic
    fun isBeforeV_26_8_O(): Boolean =
        isBeforeVersion(CVersCode.V_26_8_O)

    @JvmStatic
    fun isBeforeV_27_81_O1(): Boolean =
        isBeforeVersion(CVersCode.V_27_81_O1)

    @JvmStatic
    fun isBeforeV_28_9_P(): Boolean =
        isBeforeVersion(CVersCode.V_28_9_P)

    @JvmStatic
    fun isBeforeV_29_10_Q(): Boolean =
        isBeforeVersion(CVersCode.V_29_10_Q)

    @JvmStatic
    fun isBeforeV_30_11_R(): Boolean =
        isBeforeVersion(CVersCode.V_30_11_R)

    @JvmStatic
    fun isBeforeV_31_11_S(): Boolean =
        isBeforeVersion(CVersCode.V_31_12_S)

    @JvmStatic
    fun isBeforeV_32_11_S_V2(): Boolean =
        isBeforeVersion(CVersCode.V_32_12_S_V2)

    @JvmStatic
    fun isBeforeV_33_11_TIRAMISU(): Boolean =
        isBeforeVersion(CVersCode.V_33_13_TIRAMISU)

}