package com.msc.player.sourceprovider;

import android.text.TextUtils;
import android.util.LruCache;

import java.util.ArrayList;
import java.util.List;


public abstract class CoreLoaderSourceProviderService extends AbstractSourceProviderService {


    private static final String TAG = "CoreLoader";

    private LruCache<String, FolderWrapper> tempFolders = new LruCache<String, FolderWrapper>(20) {
        @Override
        protected int sizeOf(String key, FolderWrapper value) {
            int size = 1;
//            Log.v(TAG, "sizeOf: " + key + " size: " + size);
            return size;
        }

        @Override
        protected void entryRemoved(boolean evicted, String key, FolderWrapper oldValue, FolderWrapper newValue) {
//            Log.v(TAG, "entryRemoved: " + key);
            System.gc();
            super.entryRemoved(evicted, key, oldValue, newValue);
        }
    };
    //    private HashMap<String, FolderWrapper> tempFolders = new HashMap<>();
    private AbstractCore core;


    @Override
    public void onCreate() {
        super.onCreate();
        core = getCore();
        checkFavsAndModifyCore();
    }

    @Override
    protected void notifyChanges() {
        checkFavsAndModifyCore();
    }

    private void checkFavsAndModifyCore() {
        List<SourceItem> curr = loadFavourites();
        if (curr != null && curr.size() > 0) {
            if (core.getWrapperByFolderId(FOLDER_ID_FAVOURITES) == null) {
                core.insert(new FolderWrapper(new Folder(FOLDER_ID_FAVOURITES, "Favourites", new IconPath("ic_favorite_border_black_36dp")))
                        .tab(new TabWrapper(new Tab(core.tabInc(), "Favourites"), new LoaderFabric() {
                                    @Override
                                    public SourceItemLoader makeLoader() {
                                        return new FavouritesLoader();
                                    }
                                })
                        ), 0);
            }
        }
    }

    protected List<SourceItem> loadFavourites() {
        List<SourceItem> curr = ItemsSaver.get().read(FAVS_BOOK_KEY, new ArrayList<SourceItem>());
        return curr;
    }

    protected abstract AbstractCore getCore();

    @Override
    public List<SourceItem> getItems(Folder folder, Tab tab, LoadKind kind) {
        List<SourceItem> res = new ArrayList<>();
        FolderWrapper folderWrapper = null;
        if (!TextUtils.isEmpty(folder.getPath())) {
            folderWrapper = tempFolders.get(folder.getPath());
        } else {
            folderWrapper = core.getGroupWrapper(folder.getId());
        }
        if (folderWrapper != null) {
            TabWrapper tabWrapper = folderWrapper.getTabWrapper(tab.getId());
            if (tabWrapper != null) {
                List<SourceItem> audios = tabWrapper.loadItems(kind);
                if (audios != null) {
                    res.addAll(audios);
                }
            }
        }
        return res;
    }

    private FolderWrapper getCustomFolder(Folder folder) {
        String path = folder.getPath();
        if (!TextUtils.isEmpty(path)) {
            FolderWrapper folderWrapper = tempFolders.get(path);
            if (folderWrapper != null) {
                return folderWrapper;
            }
            folderWrapper = getCustomFolderWrapper(folder);
            if (folderWrapper != null) {
                tempFolders.put(folder.getPath(), folderWrapper);
                return folderWrapper;
            }
        }
        return null;
    }

    protected abstract FolderWrapper getCustomFolderWrapper(Folder folder);

    @Override
    public List<Tab> getTabs(Folder folder) {
        ArrayList<Tab> res = new ArrayList<>();
        FolderWrapper folderWrapper = null;
        if (!TextUtils.isEmpty(folder.getPath())) {
            folderWrapper = getCustomFolder(folder);
        } else {
            folderWrapper = core.getGroupWrapper(folder.getId());
        }
        if (folderWrapper != null) {
            res.addAll(folderWrapper.getTabs());
        }
        return res;
    }

    @Override
    public List<Folder> getFolders() {
        return core.getGroups();
    }

    protected void resetLoaders() {
        tempFolders.evictAll();
        core = getCore();
    }
}
