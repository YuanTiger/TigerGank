package com.my.gank.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.my.gank.Constant;
import com.my.gank.R;
import com.my.gank.base.BaseActivity;
import com.my.gank.base.BaseRecyclerViewHolder;
import com.my.gank.bean.HomeAllBean;
import com.my.gank.contract.HomeContract;
import com.my.gank.presenter.HomePresenter;
import com.my.gank.utils.DateUtils;
import com.my.gank.utils.ToastUtil;

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
    SwipeRefreshLayout refreshView;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    //页码
    private int pageIndex;

    //上次侧滑选中的菜单
    private MenuItem lastSelectMenu;

    private HomeAdapter adapter;

    private HomePresenter presenter;

    private List<HomeAllBean.ResultsBean> dataList;


    @Override
    public void sendRequest() {
        presenter.requestHistoryList(pageIndex = 1);
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
        //下拉刷新初始化
        //设置刷新时动画的颜色，可以设置4个
        refreshView.setProgressBackgroundColorSchemeResource(android.R.color.white);
        refreshView.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        refreshView.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        //下拉刷新控件监听
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRequest();
            }
        });
        //recyclerView初始化
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public int getPageStyle() {
        return Constant.PageStyle.LOADING_PAGE;
    }

    @Override
    public boolean isNeedToolbar() {
        return false;
    }

    @Override
    public void reConnection() {
        sendRequest();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //打开抽屉
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        //默认选中 菜单中的 第一个
        lastSelectMenu = navigationView.getMenu().getItem(0);
        lastSelectMenu.setChecked(true);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_all:
//                                ToastUtil.showLongToast(menuItem.getTitle().toString());
                                break;
                            case R.id.menu_android:
//                                ToastUtil.showLongToast("Android");
                            default:
                                break;
                        }
                        ToastUtil.showLongToast(menuItem.getTitle().toString());

                        changeSelect(menuItem);
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
        drawerLayout.closeDrawers();
    }

    //--------------------------------接口回调--------------------------------
    //--------------------------------接口回调--------------------------------
    //--------------------------------接口回调--------------------------------
    @Override
    public void getDataSuccess(HomeAllBean data) {
        refreshView.setRefreshing(false);
        if (!data.isSuccess()) {
            getDataFailed(getString(R.string.no_data));
            return;
        }
        if (data.results == null || data.results.size() <= 0) {
            // TODO: 2017/8/15 禁止上拉加载更多
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        if (pageIndex == 1) {
            dataList.clear();
        }
        dataList.addAll(data.results);

        if (adapter == null) {
            adapter = new HomeAdapter();
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        showData();
    }

    @Override
    public void getDataFailed(String message) {
        refreshView.setRefreshing(false);
        showNoDataPage(message);
    }

    //--------------------------------Adapter--------------------------------
    //--------------------------------Adapter--------------------------------
    //--------------------------------Adapter--------------------------------
    public class HomeAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {


        @Override
        public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HomeHolder(R.layout.item_home, parent, viewType);
        }

        @Override
        public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
            holder.refreshData(dataList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return dataList == null ? 0 : dataList.size();
        }
    }

    //--------------------------------Holder--------------------------------
    //--------------------------------Holder--------------------------------
    //--------------------------------Holder--------------------------------
    public class HomeHolder extends BaseRecyclerViewHolder<HomeAllBean.ResultsBean> {
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_desc)
        TextView tvDesc;


        public HomeHolder(int viewId, ViewGroup parent, int viewType) {
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
}
