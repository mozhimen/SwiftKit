package com.mozhimen.basick.elemk.cons


/**
 * @ClassName CMediaFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/27 15:58
 * @Version 1.0
 */
object CMediaFormat {
    const val MIMETYPE_IMAGE_PNG = "image/png"
    const val MIMETYPE_IMAGE_JPEG = "image/jpeg"
    const val MIMETYPE_VIDEO_VP8 = "video/x-vnd.on2.vp8"
    const val MIMETYPE_VIDEO_VP9 = "video/x-vnd.on2.vp9"
    const val MIMETYPE_VIDEO_AV1 = "video/av01"
    const val MIMETYPE_VIDEO_AVC = "video/avc"
    const val MIMETYPE_VIDEO_HEVC = "video/hevc"
    const val MIMETYPE_VIDEO_MPEG4 = "video/mp4v-es"
    const val MIMETYPE_VIDEO_H263 = "video/3gpp"
    const val MIMETYPE_VIDEO_MPEG2 = "video/mpeg2"
    const val MIMETYPE_VIDEO_RAW = "video/raw"
    const val MIMETYPE_VIDEO_DOLBY_VISION = "video/dolby-vision"
    const val MIMETYPE_VIDEO_SCRAMBLED = "video/scrambled"

    const val MIMETYPE_AUDIO_AMR_NB = "audio/3gpp"
    const val MIMETYPE_AUDIO_AMR_WB = "audio/amr-wb"
    const val MIMETYPE_AUDIO_MPEG = "audio/mpeg"
    const val MIMETYPE_AUDIO_AAC = "audio/mp4a-latm"
    const val MIMETYPE_AUDIO_QCELP = "audio/qcelp"
    const val MIMETYPE_AUDIO_VORBIS = "audio/vorbis"
    const val MIMETYPE_AUDIO_OPUS = "audio/opus"
    const val MIMETYPE_AUDIO_G711_ALAW = "audio/g711-alaw"
    const val MIMETYPE_AUDIO_G711_MLAW = "audio/g711-mlaw"
    const val MIMETYPE_AUDIO_RAW = "audio/raw"
    const val MIMETYPE_AUDIO_FLAC = "audio/flac"
    const val MIMETYPE_AUDIO_MSGSM = "audio/gsm"
    const val MIMETYPE_AUDIO_AC3 = "audio/ac3"
    const val MIMETYPE_AUDIO_EAC3 = "audio/eac3"
    const val MIMETYPE_AUDIO_EAC3_JOC = "audio/eac3-joc"
    const val MIMETYPE_AUDIO_AC4 = "audio/ac4"
    const val MIMETYPE_AUDIO_SCRAMBLED = "audio/scrambled"

    /** MIME type for MPEG-H Audio single stream  */
    const val MIMETYPE_AUDIO_MPEGH_MHA1 = "audio/mha1"

    /** MIME type for MPEG-H Audio single stream, encapsulated in MHAS  */
    const val MIMETYPE_AUDIO_MPEGH_MHM1 = "audio/mhm1"

    /** MIME type for DTS (up to 5.1 channels) audio stream.  */
    const val MIMETYPE_AUDIO_DTS = "audio/vnd.dts"

    /** MIME type for DTS HD (up to 7.1 channels) audio stream.  */
    const val MIMETYPE_AUDIO_DTS_HD = "audio/vnd.dts.hd"

    /** MIME type for DTS UHD (object-based) audio stream.  */
    const val MIMETYPE_AUDIO_DTS_UHD = "audio/vnd.dts.uhd"

    /** MIME type for Dynamic Resolution Adaptation (DRA) audio stream.  */
    const val MIMETYPE_AUDIO_DRA = "audio/vnd.dra"

    /** MIME type for Dolby Metadata-enhanced Audio Transmission (MAT) audio stream.  */
    const val MIMETYPE_AUDIO_DOLBY_MAT = "audio/vnd.dolby.mat"

    /** MIME type for Dolby TrueHD audio format, based on Meridian Lossless Packing (MLP).  */
    const val MIMETYPE_AUDIO_DOLBY_TRUEHD = "audio/vnd.dolby.mlp"

    /**
     * MIME type for AAC Low Complexity (LC) audio stream. Uses the scheme defined by
     * RFC 6381 with OTI of MPEG-4 (40) and AOT of AAC LC (2) from ISO/IEC 14496-3.
     */
    const val MIMETYPE_AUDIO_AAC_LC = "audio/mp4a.40.02"

    /**
     * MIME type for HE-AAC v1 (LC + SBR) audio stream. Uses the scheme defined by
     * RFC 6381 with OTI of MPEG-4 (40) and AOT of AAC SBR (5) from ISO/IEC 14496-3.
     */
    const val MIMETYPE_AUDIO_AAC_HE_V1 = "audio/mp4a.40.05"

    /**
     * MIME type for HE-AAC v2 (LC + SBR + PS) audio stream. Uses the scheme defined by
     * RFC 6381 with OTI of MPEG-4 (40) and AOT of PS (29) from ISO/IEC 14496-3.
     */
    const val MIMETYPE_AUDIO_AAC_HE_V2 = "audio/mp4a.40.29"

    /**
     * MIME type for AAC Enhanced Low Delay (ELD) audio stream. Uses the scheme defined by
     * RFC 6381 with OTI of MPEG-4 (40) and AOT of ELD (39) from ISO/IEC 14496-3.
     */
    const val MIMETYPE_AUDIO_AAC_ELD = "audio/mp4a.40.39"

    /**
     * MIME type for AAC XHE audio stream. Uses the scheme defined by
     * RFC 6381 with OTI of MPEG-4 (40) and AOT of USAC (42) from ISO/IEC 14496-3.
     */
    const val MIMETYPE_AUDIO_AAC_XHE = "audio/mp4a.40.42"

    /**
     * MIME type for MPEG-H Baseline (BL) Profile L3 audio stream. Uses the scheme defined by
     * RFC 6381 with mpegh3daProfileLevelIndication for main profile/L3 (0x3) from ISO/IEC 23008-3.
     */
    const val MIMETYPE_AUDIO_MPEGH_BL_L3 = "audio/mhm1.03"

    /**
     * MIME type for MPEG-H Baseline (BL) Profile L4 audio stream. Uses the scheme defined by
     * RFC 6381 with mpegh3daProfileLevelIndication for main profile/L4 (0x4) from ISO/IEC 23008-3.
     */
    const val MIMETYPE_AUDIO_MPEGH_BL_L4 = "audio/mhm1.04"

    /**
     * MIME type for MPEG-H Low Complexity (LC) L3 audio stream. Uses the scheme defined by
     * RFC 6381 with mpegh3daProfileLevelIndication for LC profile/L3 (0xD) from ISO/IEC 23008-3.
     */
    const val MIMETYPE_AUDIO_MPEGH_LC_L3 = "audio/mhm1.0d"

    /**
     * MIME type for MPEG-H Low Complexity (LC) L4 audio stream. Uses the scheme defined by
     * RFC 6381 with mpegh3daProfileLevelIndication for LC profile/L4 (0xE) from ISO/IEC 23008-3.
     */
    const val MIMETYPE_AUDIO_MPEGH_LC_L4 = "audio/mhm1.0e"

    /**
     * MIME type for the IEC61937 audio stream encapsulation. This type isn't defined by IANA.
     */
    const val MIMETYPE_AUDIO_IEC61937 = "audio/x-iec61937"
}