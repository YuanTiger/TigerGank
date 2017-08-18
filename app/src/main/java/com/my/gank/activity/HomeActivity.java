package com.my.gank.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.my.gank.Constant;
import com.my.gank.R;
import com.my.gank.base.BaseActivity;
import com.my.gank.base.BaseRecyclerViewHolder;
import com.my.gank.bean.GankItemBean;
import com.my.gank.bean.HomeAllBean;
import com.my.gank.bean.TypeGankBean;
import com.my.gank.contract.HomeContract;
import com.my.gank.presenter.HomePresenter;
import com.my.gank.utils.DateUtils;
import com.my.gank.utils.LogUtil;
import com.my.gank.utils.ToastUtil;
import com.my.gank.view.BrowseImagePopupWindow;
import com.my.gank.view.TouchImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Author：mengyuan
 * Date  : 2017/8/14下午5:42
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class HomeActivity extends BaseActivity implements HomeContract.View {


    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.refresh_view)
    MaterialRefreshLayout refreshView;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    //退出App，2秒内连续点击2次，记录第一次点击时间
    private long mExitTime;
    //页码
    private int pageIndex;

    //上次侧滑选中的菜单
    private MenuItem lastSelectMenu;

    private HomeAllAdapter allAdapter;
    private HomeTypeAdapter typeAdapter;

    private HomePresenter presenter;

    private List<HomeAllBean.ResultsBean> allDataList;
    private List<GankItemBean> typeDataList;


    @Override
    public void sendRequest() {
        if (lastSelectMenu == null || lastSelectMenu.getTitle().toString().equals(getString(R.string.all))) {
            presenter.requestHistoryList(pageIndex);
        } else {
            presenter.requestTypeDataList(lastSelectMenu.getTitle().toString(), pageIndex);
        }
    }

    @Override
    public int getLayId() {
        return R.layout.activity_home;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //初始化抽屉点击事件
        setupDrawerContent(navView);
        //初始化Presenter
        presenter = new HomePresenter(this);

        //刷新监听
        refreshView.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                pageIndex = 1;
                sendRequest();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                pageIndex++;
                sendRequest();
            }
        });
        //recyclerView初始化
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


    }

    @Override
    public void toolBarSetting(Toolbar toolbar) {
        super.toolBarSetting(toolbar);
        toolbar.setNavigationIcon(R.drawable.menu_home);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(Gravity.START)) {
                    drawerLayout.closeDrawer(Gravity.START);
                } else {
                    drawerLayout.openDrawer(Gravity.START);
                }
            }
        });
    }

    @Override
    public void reConnection() {
        sendRequest();
    }


    private void setupDrawerContent(NavigationView navigationView) {
        //默认选中 菜单中的 第一个
        lastSelectMenu = navigationView.getMenu().getItem(0);
        lastSelectMenu.setChecked(true);
        getToolBar().setTitle(getString(R.string.all));
        //策划菜单的点击监听
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        changeSelect(menuItem);
                        showLoadingPage();
                        pageIndex = 1;
                        sendRequest();
                        return true;
                    }
                });
    }

    /**
     * 改变侧滑菜单选中的menu
     *
     * @param menuItem
     */
    private void changeSelect(MenuItem menuItem) {
        lastSelectMenu.setChecked(false);
        menuItem.setChecked(true);
        lastSelectMenu = menuItem;
        getToolBar().setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.showShortToast(getString(R.string.again_click_exit_app));
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void stopRefresh() {
        refreshView.finishRefresh();
        refreshView.finishRefreshLoadMore();
    }

    //--------------------------------接口回调--------------------------------
    //--------------------------------接口回调--------------------------------
    //--------------------------------接口回调--------------------------------
    @Override
    public void getAllDataSuccess(HomeAllBean data) {
        stopRefresh();
        if (!data.isSuccess()) {
            getAllDataFailed(getString(R.string.no_data));
            return;
        }
        if (data.results == null || data.results.size() <= 0) {
            refreshView.setLoadMore(false);
            return;
        } else {
            refreshView.setLoadMore(true);
        }
        //将数据条目的Apdater置为null
        typeAdapter = null;
        if (allDataList == null) {
            allDataList = new ArrayList<>();
        }
        if (pageIndex == 1) {
            allDataList.clear();
        }
        allDataList.addAll(data.results);

        if (allAdapter == null) {
            allAdapter = new HomeAllAdapter();
            recyclerView.setAdapter(allAdapter);
        } else {
            allAdapter.notifyDataSetChanged();
        }
        showData();
    }

    @Override
    public void getAllDataFailed(String message) {
        stopRefresh();
        showNoNetPage();
    }


    @Override
    public void getTypeDataSuccess(TypeGankBean data) {
        stopRefresh();
        if (!data.isSuccess()) {
            getAllDataFailed(getString(R.string.no_data));
            return;
        }
        if (data.results == null || data.results.size() <= 0) {
            refreshView.setLoadMore(false);
            return;
        } else {
            refreshView.setLoadMore(true);
        }
        //将数据条目的Apdater置为null
        allAdapter = null;
        //保存临时数据
        if (typeDataList == null) {
            typeDataList = new ArrayList<>();
        }
        if (pageIndex == 1) {
            typeDataList.clear();
        }
        typeDataList.addAll(data.results);

        if (typeAdapter == null) {
            typeAdapter = new HomeTypeAdapter();
            recyclerView.setAdapter(typeAdapter);
        } else {
            typeAdapter.notifyDataSetChanged();
        }
        showData();
    }

    @Override
    public void getTypeDataFailed(String message) {
        stopRefresh();
        showNoNetPage();
    }


    //--------------------------------Adapter--------------------------------
    //--------------------------------Adapter--------------------------------
    //--------------------------------Adapter--------------------------------
    //全部的Adapter
    public class HomeAllAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {


        @Override
        public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HomeAllHolder(R.layout.item_home_all, parent, viewType);
        }

        @Override
        public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
            holder.refreshData(allDataList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return allDataList == null ? 0 : allDataList.size();
        }
    }

    //某种数据的条目Adapter
    public class HomeTypeAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
        private final int TYPE_WELFARE = 23;
        private final int TYPE_DATA = 24;


        @Override
        public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TYPE_WELFARE:
                    return new HomeTypeSrcHolder(R.layout.item_home_welfare, parent, viewType);
                case TYPE_DATA:
                    return new HomeTypeDataHolder(R.layout.item_home_type_data, parent, viewType);

            }
            return null;
        }

        @Override
        public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
            holder.refreshData(typeDataList.get(position), position);
        }

        @Override
        public int getItemViewType(int position) {
            if (typeDataList.get(position).type.equals(getString(R.string.welfare))) {
                return TYPE_WELFARE;
            }
            return TYPE_DATA;

        }

        @Override
        public int getItemCount() {
            return typeDataList == null ? 0 : typeDataList.size();
        }
    }

    //--------------------------------Holder--------------------------------
    //--------------------------------Holder--------------------------------
    //--------------------------------Holder--------------------------------
    //所有数据的Adapter
    public class HomeAllHolder extends BaseRecyclerViewHolder<HomeAllBean.ResultsBean> {
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_desc)
        TextView tvDesc;


        public HomeAllHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
        }

        @Override
        public void refreshData(final HomeAllBean.ResultsBean data, int position) {
            tvDate.setText(DateUtils.toGankDate(data.created_at));

            tvDesc.setText(data.title);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HomeDetailActivity.toDetail(HomeActivity.this, tvDate.getText().toString());
                }
            });
        }
    }

    //特定类型下的福利Holder
    public class HomeTypeSrcHolder extends BaseRecyclerViewHolder<GankItemBean> {
        @Bind(R.id.tv_author)
        TextView tvAuthor;
        @Bind(R.id.iv_src)
        TouchImageView ivSrc;

        public HomeTypeSrcHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
        }

        @Override
        public void refreshData(final GankItemBean data, int position) {

            tvAuthor.setText(String.format(getString(R.string.provide_s), data.who));
            Glide.with(HomeActivity.this)
                    .load(data.url + Constant.URL.imageSize)
                    .centerCrop()
                    .into(ivSrc);

            ivSrc.setonImageClickListener(new TouchImageView.onImageClickListener() {
                @Override
                public void onClick(float x, float y, View view) {
                    BrowseImagePopupWindow popupWindow = new BrowseImagePopupWindow(HomeActivity.this, data.url, x,y);
                    popupWindow.showAtLocation(getToolBar(), Gravity.CENTER | Gravity.TOP, 0, 0);
                    popupWindow.startAnimotion();
                }
            });
        }
    }

    //特定类型下的普通Holder
    public class HomeTypeDataHolder extends BaseRecyclerViewHolder<GankItemBean> {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_author)
        TextView tvAuthor;
        @Bind(R.id.tv_date)
        TextView tvDate;

        public HomeTypeDataHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
        }

        @Override
        public void refreshData(final GankItemBean data, int position) {

            tvTitle.setText(data.desc);
            tvAuthor.setText(data.who);
            tvDate.setText(DateUtils.toGankDate(data.createdAt));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebViewActivity.openUrl(HomeActivity.this, data.url);
                }
            });
        }
    }
}
