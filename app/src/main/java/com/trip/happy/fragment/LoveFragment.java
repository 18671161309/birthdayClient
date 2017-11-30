package com.trip.happy.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.trip.core.recyclerView.CommonAdapter;
import com.trip.core.recyclerView.MultiItemTypeAdapter;
import com.trip.core.recyclerView.base.ViewHolder;
import com.trip.core.utils.ToastUtils;
import com.trip.happy.R;
import com.trip.happy.bean.Picture;
import com.trip.happy.fragment.base.LazyFragment;
import com.trip.happy.view.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by xiajun on 2017/11/20.
 */

public class LoveFragment extends LazyFragment {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.floataction_grid)
    FloatingActionButton floatactionGrid;
    @BindView(R.id.floataction_list)
    FloatingActionButton floatactionList;
    @BindView(R.id.swl_wei_xin_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private CommonAdapter<Picture> personCardCommonAdapter;
    protected boolean isInit = false;
    private List<Picture> lists = new ArrayList<>();

    public static LoveFragment getInstance() {
        LoveFragment fragment = new LoveFragment();
        return fragment;
    }

    public Object setLayout() {
        return R.layout.fragment_love;
    }


    public void setLayout(RecyclerView.LayoutManager manager) {
        if (manager == null) {
            manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        recyclerview.setLayoutManager(manager);
    }

    @Override
    public void initView(View rootView) {


        setLayout(null);
        initListener();

    }

    private void initListener() {
        personCardCommonAdapter = new CommonAdapter<Picture>(getActivity(), R.layout.recyclerview_item, lists) {
            @Override
            protected void convert(ViewHolder holder, Picture personCard, int position) {
                holder.setImageUrl(R.id.user_avatar, personCard.getPic_url().getUrl());
                holder.setText(R.id.user_name, personCard.getPic_desc());
            }
        };
        recyclerview.setAdapter(personCardCommonAdapter);


        personCardCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                AlertDialog alertDialog = customDialog(R.layout.dialog_phone_layout);
                PhotoView viewById = alertDialog.findViewById(R.id.img);
                viewById.enable();
                viewById.setAnimaDuring(800);
                Glide.with(getContext())
                        .load(lists.get(position).getPic_url().getUrl()).into(viewById);
                alertDialog.show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                buildData();
            }
        });
    }


    @Override
    public void LazyLoad() {
        if (isInit && isVisible) {
            buildData();
        }
    }

    private List<Picture> buildData() {
        mDialog.show();
        BmobQuery<Picture> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Picture>() {
            @Override
            public void done(List<Picture> list, BmobException e) {
                mDialog.dismiss();
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (e == null) {
                    lists = list;
                    personCardCommonAdapter.updatas(list);
                }
            }
        });

        return lists;
    }


    @Override
    public void onResume() {
        super.onResume();
        isInit = true;
        LazyLoad();
    }

    @Override
    public void onPause() {
        super.onPause();
        isInit = false;
    }

    @OnClick({R.id.floataction_list, R.id.floataction_grid})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.floataction_list:
                setLayout(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                break;
            case R.id.floataction_grid:
                setLayout(new GridLayoutManager(getActivity(), 2));
                break;
        }
    }


}
