package com.trip.happy.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by xiajun on 2017/11/24.
 */

public class PersonCard implements Parcelable {

    public String avatarUrl; //明显头像的Url
    public String name;  //明显的名字
    public int imgHeight;  //头像图片的高度

    public PersonCard(){}

    protected PersonCard(Parcel in) {
        avatarUrl = in.readString();
        name = in.readString();
        imgHeight = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatarUrl);
        dest.writeString(name);
        dest.writeInt(imgHeight);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PersonCard> CREATOR = new Creator<PersonCard>() {
        @Override
        public PersonCard createFromParcel(Parcel in) {
            return new PersonCard(in);
        }

        @Override
        public PersonCard[] newArray(int size) {
            return new PersonCard[size];
        }
    };
}
