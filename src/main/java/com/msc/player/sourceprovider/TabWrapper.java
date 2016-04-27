package com.msc.player.sourceprovider;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bohdantp on 3/22/16.
 */
public class TabWrapper {
    private static final String TAG = "TabWrapper";
    private final int DEF_PART_SIZE = 200;
    public Tab tab;
    private SourceItemLoader loader;
    private LoaderFabric fabric;
    private ArrayList<SourceItem> cached;
    private int cachePos = 0;

    public TabWrapper(Tab tab, LoaderFabric fabric) {
        this.tab = tab;
        this.fabric = fabric;
        this.loader = fabric.makeLoader();
        this.cached = new ArrayList<>();
    }

    private void clearCache() {
        cached.clear();
        cachePos = 0;
        resetLoader();
    }

    private List<SourceItem> getCachedPart(int count) {
        List<SourceItem> res = new ArrayList<>();
        int size = cached.size();
        if (size > cachePos) {
            int c = Math.min(size - cachePos, count);
            res.addAll(cached.subList(cachePos, cachePos + c));
            cachePos += c;
        }
        return res;
    }

    public List<SourceItem> loadItems(LoadKind kind) {
        if (kind == LoadKind.FORCEFRESH) {
            clearCache();
        }
        if (kind == LoadKind.CACHED) {
            cachePos = 0;
        }
        if (cached.size() > cachePos + DEF_PART_SIZE) {
            Log.v(TAG, "CACHED 2nd buf: ");
            return getCachedPart(DEF_PART_SIZE);
        }
        List<SourceItem> items = loader.loadNext();
        cached.addAll(items);
        return getCachedPart(DEF_PART_SIZE);
    }

    public void resetLoader() {
        loader = fabric.makeLoader();
    }

    public int getSize() {
        int res = 0;
        if (cached != null) {
            for (SourceItem i : cached) {
                res += i.getArtist().length() * 2;
                res += i.getTitle().length() * 2;
                res += i.getPath().length() * 2;
                res += i.getIconPath().getPath().length() * 2;
                res += 100;
            }
            res += 200;
        }
        return res;
    }
}
