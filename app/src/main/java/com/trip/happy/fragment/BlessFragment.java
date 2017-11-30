package com.trip.happy.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.trip.core.utils.SPUtils;
import com.trip.core.utils.TimeUtil;
import com.trip.core.utils.ToastUtils;
import com.trip.happy.MainActivity;
import com.trip.happy.R;
import com.trip.happy.adpter.TimeLineAdapter;
import com.trip.happy.bean.OrderStatus;
import com.trip.happy.bean.Orientation;
import com.trip.happy.bean.TimeData;
import com.trip.happy.bean.TimeLineModel;
import com.trip.happy.constant.Constants;
import com.trip.happy.fragment.base.LazyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by xiajun on 2017/11/20.
 */

public class BlessFragment extends LazyFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeData> mDataList = new ArrayList<>();
    private Orientation mOrientation;
    private boolean mWithLinePadding = true;

    public static BlessFragment getInstance() {
        BlessFragment fragment = new BlessFragment();
        return fragment;
    }

    public Object setLayout() {
        return R.layout.fragment_bless;
    }

    @Override
    public void initView(View rootView) {


        mOrientation = Orientation.VERTICAL;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        mTimeLineAdapter = new TimeLineAdapter(mDataList, mOrientation, mWithLinePadding);
        recyclerView.setAdapter(mTimeLineAdapter);
    }


    protected boolean isInit = false;

    @Override
    public void LazyLoad() {
        if (isInit && isVisible) {
            mDataList.clear();
            initData();
        }
    }

    private void initData() {
        String time = (String) SPUtils.get(Constants.CURRENT_TIME, "");
        if (time.equals(TimeUtil.getCurrentTime())) {
            requestServer();
        } else {
            initDialog();
        }
    }

    private AlertDialog dialog;
    private EditText mText;
    private Button button;
    private CheckBox mCheck;

    private void initDialog() {
        if (dialog == null) {
            dialog = customDialog(R.layout.dialog_layout);
            dialog.setCancelable(false);
            mText = dialog.findViewById(R.id.input_content);
            button = dialog.findViewById(R.id.sure_btn);
            mCheck = dialog.findViewById(R.id.box);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String content = mText.getText().toString();

                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showShortToast("内容不能为空");
                    return;
                }

                if (mCheck.isChecked()) {
                    ToastUtils.showShortToast("一直想着你");
                    CommitData(content);
                } else {
                    new AlertDialog.Builder(getContext()).setMessage("确定不想吗? 5555")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    CommitData(content);
                                    dialogInterface.dismiss();
                                }
                            }).setNegativeButton("在考虑一下", null).show();
                }
            }
        });

    }

    private void CommitData(String content) {
        mDialog.show();
        String time = TimeUtil.getCurrentTime();
        TimeData timeData = new TimeData();
        timeData.setTime_current(time);
        timeData.setTime_content(content);
        if (mCheck.isChecked()) {
            timeData.setWantMe(1);
        } else {
            timeData.setWantMe(0);
        }

        SPUtils.put(Constants.CURRENT_TIME, time);
        timeData.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    mDialog.dismiss();
                }
                if (e == null) {
                    requestServer();
                } else {
                    ToastUtils.showShortToast(e.getMessage());
                }
            }
        });
    }


    private void requestServer() {
        mDialog.show();
        BmobQuery<TimeData> bmobQuery = new BmobQuery<TimeData>();
        bmobQuery.findObjects(new FindListener<TimeData>() {
            @Override
            public void done(List<TimeData> list, BmobException e) {
                mDialog.dismiss();
                if (e == null) {
                    for (int i = list.size() - 1; i >= 0; i--) {
                        mDataList.add(list.get(i));
                    }
                    mTimeLineAdapter.updata(mDataList);
                }else{
                    ToastUtils.showShortToast(e.getMessage());
                }
            }
        });

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
}
