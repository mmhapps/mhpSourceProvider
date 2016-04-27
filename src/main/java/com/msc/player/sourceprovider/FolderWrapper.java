package com.msc.player.sourceprovider;

import java.util.ArrayList;
import java.util.List;

public class FolderWrapper {
    public Folder group;
    List<TabWrapper> tabs = new ArrayList<>();

    public FolderWrapper(Folder group) {
        this.group = group;
    }

    public TabWrapper getTabWrapper(int tabId) {
        for (TabWrapper t : tabs) {
            if (t.tab.getId() == tabId) {
                return t;
            }
        }
        return null;
    }

    public FolderWrapper tab(TabWrapper tab) {
        tabs.add(tab);
        return this;
    }

    public List<Tab> getTabs() {
        List<Tab> res = new ArrayList<>();
        for (TabWrapper t : tabs) {
            res.add(t.tab);
        }
        return res;
    }

    public int getSize(){
        int res = 0;
        if (tabs != null) {
            for (TabWrapper w: tabs){
                res += w.getSize();
            }
            res += 200;
        }
        return res;
    }
}
