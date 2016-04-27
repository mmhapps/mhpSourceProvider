package com.msc.player.sourceprovider;

import android.text.TextUtils;

public class IconPath {

    private String path = "";
    private Kind kind = Kind.EMPTY;

    public IconPath() {
    }

    public IconPath(String path) {
        this.path = path;
        if (path.startsWith("http")) {
            kind = Kind.WEB;
        } else {
            kind = Kind.LOCAL;
        }
        if (TextUtils.isEmpty(path)) {
            kind = Kind.EMPTY;
        }
    }

    public Kind getKind() {
        return kind;
    }

    public String getPath() {
        return path;
    }

    public enum Kind {
        LOCAL, WEB, EMPTY
    }
}
