package com.msc.player.sourceprovider;

import android.os.Parcel;
import android.os.Parcelable;

public class Tab implements Parcelable {

    public static final Creator<Tab> CREATOR = new Creator<Tab>() {
        @Override
        public Tab createFromParcel(Parcel in) {
            return new Tab(in);
        }

        @Override
        public Tab[] newArray(int size) {
            return new Tab[size];
        }
    };
    private int id;
    private String title;

    public Tab(int id, String title) {
        this.id = id;
        this.title = title;
    }

    protected Tab(Parcel in) {
        id = in.readInt();
        title = in.readString();
    }

    public static Tab empty() {
        return new Tab(0, "");
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
    }

}
