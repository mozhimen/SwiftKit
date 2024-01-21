package com.mozhimen.componentktest.pagingk.restful.mos;

import androidx.room.Entity;

import java.util.List;

/**
 * @author huanglinqing
 * @project Paging3Demo
 * @date 2020/11/7
 * @desc
 */

public class DataRes {


    /**
     * data : {"curPage":1,"datas":[{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>我们在写代码的时候，有时候很容易使用一个高版本的 API，如果不注意，可能会导致在一些低版本的设备崩溃。<\/p>\r\n<p>因此，我们可以选择引入 lint 在编译时进行检查。<\/p>\r\n<p>今天的问题是？<\/p>\r\n<ol>\r\n<li>应用：如何在打包时，强制开启 New API检查？<\/li>\r\n<li>原理：lint 怎么能知道某个 方法是哪个版本加入的？是有一个汇总的地方维护着这样的方法列表吗？<\/li>\r\n<li>原理：即使有这样的一个列表，lint是怎么扫描每一个方法调用的？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":15949,"link":"https://www.wanandroid.com/wenda/show/15949","niceDate":"2020-11-02 00:15","niceShareDate":"2020-11-02 00:14","origin":"","prefix":"","projectLink":"","publishTime":1604247343000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1604247264000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | Call requires API level 23 (current min is 14) 扫描出来的原理是？","type":1,"userId":2,"visible":1,"zan":4},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>先来看一段代码：<\/p>\r\n<pre><code>final class A {\r\n    String selfIntroduction() {\r\n        return &quot;I&#39;m A&quot;;\r\n    }\r\n}\r\n\r\nclass B {\r\n    String selfIntroduction() {\r\n        return &quot;I&#39;m B&quot;;\r\n    }\r\n}\r\n\r\nclass Test {\r\n    public final A a = new A();\r\n}\r\n<\/code><\/pre>\r\n<p><strong>问题来了：<\/strong><br><strong>1. Test.<code>a<\/code>能被替换吗？<\/strong><\/p>\r\n<p><strong>2. Test.<code>a<\/code>能被替换成B对象的实例吗？<\/strong><\/p>\r\n<p><strong>3. 如果问题2成立，在成功替换对象之后，调用Test.a.<code>selfIntroduction<\/code>方法，返回的是什么？ 为什么会这样？<\/strong><\/p>\r\n<p>把代码稍微改一下：<\/p>\r\n<pre><code>final class A {\r\n    String selfIntroduction = &quot;I&#39;m A&quot;;\r\n\r\n    String selfIntroduction() {\r\n        return selfIntroduction;\r\n    }\r\n}\r\n\r\nclass B {\r\n    String selfIntroduction = &quot;I&#39;m B&quot;;\r\n\r\n    String selfIntroduction() {\r\n        return selfIntroduction;\r\n    }\r\n}\r\n\r\nclass Test {\r\n    public final A a = new A();\r\n}\r\n<\/code><\/pre>\r\n<p><strong>4. 在成功替换对象之后，调用Test.a.<code>selfIntroduction<\/code>方法，返回的是什么？ 为什么？<\/strong><\/p>\r\n<p>再把代码改一下：<\/p>\r\n<pre><code>final class A {\r\n    String selfIntroduction = &quot;I&#39;m A&quot;;\r\n\r\n    String selfIntroduction() {\r\n        return selfIntroduction;\r\n    }\r\n}\r\n\r\nclass B {\r\n    String selfIntroduction = &quot;I&#39;m B&quot;;\r\n}\r\n\r\nclass Test {\r\n    public final A a = new A();\r\n}\r\n<\/code><\/pre>\r\n<p><strong>5. 在成功替换对象之后，调用Test.a.<code>selfIntroduction<\/code>方法，会报错吗？ 为什么？<\/strong><\/p>\r\n<p>继续改一下代码：<\/p>\r\n<pre><code>final class A {\r\n    String selfIntroduction = &quot;I&#39;m A&quot;;\r\n\r\n    String selfIntroduction() {\r\n        return selfIntroduction;\r\n    }\r\n}\r\n\r\nclass B {\r\n    String fakeSelfIntroduction = &quot;I&#39;m fake B&quot;;\r\n    String selfIntroduction = &quot;I&#39;m B&quot;;\r\n}\r\n\r\nclass Test {\r\n    public final A a = new A();\r\n}\r\n<\/code><\/pre>\r\n<p><strong>6. 在成功替换对象之后，调用Test.a.<code>selfIntroduction<\/code>方法，会报错吗？ 如果不会报错，返回值是什么？ 为什么会这样？<\/strong><\/p>\r\n<p>再改一次代码吧：<\/p>\r\n<pre><code>final class A {\r\n    String selfIntroduction = &quot;I&#39;m A&quot;;\r\n\r\n    String selfIntroduction() {\r\n        return selfIntroduction;\r\n    }\r\n}\r\n\r\nclass B {\r\n    int i = 1;\r\n    String fakeSelfIntroduction = &quot;I&#39;m Fake B&quot;;\r\n    String selfIntroduction = &quot;I&#39;m B&quot;;\r\n}\r\n\r\nclass Test {\r\n    public final A a = new A();\r\n}\r\n<\/code><\/pre>\r\n<p><strong>7. 在成功替换对象之后，调用Test.a.<code>selfIntroduction<\/code>方法，会报错吗？ 为什么？<\/strong><\/p>","descMd":"","envelopePic":"","fresh":false,"id":15797,"link":"https://www.wanandroid.com/wenda/show/15797","niceDate":"2020-11-02 00:16","niceShareDate":"2020-10-19 23:57","origin":"","prefix":"","projectLink":"","publishTime":1604247367000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1603123027000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 当Unsafe遇上final，超神奇的事情发生了？","type":0,"userId":2,"visible":1,"zan":3},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>很多时候我们讨论属性动画与之前的View Animation之间的区别，很多同学都会说：<\/p>\r\n<p>\u201c属性动画在移动后，仍然可以响应用户的点击\u201d<\/p>\r\n<p>原因可以看这篇：<a href=\"https://www.wanandroid.com/wenda/show/8644\">每日一问 | 为什么属性动画移动一个控件后，目标位置仍然能响应用户事件？<\/a><\/p>\r\n<p>了解的同学应该清楚，能响应只是在事件分发的时候，做了一下坐标映射。<\/p>\r\n<p>今天，我们讨论另一个区别，就是当属性动画与硬件加速配合时，会摩擦出什么火花？<\/p>\r\n<p>看一个示例：<\/p>\r\n<pre><code>MyTextView tv =view.findViewById( R.id.tv_name);\r\n        tv.setOnClickListener(new View.OnClickListener() {\r\n            @Override\r\n            public void onClick(View v) {\r\n                ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 300).setDuration(2000);\r\n                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {\r\n                    @Override\r\n                    public void onAnimationUpdate(ValueAnimator animation) {\r\n                        v.setTranslationY((int) animation.getAnimatedValue());\r\n                    }\r\n                });\r\n                valueAnimator.start();\r\n            }\r\n        });\r\n<\/code><\/pre><p>很简答，我们自定义一个TextView，点击的时候，对它做一点往下的移动。<\/p>\r\n<p>布局文件：<\/p>\r\n<pre><code>&lt;?xml version=&quot;1.0&quot; encoding=&quot;utf-8&quot;?&gt;\r\n&lt;com.example.zhanghongyang.kotlinlearn.view.MyFrameLayout \r\n    xmlns:android=&quot;http://schemas.android.com/apk/res/android&quot;\r\n    android:layout_width=&quot;match_parent&quot;\r\n    android:layout_height=&quot;match_parent&quot;&gt;\r\n\r\n    &lt;com.example.zhanghongyang.kotlinlearn.view.MyTextView\r\n        android:id=&quot;@+id/tv_name&quot;\r\n        android:layout_width=&quot;wrap_content&quot;\r\n        android:layout_height=&quot;wrap_content&quot;\r\n        android:text=&quot;hello&quot; /&gt;\r\n\r\n&lt;/com.example.zhanghongyang.kotlinlearn.view.MyFrameLayout&gt;\r\n<\/code><\/pre><p>当<strong>硬件加速<\/strong>开启时（默认就是开启的）:<\/p>\r\n<ol>\r\n<li>动画执行过程中：MyTextView的draw方法会回调吗？<\/li>\r\n<li>动画执行过程中：MyFrameLayout的dispatchDraw方法会回调吗？<\/li>\r\n<\/ol>\r\n<p>当<strong>硬件加速<\/strong>关闭时:<\/p>\r\n<ol>\r\n<li>动画执行过程中：MyTextView的draw方法会回调吗？<\/li>\r\n<li>动画执行过程中：MyFrameLayout的dispatchDraw方法会回调吗？<\/li>\r\n<\/ol>\r\n<p>如果两者有差异：<\/p>\r\n<ol>\r\n<li><strong>v.setTranslationY<\/strong>在开启和不开启硬件加速过程中，执行的逻辑到底有什么变化？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":15582,"link":"https://wanandroid.com/wenda/show/15582","niceDate":"2020-10-26 23:45","niceShareDate":"2020-10-05 11:21","origin":"","prefix":"","projectLink":"","publishTime":1603727114000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1601868088000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 属性动画与硬件加速的相遇，不是你想的那么简单？","type":0,"userId":2,"visible":1,"zan":11},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>每当大家有在编译期间有修改字节码的需求，第一反应应该就是使用 Transform 吧，我们知道每个 Transform 都有它的输入、输出，问几个问题：<\/p>\r\n<ol>\r\n<li>在编译过程中，有哪些\u201c系统Transform\u201d执行？<\/li>\r\n<li>自定义 Transform和其他系统Transform执行的顺序是怎么样的？<\/li>\r\n<li>Transform和 gradle task的关系是怎么样的？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":15215,"link":"https://www.wanandroid.com/wenda/show/15215","niceDate":"2020-10-26 23:45","niceShareDate":"2020-09-12 18:32","origin":"","prefix":"","projectLink":"","publishTime":1603727102000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1599906744000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 玩转 Gradle，可不能不熟悉 Transform，那么，我要开始问了。","type":0,"userId":2,"visible":1,"zan":35},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>RecyclerView$Adapter 的：<\/p>\r\n<pre><code>setHasStableIds(boolean)\r\n<\/code><\/pre><ol>\r\n<li>在什么场景下我们会考虑设置为true?<\/li>\r\n<li>设置为true会带来什么好处？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":15514,"link":"https://wanandroid.com/wenda/show/15514","niceDate":"2020-10-26 23:44","niceShareDate":"2020-10-03 11:43","origin":"","prefix":"","projectLink":"","publishTime":1603727094000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1601696580000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 关于 RecyclerView$Adapter setHasStableIds(boolean)的一切","type":0,"userId":2,"visible":1,"zan":15},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>更近实在是太忙了，抽空更新一问。<\/p>\r\n<p>想到一个非常有意思的问题：<\/p>\r\n<p>如果 app 启动了一个 Activity，那么在这个 Activity 展示的情况下，问题来了：<\/p>\r\n<p>1.上述场景背后至少有多少个线程？<br>2.每个线程具体的作用是什么？<\/p>","descMd":"","envelopePic":"","fresh":false,"id":15188,"link":"https://www.wanandroid.com/wenda/show/15188","niceDate":"2020-10-12 00:47","niceShareDate":"2020-09-09 23:54","origin":"","prefix":"","projectLink":"","publishTime":1602434832000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1599666870000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 启动了Activity 的 app 至少有几个线程？","type":0,"userId":2,"visible":1,"zan":22},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>上次我们问了：<\/p>\r\n<p><a href=\"https://wanandroid.com/wenda/show/14941\">每日一问 | Java 泛型与接口碰撞出的火花！<\/a><\/p>\r\n<p>不少同学认识到了，<strong>「桥接方法」解决了实现类与接口参数类型不匹配的问题。<\/strong><\/p>\r\n<p>那么我们再来看一个例子：<\/p>\r\n<pre><code>interface TestInter&lt;T&gt; {\r\n\r\n    T getName(String name);\r\n\r\n}\r\n<\/code><\/pre><p>实现类：<\/p>\r\n<pre><code>class TestImpl implements TestInter&lt;String&gt; {\r\n    @Override\r\n    public String getName(String name) {\r\n        return null;\r\n    }\r\n}\r\n<\/code><\/pre><p>这次猜我的关注点在哪？<\/p>\r\n<p>我们反编译一下TestImpl：<\/p>\r\n<pre><code>public java.lang.String getName(java.lang.String);\r\n  descriptor: (Ljava/lang/String;)Ljava/lang/String;\r\n  flags: ACC_PUBLIC\r\n\r\n\r\npublic java.lang.Object getName(java.lang.String);\r\n  descriptor: (Ljava/lang/String;)Ljava/lang/Object;\r\n  flags: ACC_PUBLIC, ACC_BRIDGE, ACC_SYNTHETIC\r\n<\/code><\/pre><p>又看到了ACC_BRIDGE，ACC_SYNTHETIC，已知知识。<\/p>\r\n<p>可以看到生成的两个方法，我们转成Java的展现形式：<\/p>\r\n<ol>\r\n<li>String getName(String str)<\/li>\r\n<li>Object getName(String str);<\/li>\r\n<\/ol>\r\n<p>有没有觉得奇怪？<\/p>\r\n<p>我贴张图，你就能看明白了：<\/p>\r\n<p><img src=\"https://wanandroid.com/blogimgs/9d06cc96-4343-4e9c-83f0-a448a6e3bbc1.png\" alt><\/p>\r\n<p>这两个方法的方法名，参数均相同，只有返回值不同，在我们Java平时编写中是不允许的。<\/p>\r\n<p>问题来了：<\/p>\r\n<ol>\r\n<li>为何在这个场景下，允许「一个类中有方法名，参数均相同，只有返回值不同」两个方法？<\/li>\r\n<li>既然class文件有两个getName，那么我们调用getName时，是如何确定调用的哪个方法呢？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":14990,"link":"https://wanandroid.com/wenda/show/14990","niceDate":"2020-10-03 11:43","niceShareDate":"2020-08-26 21:08","origin":"","prefix":"","projectLink":"","publishTime":1601696612000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1598447321000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 |  当JVM遇到桥接方法，暴露出了什么？","type":0,"userId":2,"visible":1,"zan":8},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>之前我们问过：<\/p>\r\n<p><a href=\"https://wanandroid.com/wenda/show/14738\">每日一问 Java编译器背后干了多少活 之 「内部类构造」<\/a><\/p>\r\n<p>提到了isSynthetic，<strong>注意今天的问题也是个类似的问题。<\/strong><\/p>\r\n<p>首先我们编写个接口：<\/p>\r\n<pre><code>interface Animal&lt;T&gt;{\r\n    void test(T t);\r\n}\r\n<\/code><\/pre><p>这个接口有个实现类：<\/p>\r\n<pre><code>class Dog implements Animal&lt;String&gt;{\r\n\r\n    @override\r\n    public void test(String str){\r\n    }\r\n}\r\n<\/code><\/pre><p>符合我们平时的写法对吧。<\/p>\r\n<p>但是你仔细推敲一下：<\/p>\r\n<p>接口 Animal 类的泛型，在编译成 class 后，会经历泛型擦除，会变成这样：<\/p>\r\n<pre><code>interface Animal{\r\n    void test(Object obj);\r\n}\r\n<\/code><\/pre><p>而实现类Dog里面有个方法<code>test(String str)<\/code>，注意<strong>这个方法和接口类的方法参数并不一致<\/strong>。<\/p>\r\n<p>那么也就是说，<strong>并没有实现接口中的方法。<\/strong><\/p>\r\n<p>但是，接口的方法，实现类是必须实现的。<\/p>\r\n<p>问题来了：<\/p>\r\n<ul>\r\n<li>为何不报错呢？<\/li>\r\n<li>除了这个场景，编译期间还有哪里有类似的处理方式么？(可不回答)<\/li>\r\n<\/ul>","descMd":"","envelopePic":"","fresh":false,"id":14941,"link":"https://wanandroid.com/wenda/show/14941","niceDate":"2020-09-09 23:54","niceShareDate":"2020-08-23 23:53","origin":"","prefix":"","projectLink":"","publishTime":1599666893000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1598198007000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问  | Java 泛型与接口碰撞出的火花！","type":0,"userId":2,"visible":0,"zan":10},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>之前写代码，需要在一些特殊时机做一些事情，例如释放内存等，特殊时机包含：<\/p>\r\n<ol>\r\n<li>应用退出（用户back 退出，没有任何 Activity 了，但进程还存活的情况）<\/li>\r\n<li>应用 Home 按键置于后台<\/li>\r\n<\/ol>\r\n<p>问题来了，怎么方便的判断这两种时机呢？<\/p>\r\n<p><strong>注意：需要考虑屏幕旋转异常情况。<\/strong><\/p>","descMd":"","envelopePic":"","fresh":false,"id":14774,"link":"https://wanandroid.com/wenda/show/14774","niceDate":"2020-08-26 21:11","niceShareDate":"2020-08-16 19:55","origin":"","prefix":"","projectLink":"","publishTime":1598447504000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1597578943000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 如何判断应用退出，或者到后台了？","type":0,"userId":2,"visible":0,"zan":10},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>我们来看一坨代码：<\/p>\r\n<pre><code>public class A{\r\n\r\n    public static void main(String[] args){\r\n        B b = new A.B();\r\n    }\r\n\r\n\r\n    private static class B{\r\n\r\n    }\r\n\r\n}\r\n<\/code><\/pre><p>上述代码，如果我们执行<code>javac A.java<\/code>编译，会产生几个class文件？<\/p>\r\n<p>我们分析下：<\/p>\r\n<ol>\r\n<li>A.class 肯定有<\/li>\r\n<li>有个静态内部类B，还有个A$B.class<\/li>\r\n<\/ol>\r\n<p>两个。<\/p>\r\n<p>实际运行你会发现有三个：<\/p>\r\n<ol>\r\n<li>A.class<\/li>\r\n<li>A$B.class<\/li>\r\n<li>A$1.class<\/li>\r\n<\/ol>\r\n<p>问题来了：<\/p>\r\n<ol>\r\n<li>为什么会多了个A$1.class?<\/li>\r\n<li>Java里面<code>java.lang.reflect.Method<\/code>以及<code>java.lang.Class<\/code>都有一个<code>isSynthetic()<\/code>方法，是什么意思？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":14738,"link":"https://wanandroid.com/wenda/show/14738","niceDate":"2020-08-23 23:54","niceShareDate":"2020-08-12 10:20","origin":"","prefix":"","projectLink":"","publishTime":1598198050000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1597198816000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问  Java编译器背后干了多少活 之 「内部类构造」","type":0,"userId":2,"visible":0,"zan":14},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>每次新建项目，我们都会生成build.gradle，如果是app模块则会引入：<\/p>\r\n<pre><code>apply plugin: &#39;com.android.application&#39;\r\n<\/code><\/pre><p>如果是lib：<\/p>\r\n<pre><code>apply plugin: &#39;com.android.library&#39;\r\n<\/code><\/pre><p>问题来了：<\/p>\r\n<ol>\r\n<li>apply plugin: &#39;com.android.application&#39;背后的原理是？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":14500,"link":"https://wanandroid.com/wenda/show/14500","niceDate":"2020-08-16 19:56","niceShareDate":"2020-07-26 11:54","origin":"","prefix":"","projectLink":"","publishTime":1597578965000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1595735648000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | apply plugin: &#39;com.android.application&#39; 背后发生了什么？","type":0,"userId":2,"visible":1,"zan":28},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>View 的三大流程：测量、布局、绘制，我想大家应该都烂熟于心。<\/p>\r\n<p>而在绘制阶段，ViewGroup 不光要绘制自身，还需循环绘制其一众子 View，这个绘制策略默认为顺序绘制，即 [0 ~ childCount)。<\/p>\r\n<p>这个默认的策略，有办法调整吗？<\/p>\r\n<p>例如修改成 (childCount ~ 0]，或是修成某个 View 更后绘制。同时又有什么场景需要我们做这样的修改？<\/p>\r\n<p>问题来了：<\/p>\r\n<ol>\r\n<li>这个默认的策略，有办法调整吗？<\/li>\r\n<li>修改了之后，事件分发需要特殊处理吗？还是需要特殊处理。<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":14409,"link":"https://www.wanandroid.com/wenda/show/14409","niceDate":"2020-08-12 10:21","niceShareDate":"2020-07-19 18:07","origin":"","prefix":"","projectLink":"","publishTime":1597198865000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1595153262000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问| View 绘制的一个细节，如何修改 View 绘制的顺序？","type":0,"userId":2,"visible":1,"zan":13},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>相信 ViewGroup 的下面两个方法，大家都不陌生：<\/p>\r\n<ul>\r\n<li>removeView(View view) <\/li>\r\n<li>addView(View child)<\/li>\r\n<\/ul>\r\n<p>其实在 ViewGroup 内部还有两个类似的方法：<\/p>\r\n<ul>\r\n<li>protected void detachViewFromParent(View child) <\/li>\r\n<li>protected void attachViewToParent(View child, int index, LayoutParams params) <\/li>\r\n<\/ul>\r\n<p>问题来了：<\/p>\r\n<ol>\r\n<li>你知道detachViewFromParent/attachViewToParent 这一组方法在哪些控件中被使用中？<\/li>\r\n<li>detachViewFromParent/attachViewToParent 与 removeView/addView 有什么区别呢？<\/li>\r\n<li>detachViewFromParent/attachViewToParent在什么场景下非常适合使用？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":14256,"link":"https://www.wanandroid.com/wenda/show/14256","niceDate":"2020-07-27 01:14","niceShareDate":"2020-07-11 12:07","origin":"","prefix":"","projectLink":"","publishTime":1595783665000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1594440424000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 比 removeView 更轻量的操作，你了解过吗？","type":0,"userId":2,"visible":1,"zan":24},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>谈到 RecyclerView，相信不少同学，张口都能说出它的几级缓存机制：<\/p>\r\n<p>例如：<\/p>\r\n<ul>\r\n<li>一级缓存：mAttachedScrap 和 mChangedScrap <\/li>\r\n<li>二级缓存：mCachedViews <\/li>\r\n<li>三级缓存：ViewCacheExtension <\/li>\r\n<li>四级缓存：RecycledViewPool <\/li>\r\n<\/ul>\r\n<p>然后说怎么用，就是先从 1 级找，然后 2 级...然后4 级，找不到 create ViewHolder。<\/p>\r\n<p>那么，有没有思考过，其实上面几级缓存都属于\u201c内存缓存&quot;，那么这么分级肯定有一定区别。<\/p>\r\n<p>问题来了：<\/p>\r\n<ol>\r\n<li>每一级缓存具体作用是什么？<\/li>\r\n<li>分别在什么场景下会用到哪些缓存呢？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":14222,"link":"https://www.wanandroid.com/wenda/show/14222","niceDate":"2020-07-19 23:56","niceShareDate":"2020-07-08 23:03","origin":"","prefix":"","projectLink":"","publishTime":1595174206000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1594220631000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | RecyclerView的多级缓存机制，每级缓存到底起到什么样的作用？","type":0,"userId":2,"visible":1,"zan":23},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>上一个问答<a href=\"https://wanandroid.com/wenda/show/13775\">每日一问 | Activity与Fragment的那些事，\u201c用起来没问题，我都要走了，你崩溃了？\u201d<\/a> 其实离不开 onSaveInstanceState方法被调用的关系。<\/p>\r\n<p>记得很久以前总记得：\u201conSaveInstanceState 会在系统意外杀死 Activity 时调用，或者横纵屏切换的时候调用\u201d。<\/p>\r\n<p>问题是：<\/p>\r\n<ol>\r\n<li>随着Android SDK版本的变化，这一方法的调用时机有哪些变化？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":13906,"link":"https://wanandroid.com/wenda/show/13906","niceDate":"2020-07-12 23:49","niceShareDate":"2020-06-14 19:33","origin":"","prefix":"","projectLink":"","publishTime":1594568962000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1592134434000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 曾经的记忆中&ldquo;onSaveInstanceState 会在系统意外杀死 Activity 时调用&rdquo;，正确吗？","type":0,"userId":2,"visible":1,"zan":12},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>很多时候，我们会在编译期间，利用 Transform ，通过 javassist,asm等在一些类的方法中插入一些代码逻辑。<\/p>\r\n<p>插入代码之后，如果该类在线上发生异常崩溃，发生异常的方法调用所对应的行号，在Java源文件中还能对应上吗？<\/p>\r\n<p>如果对应不上，那么有什么办法可以让其对应上呢？<\/p>\r\n<blockquote>\r\n<p>本题难度 5 颗星。<\/p>\r\n<\/blockquote>","descMd":"","envelopePic":"","fresh":false,"id":14081,"link":"https://www.wanandroid.com/wenda/show/14081","niceDate":"2020-07-08 23:05","niceShareDate":"2020-06-29 21:26","origin":"","prefix":"","projectLink":"","publishTime":1594220730000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1593437161000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 插桩之后，异常堆栈还能正确的定位到代码行吗？","type":0,"userId":2,"visible":1,"zan":5},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>在 Android 7.0了，引入了「Network Security Configuration」，当时默认配置为信任系统内置证书以及用户安装证书，并且允许访问 HTTP 请求。<\/p>\r\n<p>而在 Android 9.0上，默认只信任系统证书了，并且默认不再允许访问 HTTP 请求，所以在很多适配P文章中，因为无法访问 HTTP 了，无法抓包了，所以我们要引入如下配置：<\/p>\r\n<pre><code>&lt;base-config&gt; \r\n    &lt;trust-anchors&gt;\r\n        &lt;certificates src=&quot;system&quot; /&gt;\r\n                 &lt;certificates src=&quot;user&quot; /&gt; // 信任用户安装证书\r\n    &lt;/trust-anchors&gt;\r\n&lt;/base-config&gt;\r\n<\/code><\/pre><p>问题来了，今天在一个技术群，有人说：<\/p>\r\n<p><strong>高版本的(AndroidP )，需要配置 android:networkSecurityConfig=&quot;@xml/network_security_config&quot;(上面的配置) ，才能抓包<\/strong><\/p>\r\n<ol>\r\n<li>这个结论一定是正确的吗？<\/li>\r\n<li>我在Android P以及以上的设备中不配置networkSecurityConfig，就一定无法抓包吗？<\/li>\r\n<\/ol>\r\n<p>其实之前问过类似的问题，上次没人回答，这次细化到具体场景了。<\/p>","descMd":"","envelopePic":"","fresh":false,"id":13949,"link":"https://www.wanandroid.com/wenda/show/13949","niceDate":"2020-06-29 21:26","niceShareDate":"2020-06-19 11:50","origin":"","prefix":"","projectLink":"","publishTime":1593437188000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1592538623000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | Android P 上，需要配置 network_security_config ，才能抓包，正确吗？","type":0,"userId":2,"visible":1,"zan":3},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<pre><code>public class FragmentTestActivity extends AppCompatActivity {\r\n\r\n    @Override\r\n    protected void onCreate(Bundle savedInstanceState) {\r\n        super.onCreate(savedInstanceState);\r\n        setContentView(R.layout.activity_fragment_test);\r\n\r\n        FragmentManager fm = getSupportFragmentManager();\r\n        Fragment fragment = fm.findFragmentById(R.id.constraint_layout);\r\n\r\n        if (fragment == null) {\r\n            Bundle bundle = new Bundle();\r\n            byte[] imgBytes = new byte[1024 * 1024 * 20];\r\n            bundle.putByteArray(&quot;img_bytes&quot;, imgBytes);\r\n            fragment = new SimpleFragment();\r\n            fragment.setArguments(bundle);\r\n            fm.beginTransaction().add(R.id.constraint_layout, fragment).commitNow();\r\n        }\r\n\r\n\r\n    }\r\n}\r\n<\/code><\/pre><p>大家可以看下上述代码，通过bundle传递了20M到fragment。<\/p>\r\n<p>问题为：<\/p>\r\n<ol>\r\n<li>上述代码该Activity启动时会崩溃吗？<\/li>\r\n<li>点击home，切换到桌面时会崩溃吗？<\/li>\r\n<li>如果上述发生崩溃，为什么，尝试从源码中进行分析；<\/li>\r\n<li>除了携带较大的数据，还有携带什么样的数据会发生崩溃？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":13775,"link":"https://wanandroid.com/wenda/show/13775","niceDate":"2020-06-22 00:39","niceShareDate":"2020-06-05 14:26","origin":"","prefix":"","projectLink":"","publishTime":1592757567000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1591338370000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | Activity与Fragment的那些事，&ldquo;用起来没问题，我都要走了，你崩溃了？&rdquo;","type":0,"userId":2,"visible":1,"zan":23},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<pre><code>public class Student  {\r\n    private Student() {\r\n        throw new IllegalArgumentException(&quot;can not create.&quot;);\r\n    }\r\n    public String name;\r\n}\r\n<\/code><\/pre><p>如何使用Java代码创建一个Student对象？<\/p>","descMd":"","envelopePic":"","fresh":false,"id":13785,"link":"https://wanandroid.com/wenda/show/13785","niceDate":"2020-06-09 23:17","niceShareDate":"2020-06-05 20:46","origin":"","prefix":"","projectLink":"","publishTime":1591715856000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1591361207000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | Java里面还能这么创建对象？","type":0,"userId":2,"visible":1,"zan":12},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>大家在开发工程中，有没有发现，有的自定义控件可以正常预览，有的就不行？<\/p>\r\n<p>那么针对这个情况，问题来了：<\/p>\r\n<ol>\r\n<li>自定义控件是如何做到预览的，AS 中我们的 View 的代码执行了？<\/li>\r\n<li>有些自定义控件不能预览的原因是？我们该怎么做，让其尽可能做到可预览状态？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":13701,"link":"https://www.wanandroid.com/wenda/show/13701","niceDate":"2020-06-07 20:55","niceShareDate":"2020-06-01 00:54","origin":"","prefix":"","projectLink":"","publishTime":1591534548000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1590944069000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 自定义控件无法预览该怎么办？","type":0,"userId":2,"visible":0,"zan":5},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>在之前的问答中：<\/p>\r\n<p><a href=\"https://wanandroid.com/wenda/show/12424\">每日一问 ViewPager 这个流传广泛的写法，其实是有问题的！<\/a><\/p>\r\n<p>我们指出了一个ViewPager 的错误写法，提到了根本原因是 Fragment存在恢复机制。<\/p>\r\n<p>那么我们继续深入讨论一下：<\/p>\r\n<ol>\r\n<li>一般情况下，我们讨论 Activity重建 Fragment恢复，都是以 Activity 旋转距离，其实还有个 case，就是进程由于内存不足被杀死，返回这个 app，Activity 也会被重建，这种情况下Fragment 也会被恢复吗（这个可以通过 app 授权一个相机权限，然后打开某个 activity，再去设置页关闭相机权限，切回 app ，就能模拟进程杀死activity 重建）？<\/li>\r\n<li>Fragment 恢复是真的和重建前使用的是同一个对象吗？<\/li>\r\n<li>是如何做到恢复的？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":12574,"link":"https://www.wanandroid.com/wenda/show/12574","niceDate":"2020-06-07 09:01","niceShareDate":"2020-03-25 00:56","origin":"","prefix":"","projectLink":"","publishTime":1591491714000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1585068987000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | Fragment 是如何被存储与恢复的？  有更新","type":0,"userId":2,"visible":1,"zan":22}],"offset":0,"over":false,"pageCount":6,"size":21,"total":119}
     * errorCode : 0
     * errorMsg :
     */

