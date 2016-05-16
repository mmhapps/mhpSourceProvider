package com.msc.player.sourceprovider;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public abstract class AbstractSourceProviderService extends Service {

    public static final int FOLDER_ID_SETTINGS = 999;
    public static final int FOLDER_ID_FAVOURITES = 9999;
    public static final String ACTION_REFRESH_BINDERS = "action.refresh_binders";
    public static final String ACTION_ADD_TO_FAVOURITES = "addtofavs";
    public static final String ACTION_REMOVE_FROM_FAVOURITES = "removefromfavs";
    public static final String FAVS_BOOK_KEY = "favs";
    private ISourceProvider.Stub mBinder = new ISourceProvider.Stub() {
        @Override
        public String providerName() throws RemoteException {
            return getProviderName();
        }

        @Override
        public List<Folder> getFolders() throws RemoteException {
            return getFolderList();
        }

        @Override
        public List<Tab> getTabs(Folder folder) throws RemoteException {
            List<Tab> res = new ArrayList<>();
            List<Tab> tabs = AbstractSourceProviderService.this.getTabs(folder);
            if (tabs != null) {
                res.addAll(tabs);
            }
            return res;
        }

        @Override
        public List<SourceItem> getItems(Folder folder, Tab tab, LoadKind loadKind) throws RemoteException {
            List<SourceItem> res = new ArrayList<>();
            List<SourceItem> items = AbstractSourceProviderService.this.getItems(folder, tab, loadKind);
            if (items != null) {
                res.addAll(items);
            }
            return res;
        }

        @Override
        public void goToSettings() throws RemoteException {
            showSettings();
        }

        @Override
        public void processItems(String operation, List<SourceItem> items) throws RemoteException {
            AbstractSourceProviderService.this.processItems(operation, items);
        }
    };

    public AbstractSourceProviderService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
    }

    protected abstract void showSettings();

    @Override
    public final IBinder onBind(Intent intent) {
        return mBinder;
    }

    public abstract List<SourceItem> getItems(Folder folder, Tab tab, LoadKind kind);

    public abstract String getProviderName();

    public abstract List<Tab> getTabs(Folder folder);

    public abstract List<Folder> getFolders();

    protected boolean hasSettingsItem() {
        return false;
    }

    private List<Folder> getFolderList() {
        List<Folder> res = new ArrayList<>();
        List<Folder> folders = getFolders();
        if (folders != null) {
            res.addAll(folders);
        }
        if (hasSettingsItem()) {
            res.add(new Folder(FOLDER_ID_SETTINGS, "Settings", new IconPath("menu_settings_icon")));
        }
        return res;
    }

    public void processItems(String operation, List<SourceItem> items) {
        if (ACTION_ADD_TO_FAVOURITES.equals(operation)) {
            List<SourceItem> curr = Paper.book().read(FAVS_BOOK_KEY, new ArrayList<SourceItem>());
            curr.addAll(items);
            Paper.book().write(FAVS_BOOK_KEY, curr);
            notifyChanges();
        }
        if (ACTION_REMOVE_FROM_FAVOURITES.equals(operation)) {
            List<SourceItem> curr = Paper.book().read(FAVS_BOOK_KEY, new ArrayList<SourceItem>());
            ArrayList<SourceItem> res = new ArrayList<>();
            for (SourceItem i : curr) {
                boolean found = false;
                for (SourceItem c : items) {
                    if (i.getPath().equals(c.getPath())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    res.add(i);
                }
            }
            Paper.book().write(FAVS_BOOK_KEY, res);
            notifyChanges();
        }
    }

    protected void notifyChanges() {

    }
}
