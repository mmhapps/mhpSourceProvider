package com.msc.player.sourceprovider;

import android.os.Parcel;
import android.os.Parcelable;

public class Folder implements Parcelable {

    public static final Creator<Folder> CREATOR = new Creator<Folder>() {
        @Override
        public Folder createFromParcel(Parcel in) {
            return new Folder(in);
        }

        @Override
        public Folder[] newArray(int size) {
            return new Folder[size];
        }
    };
    private int id;
    private String title;
    private String path = "";
    private boolean search = false;
    private IconPath iconPath = new IconPath("");

    public Folder(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Folder(int id, String title, IconPath iconPath) {
        this(id, title);
        this.iconPath = iconPath;
    }

    public Folder(int id, String title, boolean search) {
        this(id, title);
        this.search = search;
    }

    public Folder(int id, String title, boolean search, IconPath iconPath) {
        this(id, title, iconPath);
        this.search = search;
    }

    public Folder(int id, String title, String path) {
        this(id, title);
        this.path = path;
    }

    protected Folder(Parcel in) {
        id = in.readInt();
        title = in.readString();
        path = in.readString();
        search = in.readInt() == 1;
        this.iconPath = new IconPath(in.readString());
    }

    public IconPath getIconPath() {
        return iconPath;
    }

    public boolean isSearch() {
        return search;
    }

    public String getPath() {
        return path;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(path);
        dest.writeInt(search ? 1 : 0);
        dest.writeString(iconPath.getPath());
    }
}
