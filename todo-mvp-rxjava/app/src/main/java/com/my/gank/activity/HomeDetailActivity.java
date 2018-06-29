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
import com.my.gank.base.Jump;
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

import butterknife.BindView;


/**
 * Author：mengyuan
 * Date  : 2017/8/15下午2:20
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class HomeDetailActivity extends BaseActivity implements HomeDetailContract.View {

    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.recycler_view)
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    //--------------------------------接口回调--------------------------------
    //--------------------------------接口回调--------------------------------
    //--------------------------------接口回调--------------------------------
    @Override
    public void getDataSuccess(final HomeDetailItemBean data) {
        if (data == null ) {
            getDataFailed(getString(R.string.no_data));
            return;
        }
        if (data.福利 != null) {
            //福利图
            Glide.with(this)
                    .load(data.福利.get(0).url + Constant.URL.imageSize)
                    .into(backdrop);
            //福利图点击放大事件
            appbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BrowseImagePopupWindow popupWindow = new BrowseImagePopupWindow(HomeDetailActivity.this, data.福利.get(0).url, view.getWidth() / 2, view.getHeight() / 2);
                    popupWindow.showAtLocation(getToolBar(), Gravity.CENTER | Gravity.TOP, 0, 0);
                    popupWindow.startAnimotion();
                }
            });
        }
        resultData = new ArrayList<>();
        //计算条目总数,因为福利不在RecyclerView中显示，故剔除
        if (data.Android != null && data.Android.size() > 0) {
            resultData.add(new HomeDetailTileBean(getString(R.string.Android)));
            resultData.addAll(data.Android);
        }
        if (data.iOS != null && data.iOS.size() > 0) {
            resultData.add(new HomeDetailTileBean(getString(R.string.iOS)));
            resultData.addAll(data.iOS);
        }
        if (data.休息视频 != null && data.休息视频.size() > 0) {
            resultData.add(new HomeDetailTileBean(getString(R.string.sleepVedio)));
            resultData.addAll(data.休息视频);
        }
        if (data.前端 != null && data.前端.size() > 0) {
            resultData.add(new HomeDetailTileBean(getString(R.string.frontEnd)));
            resultData.addAll(data.前端);
        }
        if (data.瞎推荐 != null && data.瞎推荐.size() > 0) {
            resultData.add(new HomeDetailTileBean(getString(R.string.recommend)));
            resultData.addAll(data.瞎推荐);
        }
        if (data.App != null && data.App.size() > 0) {
            resultData.add(new HomeDetailTileBean(getString(R.string.App)));
            resultData.addAll(data.App);
        }
        if (data.拓展资源 != null && data.拓展资源.size() > 0) {
            resultData.add(new HomeDetailTileBean(getString(R.string.resource)));
            resultData.addAll(data.拓展资源);
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
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.tv_desc)
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
                    Intent intent = new Intent(HomeDetailActivity.this, WebViewActivity.class);
                    intent.putExtra("url", data.url);
                    jump.to(intent, Jump.JumpType.EJECT);
                }
            });
        }
    }

    //标题Holder
    public class TitleHoder extends BaseRecyclerViewHolder<HomeDetailTileBean> {
        @BindView(R.id.tv_title)
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
