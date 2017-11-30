package com.trip.happy.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by xiajun on 2017/11/28.
 */

public class Friends extends BmobObject{

    public BmobFile headerUrl;
    public String friend_phone;
    public String bless_statement;
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobFile getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(BmobFile headerUrl) {
        this.headerUrl = headerUrl;
    }

    public String getFriend_phone() {
        return friend_phone;
    }

    public void setFriend_phone(String friend_phone) {
        this.friend_phone = friend_phone;
    }

    public String getBless_statement() {
        return bless_statement;
    }

    public void setBless_statement(String bless_statement) {
        this.bless_statement = bless_statement;
    }
}
