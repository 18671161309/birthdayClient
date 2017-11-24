package com.trip.happy.fragment;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.trip.core.app.ConfigKeys;
import com.trip.core.app.Happy;
import com.trip.core.okHttp.OkHttpUtils;
import com.trip.core.okHttp.callback.GenericsCallback;
import com.trip.core.okHttp.callback.JsonGenericsSerializator;
import com.trip.core.recyclerView.CommonAdapter;
import com.trip.core.recyclerView.MultiItemTypeAdapter;
import com.trip.core.recyclerView.base.ViewHolder;
import com.trip.core.recyclerView.wrapper.LoadMoreWrapper;
import com.trip.core.utils.ToastUtils;
import com.trip.happy.R;
import com.trip.happy.activities.WeiXinNewsActivity;
import com.trip.happy.bean.WeiXinBean;
import com.trip.happy.constant.Constants;
import com.trip.happy.fragment.base.LazyFragment;
import com.trip.happy.response.WeixinNewsResponse;
import com.trip.happy.view.ListViewDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by xiajun on 2017/11/20.
 */

public class SmileFragment extends LazyFragment {

    protected boolean isInit = false;

    @BindView(R.id.rcv_wei_xin_display)
    RecyclerView rcvWeiXinDisplay;
    @BindView(R.id.swl_wei_xin_refresh)
    SwipeRefreshLayout swlWeiXinRefresh;
    @BindView(R.id.pb_wei_xin_load)
    ProgressBar pbWeiXinLoad;


    private List<WeiXinBean> mList = new ArrayList<>();
    private CommonAdapter<WeiXinBean> adapter;
    private LoadMoreWrapper<WeiXinBean> wrapper;

    private int page = 1;


    public Object setLayout() {
        return R.layout.wei_xin_fragment;
    }

    @Override
    public void initView(View rootView) {
        initData();
        initListener();
    }


    @Override
    public void onResume() {
        super.onResume();
        isInit = true;
        LazyLoad();
    }


    @Override
    public void LazyLoad() {
        if (isInit && isVisible) {
            mList.clear();
            page = 1;
            requestDatas(page);
        }
    }


    private void initData() {
        rcvWeiXinDisplay.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvWeiXinDisplay.setItemAnimator(new DefaultItemAnimator());
        rcvWeiXinDisplay.addItemDecoration(new ListViewDecoration(getActivity()));

        adapter = new CommonAdapter<WeiXinBean>(getContext(), R.layout.wei_xin_fragment_item, mList) {
            @Override
            protected void convert(ViewHolder holder, WeiXinBean winXinBean, int position) {
                holder.setText(R.id.tv_wei_xin_fragment_layout_title, winXinBean.getTitle());
                holder.setText(R.id.tv_wei_xin_fragment_item_description, winXinBean.getDescription());
                holder.setText(R.id.tv_wei_xin_fragment_item_time, winXinBean.getCtime());
                holder.setImageUrl(R.id.iv_win_xin_fragment_item_picture, winXinBean.getPicUrl());
            }
        };

        wrapper = new LoadMoreWrapper(adapter);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.base_load_view_layout,null);
        wrapper.setLoadMoreView(view);
        wrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestDatas(page++);
            }
        });
        rcvWeiXinDisplay.setAdapter(wrapper);
    }

    private void requestDatas(int page) {

        OkHttpUtils.get()
                .url((String) Happy.getConfigurations().get(ConfigKeys.API_HOST))
                .addParams(Constants.PAGE, String.valueOf(page))
                .build()
                .execute(new GenericsCallback<WeixinNewsResponse>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (swlWeiXinRefresh.isRefreshing()) {
                            swlWeiXinRefresh.setRefreshing(false);
                        }
                        ToastUtils.showShortToast(Constants.SERVER_ERROR);
                    }

                    @Override
                    public void onResponse(WeixinNewsResponse response, int id) {
                        if (response.code == 200) {
                            if (swlWeiXinRefresh.isRefreshing()) {
                                swlWeiXinRefresh.setRefreshing(false);
                            }
                            for (int i = 0; i < response.newslist.size(); i++) {
                                mList.add(response.newslist.get(i));
                            }

                            wrapper.notifyDataSetChanged();

                        } else {
                            ToastUtils.showShortToast(response.msg);
                        }

                    }
                });
    }


    private void initListener() {
        swlWeiXinRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mList.clear();
                requestDatas(page);
            }
        });


        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (mList.size() == 0) {
                    ToastUtils.showShortToast(Constants.SERVER_ERROR);
                    return;
                }
                WeiXinBean bean = mList.get(position);
                Intent intent = new Intent(getActivity(), WeiXinNewsActivity.class);
                intent.putExtra(Constants.WEIXIN_BEAN, bean);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


    }


}
