package com.mozhimen.basick.elemk.android.media.cons

import android.media.MediaFormat
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode


/**
 * @ClassName CMediaFormat
 * @Description [android.media.MediaFormat]
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/27 15:58
 * @Version 1.0
 */
object CMediaFormat {
    const val MIMETYPE_TEXT_PLAIN = "text/plain"

    ///////////////////////////////////////////////////////////////////

    const val MIMETYPE_IMAGE_ALL = "image/*"
    const val MIMETYPE_IMAGE_PNG = "image/png"
    const val MIMETYPE_IMAGE_JPEG = "image/jpeg"

    ///////////////////////////////////////////////////////////////////

    const val MIMETYPE_VIDEO_ALL = "video/*"
    const val MIMETYPE_VIDEO_VP8 = MediaFormat.MIMETYPE_VIDEO_VP8
    const val MIMETYPE_VIDEO_VP9 = MediaFormat.MIMETYPE_VIDEO_VP9

    @RequiresApi(CVersCode.V_29_10_Q)
    const val MIMETYPE_VIDEO_AV1 = MediaFormat.MIMETYPE_VIDEO_AV1
    const val MIMETYPE_VIDEO_AVC = MediaFormat.MIMETYPE_VIDEO_AVC
    const val MIMETYPE_VIDEO_HEVC = MediaFormat.MIMETYPE_VIDEO_HEVC
    const val MIMETYPE_VIDEO_MPEG4 = MediaFormat.MIMETYPE_VIDEO_MPEG4
    const val MIMETYPE_VIDEO_H263 = MediaFormat.MIMETYPE_VIDEO_H263
    const val MIMETYPE_VIDEO_MPEG2 = MediaFormat.MIMETYPE_VIDEO_MPEG2
    const val MIMETYPE_VIDEO_RAW = MediaFormat.MIMETYPE_VIDEO_RAW

    @RequiresApi(CVersCode.V_24_7_N)
    const val MIMETYPE_VIDEO_DOLBY_VISION = MediaFormat.MIMETYPE_VIDEO_DOLBY_VISION

    @RequiresApi(CVersCode.V_26_8_O)
    const val MIMETYPE_VIDEO_SCRAMBLED = MediaFormat.MIMETYPE_VIDEO_SCRAMBLED

    ///////////////////////////////////////////////////////////////////

    const val MIMETYPE_AUDIO_ALL = "audio/*"
    const val MIMETYPE_AUDIO_AMR_NB = MediaFormat.MIMETYPE_AUDIO_AMR_NB
    const val MIMETYPE_AUDIO_AMR_WB = MediaFormat.MIMETYPE_AUDIO_AMR_WB
    const val MIMETYPE_AUDIO_MPEG = MediaFormat.MIMETYPE_AUDIO_MPEG
    const val MIMETYPE_AUDIO_AAC = MediaFormat.MIMETYPE_AUDIO_AAC
    const val MIMETYPE_AUDIO_QCELP = MediaFormat.MIMETYPE_AUDIO_QCELP
    const val MIMETYPE_AUDIO_VORBIS = MediaFormat.MIMETYPE_AUDIO_VORBIS
    const val MIMETYPE_AUDIO_OPUS = MediaFormat.MIMETYPE_AUDIO_OPUS
    const val MIMETYPE_AUDIO_G711_ALAW = MediaFormat.MIMETYPE_AUDIO_G711_ALAW
    const val MIMETYPE_AUDIO_G711_MLAW = MediaFormat.MIMETYPE_AUDIO_G711_MLAW
    const val MIMETYPE_AUDIO_RAW = MediaFormat.MIMETYPE_AUDIO_RAW
    const val MIMETYPE_AUDIO_FLAC = MediaFormat.MIMETYPE_AUDIO_FLAC
    const val MIMETYPE_AUDIO_MSGSM = MediaFormat.MIMETYPE_AUDIO_MSGSM
    const val MIMETYPE_AUDIO_AC3 = MediaFormat.MIMETYPE_AUDIO_AC3

    @RequiresApi(CVersCode.V_23_6_M)
    const val MIMETYPE_AUDIO_EAC3 = MediaFormat.MIMETYPE_AUDIO_EAC3

    @RequiresApi(CVersCode.V_29_10_Q)
    const val MIMETYPE_AUDIO_EAC3_JOC = MediaFormat.MIMETYPE_AUDIO_EAC3_JOC

    @RequiresApi(CVersCode.V_29_10_Q)
    const val MIMETYPE_AUDIO_AC4 = MediaFormat.MIMETYPE_AUDIO_AC4

    @RequiresApi(CVersCode.V_26_8_O)
    const val MIMETYPE_AUDIO_SCRAMBLED = MediaFormat.MIMETYPE_AUDIO_SCRAMBLED

    /** MIME type for MPEG-H Audio single stream  */
    @RequiresApi(CVersCode.V_31_11_S)
    const val MIMETYPE_AUDIO_MPEGH_MHA1 = MediaFormat.MIMETYPE_AUDIO_MPEGH_MHA1

