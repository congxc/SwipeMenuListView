package com.rainnshieh;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import com.rainnshieh.adapter.CommonAdapter;
import com.rainnshieh.adapter.ViewHolder;
import com.rainnshieh.views.PullToRefreshLayout;
import com.rainnshieh.views.SwipeMenu;
import com.rainnshieh.views.SwipeMenuCreator;
import com.rainnshieh.views.SwipeMenuItem;
import com.rainnshieh.views.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PullToRefreshLayout.OnRefreshListener, SwipeMenuListView.OnMenuItemClickListener {

    private PullToRefreshLayout mRefreshLayout;
    private SwipeMenuListView mSwipeMenuListView;
    private List<String> mDatas = new ArrayList<>();
    private CommonAdapter<String> mCommonAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initView();
        initListener();
    }
    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
    private void init() {
        for (int i = 0; i < 10; i++) {
            mDatas.add("内容");
        }
    }

    private void initView() {
        mRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refreshlayout);
        mSwipeMenuListView = (SwipeMenuListView) findViewById(R.id.swipemenuListView);
        initCreator();
        mCommonAdapter = new CommonAdapter<String>(this,mDatas,R.layout.item_view) {
            @Override
            public int getItemViewType(int position) {
                if(position %2 == 0){
                    return 0;
                }
                return 1;
            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.tv,holder.getPosition()+"----"+s);
            }
        };
        mSwipeMenuListView.setAdapter(mCommonAdapter);
    }

    private void initCreator() {//为ListView添加滑出菜单
        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                switch (menu.getViewType()) {
                    case 0:
                        createMenu0(menu);
                        break;
                    case 1:
                        createMenu1(menu);
                        break;
                }
            }
            private void createMenu0(SwipeMenu menu) {//一种情况只有添加按钮
                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(R.drawable.bg_yellow_selector);
                item1.setWidth(dp2px(70));
                item1.setTitle("添加");
                item1.setTitleColor(Color.WHITE);
                item1.setTitleSize(16);
                menu.addMenuItem(item1);
            }
            private void createMenu1(SwipeMenu menu) {//有添加 也有 删除 （可以添加多种）
                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(R.drawable.bg_yellow_selector);
                item2.setWidth(dp2px(70));
                item2.setTitle("添加");
                item2.setTitleColor(Color.WHITE);
                item2.setTitleSize(16);
                menu.addMenuItem(item2);

                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(R.drawable.bg_red_img_selector);
                item1.setWidth(dp2px(70));
                item1.setIcon(R.drawable.menu_icon_delete);
                item1.setTitleSize(16);
                menu.addMenuItem(item1);
            }
        };
        mSwipeMenuListView.setMenuCreator(swipeMenuCreator);
    }

    private void initListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mSwipeMenuListView.setOnMenuItemClickListener(this);
    }

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        mDatas.clear();
        mCommonAdapter.notifyDataSetChanged();
        new Thread(){
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(2000);
                final List<String> datas = new ArrayList<String>();
                for (int i = 0; i < 10; i++) {
                    datas.add("内容");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.addAll(datas);
                        mCommonAdapter.notifyDataSetChanged();
                        pullToRefreshLayout.refreshFinish(true);
                    }
                });
            }
        }.start();
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(2000);
                final List<String> datas = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    datas.add("内容");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.addAll(datas);
                        mCommonAdapter.notifyDataSetChanged();
                        pullToRefreshLayout.refreshFinish(true);
                    }
                });
            }
        }.start();
    }

    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        switch (index){
            case  0:
                if(menu.getViewType() == 0){//添加
                   mDatas.add("内容新增");
                }else{
                    mDatas.remove(position);
                }
                mCommonAdapter.notifyDataSetChanged();
                break;
            case  1:
                mDatas.remove(position);
                mCommonAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
        return false;
    }
}
