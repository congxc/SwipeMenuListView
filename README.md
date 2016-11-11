1.使用 CommonAdapter+ViewHolder：
     mCommonAdapter = new CommonAdapter<String>(this,mDatas,R.layout.item_view) {
                @Override
                public void convert(ViewHolder holder, String s) {
                    holder.setText(R.id.tv,holder.getPosition()+"----"+s)
                            .setBackgroundRes(R.id.tv,R.drawable.icon_arrow)
                            .setOnClickListener(R.id.tv, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                }
            };
            mSwipeMenuListView.setAdapter(mCommonAdapter);
2.PullToRefreshLayout 万能的上拉加载更多 下拉刷新控件 让你的ListView  implements Pullable ，
  可以方便的通过setPullUpEnable(boolean)或者setPullDownEnable(boolean)控制是否可以上拉或下拉
    “<com.rainnshieh.views.PullToRefreshLayout
            android:id="@+id/refreshlayout"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <com.rainnshieh.views.SwipeMenuListView
                android:id="@+id/swipemenuListView"
                android:layout_width="match_parent"
                android:layout_height="match_parent"/>
        </com.rainnshieh.views.PullToRefreshLayout>”

3.解决baoyz的SwipeMenuListView控件使用CommonAdapter时滑出控件类型不一致的bug