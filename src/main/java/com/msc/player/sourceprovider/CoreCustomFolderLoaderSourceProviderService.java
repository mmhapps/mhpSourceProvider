package com.msc.player.sourceprovider;

import android.text.TextUtils;
import android.util.Pair;

import java.util.HashMap;

/**
 * Created by bohdantp on 11/2/16.
 */

public abstract class CoreCustomFolderLoaderSourceProviderService extends CoreLoaderSourceProviderService {

    public static final String CUSTOM_FOLDER_KEY_SEARCH = "search";
    private static final String DIVIDER_MARKER = "[div]";
    private HashMap<String, FolderWrapperFactory> factories = new HashMap<>();

    private static Pair<String, String> splitData(String s) {
        int i = s.indexOf(DIVIDER_MARKER);
        if (i > -1) {
            String key = s.substring(0, i);
            String data = s.substring(i + DIVIDER_MARKER.length());
            return new Pair<>(key, data);
        }
        return null;
    }

    public static String makeFolderPathWithData(String folderKey, String data) {
        return folderKey + DIVIDER_MARKER + data;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerAllCustomFolderWrapperFactories(factories);
    }

    protected abstract void registerAllCustomFolderWrapperFactories(HashMap<String, FolderWrapperFactory> factories);

    @Override
    protected final FolderWrapper getCustomFolderWrapper(Folder folder) {
        FolderWrapper folderWrapper = null;
        String path = folder.getPath();
        if (!TextUtils.isEmpty(path)) {
            if (path.startsWith(CUSTOM_FOLDER_KEY_SEARCH)) {
                path = path.replaceFirst(":", DIVIDER_MARKER);
            }
            Pair<String, String> splittedData = splitData(path);
            if (splittedData != null) {
                FolderWrapperFactory folderWrapperFactory = factories.get(splittedData.first);
                if (folderWrapperFactory != null) {
                    folderWrapper = folderWrapperFactory.createCustom(folder, splittedData.second);
                }
            }

        }
        return folderWrapper;
    }

    public interface FolderWrapperFactory {
        public FolderWrapper createCustom(Folder infolder, String data);
    }
}
