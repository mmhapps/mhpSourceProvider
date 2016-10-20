package com.msc.player.sourceprovider;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class SourceItem implements Parcelable {

    public static final Creator<SourceItem> CREATOR = new Creator<SourceItem>() {
        @Override
        public SourceItem createFromParcel(Parcel in) {
            return new SourceItem(in);
        }

        @Override
        public SourceItem[] newArray(int size) {
            return new SourceItem[size];
        }
    };
    private String title = "";
    private String artist = "";
    private ItemKind kind = ItemKind.PLAYITEM;
    private String path = "";

    private int duration = 0; // in seconds
    private IconPath iconPath = new IconPath("");


    SourceItem() {
    }

    protected SourceItem(Parcel in) {
        this.title = in.readString();
        this.artist = in.readString();
        this.kind = ItemKind.values()[in.readInt()];
        this.path = in.readString();
        this.duration = in.readInt();
        this.iconPath = new IconPath(in.readString());
    }

    void readFromJSONObject(JSONObject in) {
        this.title = in.optString("title");
        this.artist = in.optString("artist");
        this.kind = ItemKind.values()[in.optInt("kind")];
        this.path = in.optString("path");
        this.duration = in.optInt("duration");
        this.iconPath = new IconPath(in.optString("iconPath"));
    }

    JSONObject saveToJSONObject() {
        JSONObject res = new JSONObject();
        try {
            res.put("title", title);
            res.put("artist", artist);
            res.put("kind", kind.ordinal());
            res.put("path", path);
            res.put("duration", duration);
            res.put("iconPath", iconPath.getPath());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    public IconPath getIconPath() {
        return iconPath;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SourceItem)) {
            return false;
        }
        SourceItem i = (SourceItem) o;
        if (title != null) {
            if (!(title.equals(i.title))) {
                return false;
            }
        } else if (i.title != null) {
            return false;
        }
        if (artist != null) {
            if (!(artist.equals(i.artist))) {
                return false;
            }
        } else if (i.artist != null) {
            return false;
        }
        if (path != null) {
            if (!(path.equals(i.path))) {
                return false;
            }
        } else if (i.path != null) {
            return false;
        }
        if (kind != i.kind) {
            return false;
        }
        if (duration != i.duration) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int res = 0;
        res += title.hashCode();
        res += artist.hashCode();
        res += kind.ordinal();
        res += path.hashCode();
        res += duration;
        return res;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public ItemKind getKind() {
        return kind;
    }

    public String getPath() {
        return path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeInt(kind.ordinal());
        dest.writeString(path);
        dest.writeInt(duration);
        dest.writeString(iconPath.getPath());
    }

    public boolean isFolder() {
        return kind == ItemKind.FOLDER;
    }

    public Folder getFolder() {
        return new Folder((int) System.currentTimeMillis(), title, path);
    }

    public enum ItemKind {
        FOLDER, PLAYITEM
    }

    public static class Builder {
        private SourceItem res;

        public Builder() {
            res = new SourceItem();
        }

        public static Builder folder() {
            Builder res = new Builder();
            return res.kind(ItemKind.FOLDER);
        }

        public static Builder playItem() {
            Builder res = new Builder();
            return res.kind(ItemKind.PLAYITEM);
        }

        public SourceItem build() {
            return res;
        }

        public Builder title(String title) {
            res.title = title;
            return this;
        }

        public Builder artist(String artist) {
            res.artist = artist;
            return this;
        }

        public Builder path(String path) {
            res.path = path;
            return this;
        }

        public Builder iconPath(String iconPath) {
            res.iconPath = new IconPath(iconPath);
            return this;
        }

        public Builder duration(int duration) {
            res.duration = duration;
            return this;
        }

        public Builder kind(ItemKind kind) {
            res.kind = kind;
            return this;
        }
    }

}
