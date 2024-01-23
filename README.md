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

| Num | Time      | Information                |
|:--- |:--------- | ------------------------------------------ |
| 1   | `2021.5`  | *SwiftMK* Was Created      |
| 2   | `2021.10` | *SwiftMK* Was Renamed To *SwiftK*          |
| 3   | `2022.4`  | *SwiftK* Was Renamed To *SwiftKit*         |
| 4   | `2023.8`  | *SwiftKit* Start Plan To be More Normalize |
| 5   | `2024.1`  | *SwiftKit* Start Plan To be More Simpified |

***

# 3.框架架构

- 架构大图

2023.8

![SwiftKit架构.png](https://github.com/mozhimen/SwiftKit/blob/master/imgs/SwiftKit%E6%9E%B6%E6%9E%84.png)

2024.1

![SwiftKit架构.png](https://github.com/mozhimen/SwiftKit/blob/master/imgs/SwiftKit%E6%9E%B6%E6%9E%84.png)

- 架构细分概要

| Num | Name                  | Information                                                                                                                                                                                                               |
|:-- |:----------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Top Components    | ~~DebugK(调试组件群)~~         | ~~`查看本地参数`, `查看分享本地日志`, `查看本地奔溃日志`, `切换系统主题`, `Fps性能监测`, `内存泄露监测`~~ **(拆分功能)**                                                                                                                                                                                           |
|   | ~~AbilityK(业务能力支持)~~  | ~~`软件更新`, `扫码`, `文本转语音`~~ **(拆分功能)**                                                                                                                                                                                      |
|   | ~~ComponentK(基本组件群)~~ | ~~`CameraX`, `相机`, `程序安装`, `媒体播放器`, `音视频管理`, `导航`, `网络监测`, `文件下载`, `RESTful`~~ **(拆分功能)**                                                                                                                                 |
| Middle Components | ~~UiCoreK(界面组件群)~~    | ~~`适配器`, `机型适配`, `状态栏管理`, `自定义按钮`, `弹框`, `自定义Drawable`, `Banner轮播图`, `高斯模糊组件`, `上拉刷新,下拉加载`,  `刷新组件`, `搜索组件`, `导航栏组件`, `侧边栏`,     `自定义Slider`, `Tab栏`, `PopupWindow`, `Recycler组件`, `自定义Text`, `吐司`, `ViewHolder`, `自定义View`~~ **(拆分功能)** |
|   | ~~UnderlayK(中间件组件群)~~ | ~~`Crash管理`, `Fps管理`, `日志管理`~~ **(拆分功能)**                                                                                                               |
| Bottom Components | BasicK(底层支持)          | `动画构建器`, `缓存管理`, `接口依赖`, `基类群`, `图片管理`, `代码规范管理`,`Manifest管理`, `权限管理`, `通信管理`, `加解密`, `编码`, `消息通信`, `堆栈管理`, `任务构建器`,     `任务链`, `扩展方法集合`, `工具集合`                                                                          |

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
project(':SwiftKit').projectDir = new File("C:\\Users\\${System.getenv().get("USERNAME")}\\Documents\\GitHub\\SwiftKit")
```

- 引入模块

```
include ':SwiftKit:basick'
```

- 在模块build.gradle下中使用

```
api project(path: ':SwiftKit:basick')
```

- 例如

```
//SwiftKit
include ':SwiftKit'
project(':SwiftKit').projectDir = new File("C:\\Users\\${System.getenv().get("USERNAME")}\\Documents\\GitHub\\SwiftKit")
include ':SwiftKit:basick'
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
> 2.优化PopwinKBubble
