---
typora-root-url: imgs
---

# 1.简介

> SwiftKit For Android(开源框架,敏捷开发工具集)

```
   _____         _ ______  __ __ _ __ 
  / ___/      __(_) __/ /_/ //_/(_) /_
  \__ \ | /| / / / /_/ __/ ,<  / / __/
 ___/ / |/ |/ / / __/ /_/ /| |/ / /_  
/____/|__/|__/_/_/  \__/_/ |_/_/\__/  
```

***

# 2.更新日志

|Num|Time|Information| |:--|:--|---| |1|`2021.5`|*SwiftMK* Was Created| |2|`2021.10`|*SwiftMK* Was
Renamed To *SwiftK*| |2|`2022.4`|*SwiftK* Was Renamed To *SwiftKit*|

***

# 3.框架架构

- 架构大图

![SwiftKit架构.png](https://github.com/mozhimen/SwiftKit/blob/master/imgs/SwiftKit%E6%9E%B6%E6%9E%84.png)

- 架构细分概要

|Num|Name|Information| |:--|:--|:--| |Top Components| DebugK(调试组件群)|`查看本地参数`, `查看本地日志文件`, `切换系统主题`
, `性能调优`, `内存泄露监测`| ||AbilityK(业务能力支持)|`热更新`, `热修复`, `OpenCV识别`, `推送`, `扫码`, `语音`, `编解码`, `解压缩`|
||ComponentK(基本组件群)|`动画`, `摄像头`, `RESTful`, `权限管理`, `状态栏`| |Middle Components|UiCoreK(界面组件群)|`适配器`
, `Banner轮播图`, `绑定器`, `自定义按钮`, `数据加载`, `弹框`, `Item`, `自定义布局`, `上拉刷新,下拉加载`, `导航栏`, `ViewPager`
, `PopupWindow`, `刷新组件`,`搜索组件`, `侧边栏`, `Tab栏`, `自定义Text`, `自定义View`| ||UnderlayK(中间件组件群)|`Crash管理`
,`Fps管理`,`日志管理`
|Bottom Components|BasicK(底层支持)|`接口依赖`, `缓存管理`, `事件管理`,`粘性事件`, `多线程管理`, `扩展方法集合`, `四大组件预制类集合`
, `堆栈管理`, `TaskK依赖管理`, `工具集合`|
***

# 4.模块组成

- 模块大图

![](https://github.com/mozhimen/SwiftKit/blob/master/imgs/modules.png)

# 5.框架使用

## 5.1本地依赖

- fork框架并pull到你的本地

- 在setting.gradle引入项目

```
include ':SwiftKit'
```

- 设置项目地址

```
project(':SwiftKit').projectDir = new File("C:\\Users\\mozhimen\\Documents\\GitHub\\SwiftKit")
```

- 引入模块

```
include ':SwiftKit:basick'
include ':SwiftKit:uicorek'
include ':SwiftKit:debugk'
include ':SwiftKit:underlayk'
include ':SwiftKit:componentk'
```

- 在模块build.gradle下中使用

```
api project(path: ':SwiftKit:basick')
api project(path: ':SwiftKit:underlayk')
api project(path: ':SwiftKit:uicorek')
api project(path: ':SwiftKit:componentk')
debugImplementation project(':SwiftKit:debugk')
```

## 5.2三方库JitPack依赖

- 在全局build.gradle中加入

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

- 在build.gradle下使用

```
dependencies {
	implementation 'com.github.mozhimen.SwiftKit:abilityk:1.1.3'
}
```

# 6.模块功能的使用

> 后期更新,并在wiki中详细整理,请持续关注,也可以查看单模块的testapp中查看demo的使用范例
> 近期fix: 1.fix download file
> 3.TimePicker封装
> 近期更新: 1.消化BasePopwin
> 2.完善BaseDialogK
> 3.优化PopwinKBubble
> 4.整合Camera2,Camera1,CameraX
