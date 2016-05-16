package com.msc.player.sourceprovider;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCore {

    public static final int TAB_ID_LOCAL_TYPE_START = 100000;

    protected List<FolderWrapper> groups = new ArrayList<>();
    private Context context;

    private int folderId = 0;
    private int tabId = 0;

    public AbstractCore(Context context) {
        this.context = context;
    }

    public AbstractCore(Context context, boolean noDownload) {
        this(context);
        if (noDownload) {
            tabId = TAB_ID_LOCAL_TYPE_START;
        }
    }

    protected int folderInc() {
        folderId++;
        return folderId;
    }

    protected int tabInc() {
        tabId++;
        return tabId;
    }

    public FolderWrapper getGroupWrapper(int groupId) {
        for (FolderWrapper w : groups) {
            if (w.group.getId() == groupId) {
                return w;
            }
        }
        return null;
    }

    public List<Folder> getGroups() {
        List<Folder> res = new ArrayList<>();
        for (FolderWrapper w : groups) {
            res.add(w.group);
        }
        return res;
    }

    public void insert(FolderWrapper wrapper, int pos) {
        groups.add(pos, wrapper);
    }

    public FolderWrapper getWrapperByFolderId(int folderId) {
        for (FolderWrapper w : groups) {
            if (w.group.getId() == folderId) {
                return w;
            }
        }
        return null;
    }

}
