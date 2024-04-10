package com.mozhimen.basick.elemk.android.util.cons

import android.util.Base64


/**
 * @ClassName CBase64
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/7 19:00
 * @Version 1.0
 */
object CBase64 {
    /**
     * 在 Android 中，`Base64` 类提供了不同的标志（flags）来控制 Base64 编码和解码的行为。下面是各个标志的区别：
     * 1. `Base64.DEFAULT`：这是默认的 Base64 标志。它根据 RFC 4648 规范进行编码和解码，并使用标准的 Base64 字符集。编码结果中会包含换行符（`\n`）以使输出每行不超过 76 个字符，并在解码时会自动处理这些换行符。这是最常用的标志，适用于大多数情况。
     * 2. `Base64.NO_WRAP`：这个标志用于禁止在编码结果中包含换行符（`\n`）。编码结果将是一行连续的字符串，没有换行符。解码时要求输入字符串没有换行符。使用此标志可以得到没有换行符的紧凑编码结果。
     * 3. `Base64.URL_SAFE`：这个标志在编码时使用 URL 安全的字符集，将 `+` 和 `/` 字符替换为 `-` 和 `_`。这种编码结果可以在 URL 和文件名中使用，因为它避免了需要进行 URL 编码的特殊字符。解码时需要使用与编码一致的标志。
     * 4. `Base64.NO_PADDING`：这个标志用于禁止在编码结果的末尾填充 `=` 字符。Base64 编码通常要求输入数据的长度是 3 的倍数，如果不足则需要使用 `=` 字符进行填充。使用此标志可以得到没有填充字符的编码结果，但解码时要求输入数据的长度是有效的。
     *
     * 总结：
     *
     * - `Base64.DEFAULT` 是默认的 Base64 标志，包含换行符，适用于大多数情况。
     * - `Base64.NO_WRAP` 禁止在编码结果中包含换行符，得到紧凑的编码结果。
     * - `Base64.URL_SAFE` 使用 URL 安全的字符集进行编码，适用于 URL 和文件名。
     * - `Base64.NO_PADDING` 禁止在编码结果的末尾填充字符，得到没有填充字符的编码结果。
     *
     * 请根据你的需求选择适合的标志来进行 Base64 编码和解码操作。
     */
    const val DEFAULT = Base64.DEFAULT
    const val NO_WRAP = Base64.NO_WRAP
    const val URL_SAFE = Base64.URL_SAFE
    const val NO_PADDING = Base64.NO_PADDING
}