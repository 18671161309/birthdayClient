package com.trip.happy.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by xiajun on 2017/11/24.
 */

public class Picture  extends BmobObject {

    public BmobFile pic_url;
    public String pic_desc;

    public Picture(BmobFile pic_url, String pic_desc) {
        this.pic_url = pic_url;
        this.pic_desc = pic_desc;
    }


    public BmobFile getPic_url() {
        return pic_url;
    }

    public void setPic_url(BmobFile pic_url) {
        this.pic_url = pic_url;
    }

    public String getPic_desc() {
        return pic_desc;
    }

    public void setPic_desc(String pic_desc) {
        this.pic_desc = pic_desc;
    }
}
