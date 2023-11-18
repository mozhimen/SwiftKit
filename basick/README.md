**SDK规范化计划**
# BasicK库规范

## UtilK工具类库规范 

1. 关于包名

- 包名依据:要创建工具类的主体类(Class)的包名

例如: UtilKContext的包名为
```
package com.mozhimen.basick.utilk.android.content
```

2. 关于类名

- 类名统一采用object类型

- 如需TAG, 继承自IUtilK, 如需_context, 继承自BaseUtilK() 

- 类名命名: UtilK+主类名(Class)

例如: Context的工具类名为UtilKContext

3. 关于方法名

- Util工具类的方法集可分为五类:

获取 -> getXxx (不用带主语)
是否 -> isXxx/hasXxx (不用带主语)
应用 -> applyXxx (不用带主语,即set方法,为了防止扩展方法名冲突,统一用apply)
转化 -> xxx2xxx (带主语和宾语即什么转化为什么)
其他 -> xxx 

>这里的主语即类名UtilKXXX中的XXX

- 方法加@JvmStatic注解

- 方法类别之间用////隔开

例如: UtilKContext
```
package com.mozhimen.basick.utilk.android.content

object UtilKContext : BaseUtilK() {

  @JvmStatic
  fun getApplicationContext(context: Context): Context =
    context.applicationContext

  ////////////////////////////////////

  @JvmStatic
  fun applyXXX(context: Context) { ... }
}
```

4. 关于扩展方法

- 扩展方法统一声名在工具类上方

- 扩展方法调用工具类方法

例如: UtilKContext

```
fun Context.applyXXX() {
  UtilKContext.applyXXX(this)
}

object UtilKContext : BaseUtilK() {

  @JvmStatic
  fun applyXXX(context: Context) { ... }
}
```

# 隐式调用
```
<!--注册scheme-->
<intent-filter>
    <!--必有项-->
    <action android:name="android.intent.action.VIEW" />
    <!--表示该页面可以被隐式调用，必须加上该项-->
    <category android:name="android.intent.category.DEFAULT" />
    <!--BROWSABLE指定该Activity能被浏览器安全调用-->
    <category android:name="android.intent.category.BROWSABLE" />
    <!--协议部分-->
    <!--声明自定义scheme，类似于http, https-->
    <data
        android:host="basick"
        android:scheme="basick" />
</intent-filter>
```

# 内置浏览器安全策略
代码中强制设置不使用『安全浏览策略』
Android O 以上
webviewSetting.setSafeBrowsingEnabled(false)
```
<manifest>
    <application>
        <meta-data android:name="android.webkit.WebView.EnableSafeBrowsing"
android:value="false" />
        ...
    </application>
</manifest>
```

# 最大纵横比
```
<!--在全屏的时候，避免出现一些屏幕黑边-->
<meta-data
     android:name="android.max_aspect"
     android:value="2.4" />
```

# 刘海屏适配

```
<!--适配华为（huawei）刘海屏-->
<meta-data
    android:name="android.notch_support"
    android:value="true" />

<!--适配小米（xiaomi）刘海屏-->
<meta-data
    android:name="notch.config"
    android:value="portrait|landscape" />
```