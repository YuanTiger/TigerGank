
## 前言 ##
很久之前就在关注[代码家](https://github.com/daimajia)每日共享的[学习资源](http://gank.io/)，获益匪浅。

最近比较空闲，并且想自己构建一个完整项目，来了解自己的不足，刚好Gank提供了[API](http://gank.io/api)，于是决定一试。



目前处于开发阶段，该篇README等于是我的一些简单笔记。

## 效果展示 ##
| 页面  | 效果 |
| :------- |:---------------:|
| 首页效果 | ![](https://github.com/YuanTiger/TigerGank/blob/master/app/gank_home_gif.gif) |
| 网络监听 | ![](https://github.com/YuanTiger/TigerGank/blob/master/app/gank_net_gif.gif) |
| 每日详情 | ![](https://github.com/YuanTiger/TigerGank/blob/master/app/gank_detail_gif.gif) |

## 项目框架 ##
首先，整体的项目框架，打算采用MVP框架。

todo：MVP框架的选择
## BaseActivity ##
一个好的基类，可以减轻巨大的工作量，这是我坚信的。

我决定花大把时间，来打造一个自己满意的BaseActivity。

它应该包括：ToolBar标题栏的控制、页面状态的控制、切换动画的控制、网络监听等功能。


todo：开发完成后会补充详细说明。

## 网络请求 ##
计划使用**okhttp**来作为本次开发的网络请求底层。

再好的框架，想要适应一个项目，必定需要进行定制化的封装。

在封装之前，先回想一下一次完整的网络请求，注意我们项目是MVP框架：

用户在Activity中发出请求—>调用Presenter层的网络请求方法->调用Model层的网络请求方法->传递给Presenter请求结果以及数据->传递给Activity结果以及数据

在MVP框架下，一次完整的网络框架必定是这样的。

如何最大限度的抽象，是我们现在要着重考虑的。

在ViewInterface中封装成功和失败的回调，定义泛型为Bean对象。

接着在Presenter创建请求方法，参数包括请求地址、请求参数、回调结果处理，接着将这些参数统统传递到Model。

在Model层则仅需调用封装好的网络请求即可。

并且还要考虑并发、缓存等一系列事情。

## 第三方库 ##
- 控件绑定：[ButterKnife](https://github.com/JakeWharton/butterknife)

- 网络请求：[okhttp](https://github.com/square/okhttp)

- 事件分发：[EventBus](https://github.com/greenrobot/EventBus)

- 方法数量突破：[multidex](https://developer.android.com/studio/build/multidex.html)

- Json解析：[fastjson](https://github.com/alibaba/fastjson)

- 图片加载：[Glide](https://github.com/bumptech/glide)

- 图片浏览控件：[PinchImageView](https://github.com/boycy815/PinchImageView)

- 下拉刷新：[MaterialRefreshLayout](https://github.com/android-cjj/Android-MaterialRefreshLayout)

## TODO ##
到目前为止，还有一些功能没有开发：
1. Actiity切换动画

 Activity之间的切换看似一瞬间，其实我个人认为还是很重要的。上下配合的流程型页面应该使用左右横屏切换关闭，给人一种流畅的感觉。两个页面毫无关联的，应该给人一种打开全新界面的动画感等等。
2. GankApi的搜索功能

 Gank的API中拥有搜索的API，该功能在后续会去实现。
3. 网络请求优化

 从该项目当中，我发现自己对网络请求的理解和Android文件存储的方式存在很多问题，等到有空，我会将网络请求这边重构，包括添加File缓存、异步同步请求、文件上传接收请求等一系列基本功能。
4. RxJava+Retrofit

 本项目当中没有使用RxJava。在后期有空我会重新再写一遍该项目，会使用RxJava和其他框架来实现，这算是立一个flag吧。
