package com.trip.happy.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.trip.core.recyclerView.CommonAdapter;
import com.trip.core.recyclerView.MultiItemTypeAdapter;
import com.trip.core.recyclerView.base.ViewHolder;
import com.trip.core.utils.ToastUtils;
import com.trip.happy.R;
import com.trip.happy.activities.PrePicActivity;
import com.trip.happy.bean.PersonCard;
import com.trip.happy.fragment.base.LazyFragment;
import com.trip.happy.view.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


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
    private CommonAdapter<PersonCard> personCardCommonAdapter;
    protected boolean isInit = false;
    private List<PersonCard> list;

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
        personCardCommonAdapter = new CommonAdapter<PersonCard>(getActivity(), R.layout.recyclerview_item, buildData()) {
            @Override
            protected void convert(ViewHolder holder, PersonCard personCard, int position) {
                holder.setImageUrl(R.id.user_avatar, personCard.avatarUrl);
                holder.setText(R.id.user_name, personCard.name);
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
                        .load(list.get(position).avatarUrl).into(viewById);
                alertDialog.show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    @Override
    public void LazyLoad() {
        if (isInit && isVisible) {

        }
    }

    //生成6个明星数据，这些Url地址都来源于网络
    private List<PersonCard> buildData() {

        String[] names = {"邓紫棋", "范冰冰", "杨幂"};
        String[] imgUrs = {
                "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1477122795&di=f740bd484870f9bcb0cafe454a6465a2&src=http://tpic.home.news.cn/xhCloudNewsPic/xhpic1501/M08/28/06/wKhTlVfs1h2EBoQfAAAAAF479OI749.jpg",
                "https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=fd90a83e900a304e4d22a7fae1c9a7c3/d01373f082025aafa480a2f1fcedab64034f1a5d.jpg",
                "https://ss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=005560fc8b5494ee982208191df4e0e1/c2fdfc039245d68827b453e7a3c27d1ed21b243b.jpg",
        };

        list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            PersonCard p = new PersonCard();
            p.avatarUrl = imgUrs[i];
            p.name = names[i];
            list.add(p);
        }

        return list;
    }


    @Override
    public void onResume() {
        super.onResume();
        isInit = true;
        LazyLoad();
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
