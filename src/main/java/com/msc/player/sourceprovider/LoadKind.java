package com.msc.player.sourceprovider;

import android.os.Parcel;
import android.os.Parcelable;

public enum LoadKind implements Parcelable {

    FORCEFRESH, CACHED, MORE;

    public static final Creator<LoadKind> CREATOR = new Creator<LoadKind>() {
        @Override
        public LoadKind createFromParcel(final Parcel source) {
            return LoadKind.values()[source.readInt()];
        }

        @Override
        public LoadKind[] newArray(final int size) {
            return new LoadKind[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }
    
}
