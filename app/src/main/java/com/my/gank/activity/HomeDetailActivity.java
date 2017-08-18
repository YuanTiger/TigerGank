package com.my.gank.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.my.gank.Constant;
import com.my.gank.R;
import com.my.gank.base.BaseActivity;
import com.my.gank.base.BaseRecyclerViewHolder;
import com.my.gank.bean.GankItemBean;
import com.my.gank.bean.HomeDetailItemBean;
import com.my.gank.bean.HomeDetailTileBean;
import com.my.gank.bean.HomeDetailView;
import com.my.gank.contract.HomeDetailContract;
import com.my.gank.presenter.HomeDetailPresenter;
import com.my.gank.utils.ToastUtil;
import com.my.gank.view.BrowseImagePopupWindow;
import com.my.gank.view.TouchImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * Author：mengyuan
 * Date  : 2017/8/15下午3:00
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class HomeDetailActivity extends BaseActivity implements HomeDetailContract.View {

    @Bind(R.id.backdrop)
    ImageView backdrop;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.main_content)
    CoordinatorLayout mainContent;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private String date;

    private HomeDetailContract.Presenter presenter;


    private HomeDetailAdapter adapter;

    private List<HomeDetailView> resultData;

    @Override
    public void sendRequest() {
        presenter.requestDetail(date);

    }

    @Override
    public int getLayId() {
        return R.layout.activity_home_detail;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        date = getIntent().getStringExtra("date");
        if (TextUtils.isEmpty(date)) {
            ToastUtil.showShortToast(getString(R.string.data_error));
            finish();
            return;
        }

        presenter = new HomeDetailPresenter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void toolBarSetting(Toolbar toolbar) {
        super.toolBarSetting(toolbar);
        toolbar.setTitle(date);
    }

    @Override
    public void reConnection() {
        sendRequest();
    }

    @Override
    public int getPageStyle() {
        return Constant.PageStyle.LOADING_PAGE;
    }

    /**
     * 静态跳转
     *
     * @param context 。
     * @param date    日期格式为：xxxx/xx/xx
     */
    public static void toDetail(Context context, String date) {
        Intent intent = new Intent(context, HomeDetailActivity.class);
        intent.putExtra("date", date);
        context.startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    //--------------------------------接口回调--------------------------------
    //--------------------------------接口回调--------------------------------
    //--------------------------------接口回调--------------------------------
    @Override
    public void getDataSuccess(final HomeDetailItemBean data) {
        if (data == null || !data.isSuccess() || data.category == null || data.category.size() <= 0) {
            getDataFailed(getString(R.string.no_data));
            return;
        }
        if (data.results.福利 != null) {
            //福利图
            Glide.with(this)
                    .load(data.results.福利.get(0).url + Constant.URL.imageSize)
                    .centerCrop()
                    .into(backdrop);
            //福利图点击放大事件
            appbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BrowseImagePopupWindow popupWindow = new BrowseImagePopupWindow(HomeDetailActivity.this, data.results.福利.get(0).url, view.getWidth()/2, view.getHeight()/2);
                    popupWindow.showAtLocation(getToolBar(), Gravity.CENTER | Gravity.TOP, 0, 0);
                    popupWindow.startAnimotion();
                }
            });
        }
        resultData = new ArrayList<>();
        //计算条目总数,因为福利不在RecyclerView中显示，故剔除
        if (data.results.Android != null && data.results.Android.size() > 0) {
            resultData.add(new HomeDetailTileBean(getString(R.string.Android)));
            resultData.addAll(data.results.Android);
        }
        if (data.results.iOS != null && data.results.iOS.size() > 0) {
            resultData.add(new HomeDetailTileBean(getString(R.string.iOS)));
            resultData.addAll(data.results.iOS);
        }
        if (data.results.休息视频 != null && data.results.休息视频.size() > 0) {
            resultData.add(new HomeDetailTileBean(getString(R.string.sleepVedio)));
            resultData.addAll(data.results.休息视频);
        }
        if (data.results.前端 != null && data.results.前端.size() > 0) {
            resultData.add(new HomeDetailTileBean(getString(R.string.frontEnd)));
            resultData.addAll(data.results.前端);
        }
        if (data.results.瞎推荐 != null && data.results.瞎推荐.size() > 0) {
            resultData.add(new HomeDetailTileBean(getString(R.string.recommend)));
            resultData.addAll(data.results.瞎推荐);
        }
        if (data.results.App != null && data.results.App.size() > 0) {
            resultData.add(new HomeDetailTileBean(getString(R.string.App)));
            resultData.addAll(data.results.App);
        }
        if (data.results.拓展资源 != null && data.results.拓展资源.size() > 0) {
            resultData.add(new HomeDetailTileBean(getString(R.string.resource)));
            resultData.addAll(data.results.拓展资源);
        }

        if (adapter == null) {
            adapter = new HomeDetailAdapter();
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        showData();
    }

    @Override
    public void getDataFailed(String message) {
        showNoDataPage(message);

    }

    //--------------------------------Adapter--------------------------------
    //--------------------------------Adapter--------------------------------
    //--------------------------------Adapter--------------------------------
    public class HomeDetailAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {


        @Override
        public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            switch (viewType) {
                case Constant.TYPE_HOME_DETAIL_TITLE:
                    return new TitleHoder(R.layout.item_home_detail_title, parent, viewType);
                case Constant.TYPE_HOME_DETAIL_ITEM:
                    return new ItemHolder(R.layout.item_home_detail_item, parent, viewType);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {

            if (holder instanceof TitleHoder) {
                ((TitleHoder) holder).refreshData((HomeDetailTileBean) resultData.get(position), position);
            } else if (holder instanceof ItemHolder) {
                ((ItemHolder) holder).refreshData((GankItemBean) resultData.get(position), position);
            }
        }


        @Override
        public int getItemViewType(int position) {

            return resultData.get(position).getType();
        }

        @Override
        public int getItemCount() {
            return resultData.size();
        }
    }

    //--------------------------------Holder--------------------------------
    //--------------------------------Holder--------------------------------
    //--------------------------------Holder--------------------------------
    //条目Holder
    public class ItemHolder extends BaseRecyclerViewHolder<GankItemBean> {
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.tv_desc)
        TextView tvDesc;


        public ItemHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
        }

        @Override
        public void refreshData(final GankItemBean data, int position) {
            tvDesc.setText(data.desc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebViewActivity.openUrl(HomeDetailActivity.this, data.url);
                }
            });
        }
    }

    //标题Holder
    public class TitleHoder extends BaseRecyclerViewHolder<HomeDetailTileBean> {
        @Bind(R.id.tv_title)
        TextView tvTitle;

        public TitleHoder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
        }

        @Override
        public void refreshData(HomeDetailTileBean data, int position) {
            tvTitle.setText(data.title);
        }
    }
}
