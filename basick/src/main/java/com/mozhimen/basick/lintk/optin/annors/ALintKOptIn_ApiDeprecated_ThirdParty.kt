package com.mozhimen.basick.lintk.optin.annors

/**
 * @ClassName AOptInOfficialDeprecated
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/7 11:53
 * @Version 1.0
 */
@RequiresOptIn("The api is deprecated by third party (or maybe no longer maintain).", RequiresOptIn.Level.WARNING)
annotation class ALintKOptIn_ApiDeprecated_ThirdParty
