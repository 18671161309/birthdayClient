package com.trip.happy.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiajun on 2017/11/28.
 */

public class TimeData extends BmobObject {

    public String time_content;
    public String time_current;
    public int wantMe;

    public String getTime_content() {
        return time_content;
    }

    public void setTime_content(String time_content) {
        this.time_content = time_content;
    }

    public String getTime_current() {
        return time_current;
    }

    public void setTime_current(String time_current) {
        this.time_current = time_current;
    }

    public int getWantMe() {
        return wantMe;
    }

    public void setWantMe(int wantMe) {
        this.wantMe = wantMe;
    }


}
