
## 前言 ##
很久之前就在关注[代码家](https://github.com/daimajia)每日共享的[学习资源](http://gank.io/)，获益匪浅。

最近比较空闲，并且想自己构建一个完整项目，来了解自己的不足，刚好Gank提供了[API](http://gank.io/api)，于是决定一试。

## 效果展示 ##
| 页面  | 效果 |
| :------- |:---------------:|
| 首页效果 | ![](https://github.com/YuanTiger/TigerGank/blob/master/gank_home_gif.gif) |
| 网络监听 | ![](https://github.com/YuanTiger/TigerGank/blob/master/gank_net_gif.gif) |
| 每日详情 | ![](https://github.com/YuanTiger/TigerGank/blob/master/gank_detail_gif.gif) |

## 第三方库 ##
- 控件绑定：[ButterKnife](https://github.com/JakeWharton/butterknife)

- 网络请求：[okhttp](https://github.com/square/okhttp)

- 事件分发：[EventBus](https://github.com/greenrobot/EventBus)

- 方法数量突破：[multidex](https://developer.android.com/studio/build/multidex.html)

- Json解析：[fastjson](https://github.com/alibaba/fastjson)

- 图片加载：[Glide](https://github.com/bumptech/glide)

- 图片浏览控件：[PinchImageView](https://github.com/boycy815/PinchImageView)

- 下拉刷新：[MaterialRefreshLayout](https://github.com/android-cjj/Android-MaterialRefreshLayout)

## 项目框架 ##
首先，整体的项目框架，采用MVP框架。

关于Android框架，还是比较自由的。根据需求去选择自己喜欢的框架就可以。

这里我想加深一下自己对MVP框架的理解，于是选择采用MVP框架。

关于MVC、MVP、MVVM的框架含义，可以参考[此篇短文](http://www.ruanyifeng.com/blog/2015/02/mvcmvp_mvvm.html)，言简意赅。

该项目的框架基本仿照Google开源的[Android框架模板](https://github.com/googlesamples/android-architecture)。

虽然存在一些差异，不过大同小异。

## BaseActivity ##
一个好的基类，可以大大减少后续的工作量。

首先，我们来稍微屡一下全局的要求：

- 标题栏：基本所有页面都会有，但是有些页面可能需要定制化，甚至可能没有。

- 页面控制：此处是一大头，包括Loading、无网、无数据、数据展示等多种页面样式以及展示逻辑。


综上，我认为我们需要为BaseActivity设置一个layout文件，它持有ToolBar和各个状态的页面UI，并且可以控制页面UI的展示时机。

在子Activity中，继承BaseActivity后只需设置数据UI的样式即可。

接下来我们就开始构建BaseActivity，首先来构建BaseActivity的layout：
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/c_ffffff"
    android:orientation="vertical">

    <!--ToolBar-->
    <android.support.v7.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/c_3f51b5"
        android:minHeight="?attr/actionBarSize" />

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1">

        <!--Loading Page-->
        <include
            android:id="@+id/view_loading"
            layout="@layout/view_loading"
            android:visibility="gone" />

        <!--无数据 Page-->
        <include
            android:id="@+id/view_no_data"
            layout="@layout/view_no_data"
            android:visibility="gone" />

        <!--无网 Page-->
        <include
            android:id="@+id/view_no_net"
            layout="@layout/view_no_net"
            android:visibility="gone" />

        <!--数据 Page-->
        <FrameLayout
            android:id="@+id/view_data"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:visibility="gone" />

    </FrameLayout>

</LinearLayout>
```
可以看到，它包括了ToolBar、LoadingPage、无数据Page、无网Page以及数据Page。

接下来我们在BaseActivity中对控件进行初始化，开始书写页面状态的控制代码。

此处各位看官要有意识，这些控制代码，不应存在于BaseActivit中。

原因有很多，首先因为Activity是我们MVP的View层，它仅仅掌控UI变化的结果，而不应该持有UI变化的逻辑判断。其次，如果我们将来封装BaseFragment，难道需要将这些逻辑代码再写一遍应用至BaseFragment中吗？

所以这里我决定在BaseActivity中创建成员变量**PageController**。每个Activity都会有一个独立的**PageController**，它持有Activity，来通知Activity的UI变化，并且和Activity拥有相同的生命周期：
```
//页面状态控制器，每个页面持有一个，使用弱引用来持有Activity
//存在于BaseActivity中，无需手动控制，在页面销毁时自动释放
public class PageController {
    //Activity对象
    private WeakReference<BaseActivity> weakActivity;
    //当前页面状态
    private int currentState;

    public PageController(BaseActivity activity) {

        weakActivity = new WeakReference<>(activity);
    }
    //销毁方法
    public void onDestory() {
        if (weakActivity == null) {
            return;
        }
        weakActivity.clear();
        weakActivity = null;
    }
}
```
接下来就开始构建具体的页面控制逻辑。

这个逻辑因人而异，我这里就不介绍自己构建时的思路了，源码里的注释都有体现。

在完成**PageController**后，当你需要构建BaseFragment时，仅仅需要将fragment.getAcitivty()传递至**PageController**中，就可以使用**PageController**了。

之后在实际使用过程中，我也发现了一个问题：

由于我们的页面是根据页面状态来显示的，也就是说随着状态变化，页面中有且只有一种类型的页面处于显示状态。

这就产生了一个巨大的问题：当数据View有下拉刷新或者抽屉效果时，如果网络超时或者无数据，数据View就会隐藏显示对应的状态View，数据View的隐藏代表着下拉刷新或抽屉效果的View被隐藏了，也就代表着用户无法使用了。

这明显是不符合用户操作习惯的。

这个问题待解决。

## 网络请求 ##
使用**okhttp**来作为本次开发的网络请求底层。

再好的框架，想要适应一个项目，必定需要进行定制化的封装。

在封装之前，先回想一下一次完整的网络请求，注意我们项目是MVP框架：

用户在Activity中发出请求—>调用Presenter层的网络请求方法->调用Model层的网络请求方法->传递给Presenter请求结果以及数据->传递给Activity结果以及数据

在MVP框架下，一次网络请求基本就是这样的了。
不过这就牵扯到框架问题了，我们先回到网络请求中，不去考虑框架。

如何最大限度的抽象，是我们现在要着重考虑的。

并且还要考虑并发、缓存、异步同步等一系列事情。

该项目的网络请求封装简直不忍直视，基本是本着能用就行的思想来进行封装的。。。

![](http://7xvzby.com1.z0.glb.clouddn.com/gaoxiao/%E6%83%A8%E4%B8%8D%E5%BF%8D%E7%9D%B9.png)

我发现此处是我的薄弱项，在将来有空时我会好好学习的。。。。

这里就不做过多介绍了，其实网上有很多基于okhttp封装相当好的网络请求框架，想使用的话也可以使用。


## Activity切换动画 ##
Activity的切换动画是可以定制的，大家都知道。这里我定义了多套Activity切换动画，并封装为[Jump](https://github.com/YuanTiger/TigerGank/blob/master/app/src/main/java/com/my/gank/base/Jump.java)，和**PageController**一样，每个Acitivty持有一个。

为什么要这样做？因为我认为Activity的切换动画体验其实很重要。

有些时候，从一个Activity打开一个Activity时，是一种流程，类似的有修改密码功能：

输入完成旧密码之后点击下一步，打开输入新密码页面，就属于一种流程页面。那么它的动画应该就和上个页面很契合，给人一种页面连贯感。

有些时候，即将打开的Activity与上个Activity毫无关联，用户将在这个页面领略到风格完全不同的数据，此时Activity的切换动画就需要给人一种打开新世界的感觉。

比如一些详情页、或者一些轮播图跳转等。


在封装Jump的过程中，我想到了两种方案：

- 将Jump封装为单例静态的，并在Jump中封装一个Map集合来记录每个Activity的打开动画的类型，当这个Activity要关闭时，去查询匹配对应的关闭动画。

- 每个Activity持有一个Jump，该Jump记录了该Activity打开时的动画类型，在关闭时直接去查询匹配即可。


两种方案理解之后，可以很明显地发现，第2种方案更简单一些，并且第2种方案的性能会更好一些？

所以我是选择了第2种方案。

接着在开发过程中，遇到了一个难点，和大家分享一下：

在使用封装的Jump跳转时，该Jump是上个Activity持有的Jump，而不是即将打开的Activity持有的Jump。

这就导致了使用Jump去获取动画类型时，获取的是上个Activity的动画类型，而不是即将打开的动画类型。

为什么不使用即将打开的Activity的Jump呢？因为在跳转过去的时候，也就是startActivity()时，我们是没有即将打开的Activity的实例的。

![](http://7xvzby.com1.z0.glb.clouddn.com/gank/gank_desc_0.png)

我写了很多代码来进行测试，发现大部分解决方案都是需要在两处指定动画类型。

我认为这是不符合编程思想的。将来如果有人接手这些代码，那么必定不知道要指定Activity的动画类型，需要修改两处代码。

目前我是通过添加Jump中封装的跳转方法的参数，用来指定动画类型，就像这样子：
```
public void to(Intent intent) {}
public void to(Intent intent, Jump.JumpType type) {}
public void to(Intent intent, int requestCode) {}
public void to(Intent intent, int requestCode,Jump.JumpType type) {}
```
可以发现除了传递Intent之外，我又添加了指定动画类型**JumpType**的跳转方法。

在指定**JumpType**的跳转方法中，将**JumpType**同时塞入到Intent当中，然后在BaseActivity中去解析**JumpType**并赋值给Jump，就是整体的流程。

这里一定要注意，当指定**JumpType**时，我们不能将**JumpType**赋值给当前Jump，因为这个**JumpType**是下个Activity所做的动画，而不是当前Activity所做的动画。

具体代码可参考：[Jump](https://github.com/YuanTiger/TigerGank/blob/master/app/src/main/java/com/my/gank/base/Jump.java)


## TODO ##
到目前为止，还有一些功能没有开发：

- 搜索功能

   Gank的API中拥有搜索的API，该功能在后续会去实现。

- 网络请求优化

   从该项目当中，我发现自己对网络请求的理解和Android文件存储的方式存在很多问题，等到有空，我会将网络请求这边重构，包括添加File缓存、异步同步请求、文件上传接收请求等一系列基本功能。

- RxJava+Retrofit

   本项目当中没有使用RxJava。在后期有空我会重新再写一遍该项目，会使用RxJava和其他框架来实现，这算是立一个flag吧。


## 结语 ##
这一周的精力除了完成本职工作外，基本上都放到了[TigerGank](https://github.com/YuanTiger/TigerGank)中。

我会慢慢抽空继续完善该项目，这周就先这样了。

开发过程中遇到很多瓶颈，解决瓶颈的代码肯定是不优雅的。主要还是因为自己的水平太差。

一起加油，感兴趣的朋友也可以自己开发Gank。

## 感谢 ##
上述所有第三方库

[Gank API](http://gank.io/api)