    private DataBean data;
    private int errorCode;
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    public static class DataBean {
        /**
         * curPage : 1
         * datas : [{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>我们在写代码的时候，有时候很容易使用一个高版本的 API，如果不注意，可能会导致在一些低版本的设备崩溃。<\/p>\r\n<p>因此，我们可以选择引入 lint 在编译时进行检查。<\/p>\r\n<p>今天的问题是？<\/p>\r\n<ol>\r\n<li>应用：如何在打包时，强制开启 New API检查？<\/li>\r\n<li>原理：lint 怎么能知道某个 方法是哪个版本加入的？是有一个汇总的地方维护着这样的方法列表吗？<\/li>\r\n<li>原理：即使有这样的一个列表，lint是怎么扫描每一个方法调用的？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":15949,"link":"https://www.wanandroid.com/wenda/show/15949","niceDate":"2020-11-02 00:15","niceShareDate":"2020-11-02 00:14","origin":"","prefix":"","projectLink":"","publishTime":1604247343000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1604247264000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | Call requires API level 23 (current min is 14) 扫描出来的原理是？","type":1,"userId":2,"visible":1,"zan":4},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>先来看一段代码：<\/p>\r\n<pre><code>final class A {\r\n    String selfIntroduction() {\r\n        return &quot;I&#39;m A&quot;;\r\n    }\r\n}\r\n\r\nclass B {\r\n    String selfIntroduction() {\r\n        return &quot;I&#39;m B&quot;;\r\n    }\r\n}\r\n\r\nclass Test {\r\n    public final A a = new A();\r\n}\r\n<\/code><\/pre>\r\n<p><strong>问题来了：<\/strong><br><strong>1. Test.<code>a<\/code>能被替换吗？<\/strong><\/p>\r\n<p><strong>2. Test.<code>a<\/code>能被替换成B对象的实例吗？<\/strong><\/p>\r\n<p><strong>3. 如果问题2成立，在成功替换对象之后，调用Test.a.<code>selfIntroduction<\/code>方法，返回的是什么？ 为什么会这样？<\/strong><\/p>\r\n<p>把代码稍微改一下：<\/p>\r\n<pre><code>final class A {\r\n    String selfIntroduction = &quot;I&#39;m A&quot;;\r\n\r\n    String selfIntroduction() {\r\n        return selfIntroduction;\r\n    }\r\n}\r\n\r\nclass B {\r\n    String selfIntroduction = &quot;I&#39;m B&quot;;\r\n\r\n    String selfIntroduction() {\r\n        return selfIntroduction;\r\n    }\r\n}\r\n\r\nclass Test {\r\n    public final A a = new A();\r\n}\r\n<\/code><\/pre>\r\n<p><strong>4. 在成功替换对象之后，调用Test.a.<code>selfIntroduction<\/code>方法，返回的是什么？ 为什么？<\/strong><\/p>\r\n<p>再把代码改一下：<\/p>\r\n<pre><code>final class A {\r\n    String selfIntroduction = &quot;I&#39;m A&quot;;\r\n\r\n    String selfIntroduction() {\r\n        return selfIntroduction;\r\n    }\r\n}\r\n\r\nclass B {\r\n    String selfIntroduction = &quot;I&#39;m B&quot;;\r\n}\r\n\r\nclass Test {\r\n    public final A a = new A();\r\n}\r\n<\/code><\/pre>\r\n<p><strong>5. 在成功替换对象之后，调用Test.a.<code>selfIntroduction<\/code>方法，会报错吗？ 为什么？<\/strong><\/p>\r\n<p>继续改一下代码：<\/p>\r\n<pre><code>final class A {\r\n    String selfIntroduction = &quot;I&#39;m A&quot;;\r\n\r\n    String selfIntroduction() {\r\n        return selfIntroduction;\r\n    }\r\n}\r\n\r\nclass B {\r\n    String fakeSelfIntroduction = &quot;I&#39;m fake B&quot;;\r\n    String selfIntroduction = &quot;I&#39;m B&quot;;\r\n}\r\n\r\nclass Test {\r\n    public final A a = new A();\r\n}\r\n<\/code><\/pre>\r\n<p><strong>6. 在成功替换对象之后，调用Test.a.<code>selfIntroduction<\/code>方法，会报错吗？ 如果不会报错，返回值是什么？ 为什么会这样？<\/strong><\/p>\r\n<p>再改一次代码吧：<\/p>\r\n<pre><code>final class A {\r\n    String selfIntroduction = &quot;I&#39;m A&quot;;\r\n\r\n    String selfIntroduction() {\r\n        return selfIntroduction;\r\n    }\r\n}\r\n\r\nclass B {\r\n    int i = 1;\r\n    String fakeSelfIntroduction = &quot;I&#39;m Fake B&quot;;\r\n    String selfIntroduction = &quot;I&#39;m B&quot;;\r\n}\r\n\r\nclass Test {\r\n    public final A a = new A();\r\n}\r\n<\/code><\/pre>\r\n<p><strong>7. 在成功替换对象之后，调用Test.a.<code>selfIntroduction<\/code>方法，会报错吗？ 为什么？<\/strong><\/p>","descMd":"","envelopePic":"","fresh":false,"id":15797,"link":"https://www.wanandroid.com/wenda/show/15797","niceDate":"2020-11-02 00:16","niceShareDate":"2020-10-19 23:57","origin":"","prefix":"","projectLink":"","publishTime":1604247367000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1603123027000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 当Unsafe遇上final，超神奇的事情发生了？","type":0,"userId":2,"visible":1,"zan":3},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>很多时候我们讨论属性动画与之前的View Animation之间的区别，很多同学都会说：<\/p>\r\n<p>\u201c属性动画在移动后，仍然可以响应用户的点击\u201d<\/p>\r\n<p>原因可以看这篇：<a href=\"https://www.wanandroid.com/wenda/show/8644\">每日一问 | 为什么属性动画移动一个控件后，目标位置仍然能响应用户事件？<\/a><\/p>\r\n<p>了解的同学应该清楚，能响应只是在事件分发的时候，做了一下坐标映射。<\/p>\r\n<p>今天，我们讨论另一个区别，就是当属性动画与硬件加速配合时，会摩擦出什么火花？<\/p>\r\n<p>看一个示例：<\/p>\r\n<pre><code>MyTextView tv =view.findViewById( R.id.tv_name);\r\n        tv.setOnClickListener(new View.OnClickListener() {\r\n            @Override\r\n            public void onClick(View v) {\r\n                ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 300).setDuration(2000);\r\n                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {\r\n                    @Override\r\n                    public void onAnimationUpdate(ValueAnimator animation) {\r\n                        v.setTranslationY((int) animation.getAnimatedValue());\r\n                    }\r\n                });\r\n                valueAnimator.start();\r\n            }\r\n        });\r\n<\/code><\/pre><p>很简答，我们自定义一个TextView，点击的时候，对它做一点往下的移动。<\/p>\r\n<p>布局文件：<\/p>\r\n<pre><code>&lt;?xml version=&quot;1.0&quot; encoding=&quot;utf-8&quot;?&gt;\r\n&lt;com.example.zhanghongyang.kotlinlearn.view.MyFrameLayout \r\n    xmlns:android=&quot;http://schemas.android.com/apk/res/android&quot;\r\n    android:layout_width=&quot;match_parent&quot;\r\n    android:layout_height=&quot;match_parent&quot;&gt;\r\n\r\n    &lt;com.example.zhanghongyang.kotlinlearn.view.MyTextView\r\n        android:id=&quot;@+id/tv_name&quot;\r\n        android:layout_width=&quot;wrap_content&quot;\r\n        android:layout_height=&quot;wrap_content&quot;\r\n        android:text=&quot;hello&quot; /&gt;\r\n\r\n&lt;/com.example.zhanghongyang.kotlinlearn.view.MyFrameLayout&gt;\r\n<\/code><\/pre><p>当<strong>硬件加速<\/strong>开启时（默认就是开启的）:<\/p>\r\n<ol>\r\n<li>动画执行过程中：MyTextView的draw方法会回调吗？<\/li>\r\n<li>动画执行过程中：MyFrameLayout的dispatchDraw方法会回调吗？<\/li>\r\n<\/ol>\r\n<p>当<strong>硬件加速<\/strong>关闭时:<\/p>\r\n<ol>\r\n<li>动画执行过程中：MyTextView的draw方法会回调吗？<\/li>\r\n<li>动画执行过程中：MyFrameLayout的dispatchDraw方法会回调吗？<\/li>\r\n<\/ol>\r\n<p>如果两者有差异：<\/p>\r\n<ol>\r\n<li><strong>v.setTranslationY<\/strong>在开启和不开启硬件加速过程中，执行的逻辑到底有什么变化？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":15582,"link":"https://wanandroid.com/wenda/show/15582","niceDate":"2020-10-26 23:45","niceShareDate":"2020-10-05 11:21","origin":"","prefix":"","projectLink":"","publishTime":1603727114000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1601868088000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 属性动画与硬件加速的相遇，不是你想的那么简单？","type":0,"userId":2,"visible":1,"zan":11},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>每当大家有在编译期间有修改字节码的需求，第一反应应该就是使用 Transform 吧，我们知道每个 Transform 都有它的输入、输出，问几个问题：<\/p>\r\n<ol>\r\n<li>在编译过程中，有哪些\u201c系统Transform\u201d执行？<\/li>\r\n<li>自定义 Transform和其他系统Transform执行的顺序是怎么样的？<\/li>\r\n<li>Transform和 gradle task的关系是怎么样的？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":15215,"link":"https://www.wanandroid.com/wenda/show/15215","niceDate":"2020-10-26 23:45","niceShareDate":"2020-09-12 18:32","origin":"","prefix":"","projectLink":"","publishTime":1603727102000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1599906744000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 玩转 Gradle，可不能不熟悉 Transform，那么，我要开始问了。","type":0,"userId":2,"visible":1,"zan":35},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>RecyclerView$Adapter 的：<\/p>\r\n<pre><code>setHasStableIds(boolean)\r\n<\/code><\/pre><ol>\r\n<li>在什么场景下我们会考虑设置为true?<\/li>\r\n<li>设置为true会带来什么好处？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":15514,"link":"https://wanandroid.com/wenda/show/15514","niceDate":"2020-10-26 23:44","niceShareDate":"2020-10-03 11:43","origin":"","prefix":"","projectLink":"","publishTime":1603727094000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1601696580000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 关于 RecyclerView$Adapter setHasStableIds(boolean)的一切","type":0,"userId":2,"visible":1,"zan":15},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>更近实在是太忙了，抽空更新一问。<\/p>\r\n<p>想到一个非常有意思的问题：<\/p>\r\n<p>如果 app 启动了一个 Activity，那么在这个 Activity 展示的情况下，问题来了：<\/p>\r\n<p>1.上述场景背后至少有多少个线程？<br>2.每个线程具体的作用是什么？<\/p>","descMd":"","envelopePic":"","fresh":false,"id":15188,"link":"https://www.wanandroid.com/wenda/show/15188","niceDate":"2020-10-12 00:47","niceShareDate":"2020-09-09 23:54","origin":"","prefix":"","projectLink":"","publishTime":1602434832000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1599666870000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 启动了Activity 的 app 至少有几个线程？","type":0,"userId":2,"visible":1,"zan":22},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>上次我们问了：<\/p>\r\n<p><a href=\"https://wanandroid.com/wenda/show/14941\">每日一问 | Java 泛型与接口碰撞出的火花！<\/a><\/p>\r\n<p>不少同学认识到了，<strong>「桥接方法」解决了实现类与接口参数类型不匹配的问题。<\/strong><\/p>\r\n<p>那么我们再来看一个例子：<\/p>\r\n<pre><code>interface TestInter&lt;T&gt; {\r\n\r\n    T getName(String name);\r\n\r\n}\r\n<\/code><\/pre><p>实现类：<\/p>\r\n<pre><code>class TestImpl implements TestInter&lt;String&gt; {\r\n    @Override\r\n    public String getName(String name) {\r\n        return null;\r\n    }\r\n}\r\n<\/code><\/pre><p>这次猜我的关注点在哪？<\/p>\r\n<p>我们反编译一下TestImpl：<\/p>\r\n<pre><code>public java.lang.String getName(java.lang.String);\r\n  descriptor: (Ljava/lang/String;)Ljava/lang/String;\r\n  flags: ACC_PUBLIC\r\n\r\n\r\npublic java.lang.Object getName(java.lang.String);\r\n  descriptor: (Ljava/lang/String;)Ljava/lang/Object;\r\n  flags: ACC_PUBLIC, ACC_BRIDGE, ACC_SYNTHETIC\r\n<\/code><\/pre><p>又看到了ACC_BRIDGE，ACC_SYNTHETIC，已知知识。<\/p>\r\n<p>可以看到生成的两个方法，我们转成Java的展现形式：<\/p>\r\n<ol>\r\n<li>String getName(String str)<\/li>\r\n<li>Object getName(String str);<\/li>\r\n<\/ol>\r\n<p>有没有觉得奇怪？<\/p>\r\n<p>我贴张图，你就能看明白了：<\/p>\r\n<p><img src=\"https://wanandroid.com/blogimgs/9d06cc96-4343-4e9c-83f0-a448a6e3bbc1.png\" alt><\/p>\r\n<p>这两个方法的方法名，参数均相同，只有返回值不同，在我们Java平时编写中是不允许的。<\/p>\r\n<p>问题来了：<\/p>\r\n<ol>\r\n<li>为何在这个场景下，允许「一个类中有方法名，参数均相同，只有返回值不同」两个方法？<\/li>\r\n<li>既然class文件有两个getName，那么我们调用getName时，是如何确定调用的哪个方法呢？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":14990,"link":"https://wanandroid.com/wenda/show/14990","niceDate":"2020-10-03 11:43","niceShareDate":"2020-08-26 21:08","origin":"","prefix":"","projectLink":"","publishTime":1601696612000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1598447321000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 |  当JVM遇到桥接方法，暴露出了什么？","type":0,"userId":2,"visible":1,"zan":8},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>之前我们问过：<\/p>\r\n<p><a href=\"https://wanandroid.com/wenda/show/14738\">每日一问 Java编译器背后干了多少活 之 「内部类构造」<\/a><\/p>\r\n<p>提到了isSynthetic，<strong>注意今天的问题也是个类似的问题。<\/strong><\/p>\r\n<p>首先我们编写个接口：<\/p>\r\n<pre><code>interface Animal&lt;T&gt;{\r\n    void test(T t);\r\n}\r\n<\/code><\/pre><p>这个接口有个实现类：<\/p>\r\n<pre><code>class Dog implements Animal&lt;String&gt;{\r\n\r\n    @override\r\n    public void test(String str){\r\n    }\r\n}\r\n<\/code><\/pre><p>符合我们平时的写法对吧。<\/p>\r\n<p>但是你仔细推敲一下：<\/p>\r\n<p>接口 Animal 类的泛型，在编译成 class 后，会经历泛型擦除，会变成这样：<\/p>\r\n<pre><code>interface Animal{\r\n    void test(Object obj);\r\n}\r\n<\/code><\/pre><p>而实现类Dog里面有个方法<code>test(String str)<\/code>，注意<strong>这个方法和接口类的方法参数并不一致<\/strong>。<\/p>\r\n<p>那么也就是说，<strong>并没有实现接口中的方法。<\/strong><\/p>\r\n<p>但是，接口的方法，实现类是必须实现的。<\/p>\r\n<p>问题来了：<\/p>\r\n<ul>\r\n<li>为何不报错呢？<\/li>\r\n<li>除了这个场景，编译期间还有哪里有类似的处理方式么？(可不回答)<\/li>\r\n<\/ul>","descMd":"","envelopePic":"","fresh":false,"id":14941,"link":"https://wanandroid.com/wenda/show/14941","niceDate":"2020-09-09 23:54","niceShareDate":"2020-08-23 23:53","origin":"","prefix":"","projectLink":"","publishTime":1599666893000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1598198007000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问  | Java 泛型与接口碰撞出的火花！","type":0,"userId":2,"visible":0,"zan":10},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>之前写代码，需要在一些特殊时机做一些事情，例如释放内存等，特殊时机包含：<\/p>\r\n<ol>\r\n<li>应用退出（用户back 退出，没有任何 Activity 了，但进程还存活的情况）<\/li>\r\n<li>应用 Home 按键置于后台<\/li>\r\n<\/ol>\r\n<p>问题来了，怎么方便的判断这两种时机呢？<\/p>\r\n<p><strong>注意：需要考虑屏幕旋转异常情况。<\/strong><\/p>","descMd":"","envelopePic":"","fresh":false,"id":14774,"link":"https://wanandroid.com/wenda/show/14774","niceDate":"2020-08-26 21:11","niceShareDate":"2020-08-16 19:55","origin":"","prefix":"","projectLink":"","publishTime":1598447504000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1597578943000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 如何判断应用退出，或者到后台了？","type":0,"userId":2,"visible":0,"zan":10},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>我们来看一坨代码：<\/p>\r\n<pre><code>public class A{\r\n\r\n    public static void main(String[] args){\r\n        B b = new A.B();\r\n    }\r\n\r\n\r\n    private static class B{\r\n\r\n    }\r\n\r\n}\r\n<\/code><\/pre><p>上述代码，如果我们执行<code>javac A.java<\/code>编译，会产生几个class文件？<\/p>\r\n<p>我们分析下：<\/p>\r\n<ol>\r\n<li>A.class 肯定有<\/li>\r\n<li>有个静态内部类B，还有个A$B.class<\/li>\r\n<\/ol>\r\n<p>两个。<\/p>\r\n<p>实际运行你会发现有三个：<\/p>\r\n<ol>\r\n<li>A.class<\/li>\r\n<li>A$B.class<\/li>\r\n<li>A$1.class<\/li>\r\n<\/ol>\r\n<p>问题来了：<\/p>\r\n<ol>\r\n<li>为什么会多了个A$1.class?<\/li>\r\n<li>Java里面<code>java.lang.reflect.Method<\/code>以及<code>java.lang.Class<\/code>都有一个<code>isSynthetic()<\/code>方法，是什么意思？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":14738,"link":"https://wanandroid.com/wenda/show/14738","niceDate":"2020-08-23 23:54","niceShareDate":"2020-08-12 10:20","origin":"","prefix":"","projectLink":"","publishTime":1598198050000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1597198816000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问  Java编译器背后干了多少活 之 「内部类构造」","type":0,"userId":2,"visible":0,"zan":14},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>每次新建项目，我们都会生成build.gradle，如果是app模块则会引入：<\/p>\r\n<pre><code>apply plugin: &#39;com.android.application&#39;\r\n<\/code><\/pre><p>如果是lib：<\/p>\r\n<pre><code>apply plugin: &#39;com.android.library&#39;\r\n<\/code><\/pre><p>问题来了：<\/p>\r\n<ol>\r\n<li>apply plugin: &#39;com.android.application&#39;背后的原理是？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":14500,"link":"https://wanandroid.com/wenda/show/14500","niceDate":"2020-08-16 19:56","niceShareDate":"2020-07-26 11:54","origin":"","prefix":"","projectLink":"","publishTime":1597578965000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1595735648000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | apply plugin: &#39;com.android.application&#39; 背后发生了什么？","type":0,"userId":2,"visible":1,"zan":28},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>View 的三大流程：测量、布局、绘制，我想大家应该都烂熟于心。<\/p>\r\n<p>而在绘制阶段，ViewGroup 不光要绘制自身，还需循环绘制其一众子 View，这个绘制策略默认为顺序绘制，即 [0 ~ childCount)。<\/p>\r\n<p>这个默认的策略，有办法调整吗？<\/p>\r\n<p>例如修改成 (childCount ~ 0]，或是修成某个 View 更后绘制。同时又有什么场景需要我们做这样的修改？<\/p>\r\n<p>问题来了：<\/p>\r\n<ol>\r\n<li>这个默认的策略，有办法调整吗？<\/li>\r\n<li>修改了之后，事件分发需要特殊处理吗？还是需要特殊处理。<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":14409,"link":"https://www.wanandroid.com/wenda/show/14409","niceDate":"2020-08-12 10:21","niceShareDate":"2020-07-19 18:07","origin":"","prefix":"","projectLink":"","publishTime":1597198865000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1595153262000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问| View 绘制的一个细节，如何修改 View 绘制的顺序？","type":0,"userId":2,"visible":1,"zan":13},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>相信 ViewGroup 的下面两个方法，大家都不陌生：<\/p>\r\n<ul>\r\n<li>removeView(View view) <\/li>\r\n<li>addView(View child)<\/li>\r\n<\/ul>\r\n<p>其实在 ViewGroup 内部还有两个类似的方法：<\/p>\r\n<ul>\r\n<li>protected void detachViewFromParent(View child) <\/li>\r\n<li>protected void attachViewToParent(View child, int index, LayoutParams params) <\/li>\r\n<\/ul>\r\n<p>问题来了：<\/p>\r\n<ol>\r\n<li>你知道detachViewFromParent/attachViewToParent 这一组方法在哪些控件中被使用中？<\/li>\r\n<li>detachViewFromParent/attachViewToParent 与 removeView/addView 有什么区别呢？<\/li>\r\n<li>detachViewFromParent/attachViewToParent在什么场景下非常适合使用？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":14256,"link":"https://www.wanandroid.com/wenda/show/14256","niceDate":"2020-07-27 01:14","niceShareDate":"2020-07-11 12:07","origin":"","prefix":"","projectLink":"","publishTime":1595783665000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1594440424000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 比 removeView 更轻量的操作，你了解过吗？","type":0,"userId":2,"visible":1,"zan":24},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>谈到 RecyclerView，相信不少同学，张口都能说出它的几级缓存机制：<\/p>\r\n<p>例如：<\/p>\r\n<ul>\r\n<li>一级缓存：mAttachedScrap 和 mChangedScrap <\/li>\r\n<li>二级缓存：mCachedViews <\/li>\r\n<li>三级缓存：ViewCacheExtension <\/li>\r\n<li>四级缓存：RecycledViewPool <\/li>\r\n<\/ul>\r\n<p>然后说怎么用，就是先从 1 级找，然后 2 级...然后4 级，找不到 create ViewHolder。<\/p>\r\n<p>那么，有没有思考过，其实上面几级缓存都属于\u201c内存缓存&quot;，那么这么分级肯定有一定区别。<\/p>\r\n<p>问题来了：<\/p>\r\n<ol>\r\n<li>每一级缓存具体作用是什么？<\/li>\r\n<li>分别在什么场景下会用到哪些缓存呢？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":14222,"link":"https://www.wanandroid.com/wenda/show/14222","niceDate":"2020-07-19 23:56","niceShareDate":"2020-07-08 23:03","origin":"","prefix":"","projectLink":"","publishTime":1595174206000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1594220631000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | RecyclerView的多级缓存机制，每级缓存到底起到什么样的作用？","type":0,"userId":2,"visible":1,"zan":23},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>上一个问答<a href=\"https://wanandroid.com/wenda/show/13775\">每日一问 | Activity与Fragment的那些事，\u201c用起来没问题，我都要走了，你崩溃了？\u201d<\/a> 其实离不开 onSaveInstanceState方法被调用的关系。<\/p>\r\n<p>记得很久以前总记得：\u201conSaveInstanceState 会在系统意外杀死 Activity 时调用，或者横纵屏切换的时候调用\u201d。<\/p>\r\n<p>问题是：<\/p>\r\n<ol>\r\n<li>随着Android SDK版本的变化，这一方法的调用时机有哪些变化？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":13906,"link":"https://wanandroid.com/wenda/show/13906","niceDate":"2020-07-12 23:49","niceShareDate":"2020-06-14 19:33","origin":"","prefix":"","projectLink":"","publishTime":1594568962000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1592134434000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 曾经的记忆中&ldquo;onSaveInstanceState 会在系统意外杀死 Activity 时调用&rdquo;，正确吗？","type":0,"userId":2,"visible":1,"zan":12},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>很多时候，我们会在编译期间，利用 Transform ，通过 javassist,asm等在一些类的方法中插入一些代码逻辑。<\/p>\r\n<p>插入代码之后，如果该类在线上发生异常崩溃，发生异常的方法调用所对应的行号，在Java源文件中还能对应上吗？<\/p>\r\n<p>如果对应不上，那么有什么办法可以让其对应上呢？<\/p>\r\n<blockquote>\r\n<p>本题难度 5 颗星。<\/p>\r\n<\/blockquote>","descMd":"","envelopePic":"","fresh":false,"id":14081,"link":"https://www.wanandroid.com/wenda/show/14081","niceDate":"2020-07-08 23:05","niceShareDate":"2020-06-29 21:26","origin":"","prefix":"","projectLink":"","publishTime":1594220730000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1593437161000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 插桩之后，异常堆栈还能正确的定位到代码行吗？","type":0,"userId":2,"visible":1,"zan":5},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>在 Android 7.0了，引入了「Network Security Configuration」，当时默认配置为信任系统内置证书以及用户安装证书，并且允许访问 HTTP 请求。<\/p>\r\n<p>而在 Android 9.0上，默认只信任系统证书了，并且默认不再允许访问 HTTP 请求，所以在很多适配P文章中，因为无法访问 HTTP 了，无法抓包了，所以我们要引入如下配置：<\/p>\r\n<pre><code>&lt;base-config&gt; \r\n    &lt;trust-anchors&gt;\r\n        &lt;certificates src=&quot;system&quot; /&gt;\r\n                 &lt;certificates src=&quot;user&quot; /&gt; // 信任用户安装证书\r\n    &lt;/trust-anchors&gt;\r\n&lt;/base-config&gt;\r\n<\/code><\/pre><p>问题来了，今天在一个技术群，有人说：<\/p>\r\n<p><strong>高版本的(AndroidP )，需要配置 android:networkSecurityConfig=&quot;@xml/network_security_config&quot;(上面的配置) ，才能抓包<\/strong><\/p>\r\n<ol>\r\n<li>这个结论一定是正确的吗？<\/li>\r\n<li>我在Android P以及以上的设备中不配置networkSecurityConfig，就一定无法抓包吗？<\/li>\r\n<\/ol>\r\n<p>其实之前问过类似的问题，上次没人回答，这次细化到具体场景了。<\/p>","descMd":"","envelopePic":"","fresh":false,"id":13949,"link":"https://www.wanandroid.com/wenda/show/13949","niceDate":"2020-06-29 21:26","niceShareDate":"2020-06-19 11:50","origin":"","prefix":"","projectLink":"","publishTime":1593437188000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1592538623000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | Android P 上，需要配置 network_security_config ，才能抓包，正确吗？","type":0,"userId":2,"visible":1,"zan":3},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<pre><code>public class FragmentTestActivity extends AppCompatActivity {\r\n\r\n    @Override\r\n    protected void onCreate(Bundle savedInstanceState) {\r\n        super.onCreate(savedInstanceState);\r\n        setContentView(R.layout.activity_fragment_test);\r\n\r\n        FragmentManager fm = getSupportFragmentManager();\r\n        Fragment fragment = fm.findFragmentById(R.id.constraint_layout);\r\n\r\n        if (fragment == null) {\r\n            Bundle bundle = new Bundle();\r\n            byte[] imgBytes = new byte[1024 * 1024 * 20];\r\n            bundle.putByteArray(&quot;img_bytes&quot;, imgBytes);\r\n            fragment = new SimpleFragment();\r\n            fragment.setArguments(bundle);\r\n            fm.beginTransaction().add(R.id.constraint_layout, fragment).commitNow();\r\n        }\r\n\r\n\r\n    }\r\n}\r\n<\/code><\/pre><p>大家可以看下上述代码，通过bundle传递了20M到fragment。<\/p>\r\n<p>问题为：<\/p>\r\n<ol>\r\n<li>上述代码该Activity启动时会崩溃吗？<\/li>\r\n<li>点击home，切换到桌面时会崩溃吗？<\/li>\r\n<li>如果上述发生崩溃，为什么，尝试从源码中进行分析；<\/li>\r\n<li>除了携带较大的数据，还有携带什么样的数据会发生崩溃？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":13775,"link":"https://wanandroid.com/wenda/show/13775","niceDate":"2020-06-22 00:39","niceShareDate":"2020-06-05 14:26","origin":"","prefix":"","projectLink":"","publishTime":1592757567000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1591338370000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | Activity与Fragment的那些事，&ldquo;用起来没问题，我都要走了，你崩溃了？&rdquo;","type":0,"userId":2,"visible":1,"zan":23},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<pre><code>public class Student  {\r\n    private Student() {\r\n        throw new IllegalArgumentException(&quot;can not create.&quot;);\r\n    }\r\n    public String name;\r\n}\r\n<\/code><\/pre><p>如何使用Java代码创建一个Student对象？<\/p>","descMd":"","envelopePic":"","fresh":false,"id":13785,"link":"https://wanandroid.com/wenda/show/13785","niceDate":"2020-06-09 23:17","niceShareDate":"2020-06-05 20:46","origin":"","prefix":"","projectLink":"","publishTime":1591715856000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1591361207000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | Java里面还能这么创建对象？","type":0,"userId":2,"visible":1,"zan":12},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>大家在开发工程中，有没有发现，有的自定义控件可以正常预览，有的就不行？<\/p>\r\n<p>那么针对这个情况，问题来了：<\/p>\r\n<ol>\r\n<li>自定义控件是如何做到预览的，AS 中我们的 View 的代码执行了？<\/li>\r\n<li>有些自定义控件不能预览的原因是？我们该怎么做，让其尽可能做到可预览状态？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":13701,"link":"https://www.wanandroid.com/wenda/show/13701","niceDate":"2020-06-07 20:55","niceShareDate":"2020-06-01 00:54","origin":"","prefix":"","projectLink":"","publishTime":1591534548000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1590944069000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 自定义控件无法预览该怎么办？","type":0,"userId":2,"visible":0,"zan":5},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>在之前的问答中：<\/p>\r\n<p><a href=\"https://wanandroid.com/wenda/show/12424\">每日一问 ViewPager 这个流传广泛的写法，其实是有问题的！<\/a><\/p>\r\n<p>我们指出了一个ViewPager 的错误写法，提到了根本原因是 Fragment存在恢复机制。<\/p>\r\n<p>那么我们继续深入讨论一下：<\/p>\r\n<ol>\r\n<li>一般情况下，我们讨论 Activity重建 Fragment恢复，都是以 Activity 旋转距离，其实还有个 case，就是进程由于内存不足被杀死，返回这个 app，Activity 也会被重建，这种情况下Fragment 也会被恢复吗（这个可以通过 app 授权一个相机权限，然后打开某个 activity，再去设置页关闭相机权限，切回 app ，就能模拟进程杀死activity 重建）？<\/li>\r\n<li>Fragment 恢复是真的和重建前使用的是同一个对象吗？<\/li>\r\n<li>是如何做到恢复的？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":12574,"link":"https://www.wanandroid.com/wenda/show/12574","niceDate":"2020-06-07 09:01","niceShareDate":"2020-03-25 00:56","origin":"","prefix":"","projectLink":"","publishTime":1591491714000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1585068987000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | Fragment 是如何被存储与恢复的？  有更新","type":0,"userId":2,"visible":1,"zan":22}]
         * offset : 0
         * over : false
         * pageCount : 6
         * size : 21
         * total : 119
         */

        private int curPage;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        private List<DatasBean> datas;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        @Entity(tableName = "DataBean")
        public static class DatasBean {
            /**
             * apkLink :
             * audit : 1
             * author : xiaoyang
             * canEdit : false
             * chapterId : 440
             * chapterName : 官方
             * collect : false
             * courseId : 13
             * desc : <p>我们在写代码的时候，有时候很容易使用一个高版本的 API，如果不注意，可能会导致在一些低版本的设备崩溃。</p>
             * <p>因此，我们可以选择引入 lint 在编译时进行检查。</p>
             * <p>今天的问题是？</p>
             * <ol>
             * <li>应用：如何在打包时，强制开启 New API检查？</li>
             * <li>原理：lint 怎么能知道某个 方法是哪个版本加入的？是有一个汇总的地方维护着这样的方法列表吗？</li>
             * <li>原理：即使有这样的一个列表，lint是怎么扫描每一个方法调用的？</li>
             * </ol>
             * descMd :
             * envelopePic :
             * fresh : false
             * id : 15949
             * link : https://www.wanandroid.com/wenda/show/15949
             * niceDate : 2020-11-02 00:15
             * niceShareDate : 2020-11-02 00:14
             * origin :
             * prefix :
             * projectLink :
             * publishTime : 1604247343000
             * realSuperChapterId : 439
             * selfVisible : 0
             * shareDate : 1604247264000
             * shareUser :
             * superChapterId : 440
             * superChapterName : 问答
             * tags : [{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}]
             * title : 每日一问 | Call requires API level 23 (current min is 14) 扫描出来的原理是？
             * type : 1
             * userId : 2
             * visible : 1
             * zan : 4
             */

            private String apkLink;
            private int audit;
            private String author;
            private boolean canEdit;
            private int chapterId;
            private String chapterName;
            private boolean collect;
            private int courseId;
            private String desc;
            private String descMd;
            private String envelopePic;
            private boolean fresh;
            private int id;
            private String link;
            private String niceDate;
            private String niceShareDate;
            private String origin;
            private String prefix;
            private String projectLink;
            private long publishTime;
            private int realSuperChapterId;
            private int selfVisible;
            private long shareDate;
            private String shareUser;
            private int superChapterId;
            private String superChapterName;
            private String title;
            private int type;
            private int userId;
            private int visible;
            private int zan;
            private List<TagsBean> tags;

            public String getApkLink() {
                return apkLink;
            }

            public void setApkLink(String apkLink) {
                this.apkLink = apkLink;
            }

            public int getAudit() {
                return audit;
            }

            public void setAudit(int audit) {
                this.audit = audit;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public boolean isCanEdit() {
                return canEdit;
            }

            public void setCanEdit(boolean canEdit) {
                this.canEdit = canEdit;
            }

            public int getChapterId() {
                return chapterId;
            }

            public void setChapterId(int chapterId) {
                this.chapterId = chapterId;
            }

            public String getChapterName() {
                return chapterName;
            }

            public void setChapterName(String chapterName) {
                this.chapterName = chapterName;
            }

            public boolean isCollect() {
                return collect;
            }

            public void setCollect(boolean collect) {
                this.collect = collect;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getDescMd() {
                return descMd;
            }

            public void setDescMd(String descMd) {
                this.descMd = descMd;
            }

            public String getEnvelopePic() {
                return envelopePic;
            }

            public void setEnvelopePic(String envelopePic) {
                this.envelopePic = envelopePic;
            }

            public boolean isFresh() {
                return fresh;
            }

            public void setFresh(boolean fresh) {
                this.fresh = fresh;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getNiceDate() {
                return niceDate;
            }

            public void setNiceDate(String niceDate) {
                this.niceDate = niceDate;
            }

            public String getNiceShareDate() {
                return niceShareDate;
            }

            public void setNiceShareDate(String niceShareDate) {
                this.niceShareDate = niceShareDate;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public String getPrefix() {
                return prefix;
            }

            public void setPrefix(String prefix) {
                this.prefix = prefix;
            }

            public String getProjectLink() {
                return projectLink;
            }

            public void setProjectLink(String projectLink) {
                this.projectLink = projectLink;
            }

            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }

            public int getRealSuperChapterId() {
                return realSuperChapterId;
            }

            public void setRealSuperChapterId(int realSuperChapterId) {
                this.realSuperChapterId = realSuperChapterId;
            }

            public int getSelfVisible() {
                return selfVisible;
            }

            public void setSelfVisible(int selfVisible) {
                this.selfVisible = selfVisible;
            }

            public long getShareDate() {
                return shareDate;
            }

            public void setShareDate(long shareDate) {
                this.shareDate = shareDate;
            }

            public String getShareUser() {
                return shareUser;
            }

            public void setShareUser(String shareUser) {
                this.shareUser = shareUser;
            }

            public int getSuperChapterId() {
                return superChapterId;
            }

            public void setSuperChapterId(int superChapterId) {
                this.superChapterId = superChapterId;
            }

            public String getSuperChapterName() {
                return superChapterName;
            }

            public void setSuperChapterName(String superChapterName) {
                this.superChapterName = superChapterName;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getVisible() {
                return visible;
            }

            public void setVisible(int visible) {
                this.visible = visible;
            }

            public int getZan() {
                return zan;
            }

            public void setZan(int zan) {
                this.zan = zan;
            }

            public List<TagsBean> getTags() {
                return tags;
            }

            public void setTags(List<TagsBean> tags) {
                this.tags = tags;
            }

            public static class TagsBean {
                /**
                 * name : 本站发布
                 * url : /article/list/0?cid=440
                 */

                private String name;
                private String url;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
