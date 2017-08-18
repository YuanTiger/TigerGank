
## 前言 ##
很久之前就在关注[代码家](https://github.com/daimajia)每日共享的[学习资源](http://gank.io/)，获益匪浅。

最近比较空闲，并且想自己构建一个完整项目，来了解自己的不足，刚好Gank提供了[API](http://gank.io/api)，于是决定一试。



目前处于开发阶段，该篇README等于是我的一些简单笔记。

## 效果展示 ##
| 页面  | 效果 |
| :------- |:---------------:|
| 首页效果 | ![首页效果](http://7xvzby.com1.z0.glb.clouddn.com/gank/gank_home_gif.gif) |
| 网络监听 | ![首页效果](http://7xvzby.com1.z0.glb.clouddn.com/gank/gank_net_gif.gif) |
| 每日详情 | ![首页效果](http://7xvzby.com1.z0.glb.clouddn.com/gank/gank_detail_gif.gif) |

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

## 第三方框架 ##