    /** MIME type for MPEG-H Audio single stream, encapsulated in MHAS  */
    @RequiresApi(CVersCode.V_31_11_S)
    const val MIMETYPE_AUDIO_MPEGH_MHM1 = MediaFormat.MIMETYPE_AUDIO_MPEGH_MHM1

    /** MIME type for DTS (up to 5.1 channels) audio stream.  */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_DTS = MediaFormat.MIMETYPE_AUDIO_DTS

    /** MIME type for DTS HD (up to 7.1 channels) audio stream.  */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_DTS_HD = MediaFormat.MIMETYPE_AUDIO_DTS_HD

    /** MIME type for DTS UHD (object-based) audio stream.  */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_DTS_UHD = MediaFormat.MIMETYPE_AUDIO_DTS_UHD

    /** MIME type for Dynamic Resolution Adaptation (DRA) audio stream.  */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_DRA = MediaFormat.MIMETYPE_AUDIO_DRA

    /** MIME type for Dolby Metadata-enhanced Audio Transmission (MAT) audio stream.  */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_DOLBY_MAT = MediaFormat.MIMETYPE_AUDIO_DOLBY_MAT

    /** MIME type for Dolby TrueHD audio format, based on Meridian Lossless Packing (MLP).  */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_DOLBY_TRUEHD = MediaFormat.MIMETYPE_AUDIO_DOLBY_TRUEHD

    /**
     * MIME type for AAC Low Complexity (LC) audio stream. Uses the scheme defined by
     * RFC 6381 with OTI of MPEG-4 (40) and AOT of AAC LC (2) from ISO/IEC 14496-3.
     */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_AAC_LC = MediaFormat.MIMETYPE_AUDIO_AAC_LC

    /**
     * MIME type for HE-AAC v1 (LC + SBR) audio stream. Uses the scheme defined by
     * RFC 6381 with OTI of MPEG-4 (40) and AOT of AAC SBR (5) from ISO/IEC 14496-3.
     */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_AAC_HE_V1 = MediaFormat.MIMETYPE_AUDIO_AAC_HE_V1

    /**
     * MIME type for HE-AAC v2 (LC + SBR + PS) audio stream. Uses the scheme defined by
     * RFC 6381 with OTI of MPEG-4 (40) and AOT of PS (29) from ISO/IEC 14496-3.
     */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_AAC_HE_V2 = MediaFormat.MIMETYPE_AUDIO_AAC_HE_V2

    /**
     * MIME type for AAC Enhanced Low Delay (ELD) audio stream. Uses the scheme defined by
     * RFC 6381 with OTI of MPEG-4 (40) and AOT of ELD (39) from ISO/IEC 14496-3.
     */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_AAC_ELD = MediaFormat.MIMETYPE_AUDIO_AAC_ELD

    /**
     * MIME type for AAC XHE audio stream. Uses the scheme defined by
     * RFC 6381 with OTI of MPEG-4 (40) and AOT of USAC (42) from ISO/IEC 14496-3.
     */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_AAC_XHE = MediaFormat.MIMETYPE_AUDIO_AAC_XHE

    /**
     * MIME type for MPEG-H Baseline (BL) Profile L3 audio stream. Uses the scheme defined by
     * RFC 6381 with mpegh3daProfileLevelIndication for main profile/L3 (0x3) from ISO/IEC 23008-3.
     */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_MPEGH_BL_L3 = MediaFormat.MIMETYPE_AUDIO_MPEGH_BL_L3

    /**
     * MIME type for MPEG-H Baseline (BL) Profile L4 audio stream. Uses the scheme defined by
     * RFC 6381 with mpegh3daProfileLevelIndication for main profile/L4 (0x4) from ISO/IEC 23008-3.
     */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_MPEGH_BL_L4 = MediaFormat.MIMETYPE_AUDIO_MPEGH_BL_L4

    /**
     * MIME type for MPEG-H Low Complexity (LC) L3 audio stream. Uses the scheme defined by
     * RFC 6381 with mpegh3daProfileLevelIndication for LC profile/L3 (0xD) from ISO/IEC 23008-3.
     */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_MPEGH_LC_L3 = MediaFormat.MIMETYPE_AUDIO_MPEGH_LC_L3

    /**
     * MIME type for MPEG-H Low Complexity (LC) L4 audio stream. Uses the scheme defined by
     * RFC 6381 with mpegh3daProfileLevelIndication for LC profile/L4 (0xE) from ISO/IEC 23008-3.
     */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_MPEGH_LC_L4 = MediaFormat.MIMETYPE_AUDIO_MPEGH_LC_L4

    /**
     * MIME type for the IEC61937 audio stream encapsulation. This type isn't defined by IANA.
     */
    @RequiresApi(CVersCode.V_33_11_TIRAMISU)
    const val MIMETYPE_AUDIO_IEC61937 = MediaFormat.MIMETYPE_AUDIO_IEC61937
}