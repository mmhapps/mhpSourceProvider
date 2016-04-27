# mhpSourceProvider
Source provider library for MH Player

#Usage
1. Pull library project
2. Add dependency: 'compile project(':sourceprovider')'
3. Add your provider service 

```xml
<service
        android:name=".<YOUR_SERVICE_NAME>"
        android:enabled="true"
        android:exported="true"
        android:label="<SERVICE_LABEL>"
        android:process=":remote">
            <intent-filter>
                <action android:name="com.msc.player.ISourceProvider" />
            </intent-filter>
</service>
```

The easiest way to implement source provider is extend from **CoreLoaderSourceProviderService** and implement 2 abstract methods

```java
protected abstract AbstractCore getCore();
protected abstract FolderWrapper getCustomFolderWrapper(Folder folder);
```

#Core sample

```java
public class Core extends AbstractCore {

    public Core(final MyApi api, final Context context) {
        super(context);
        groups.add(
                new FolderWrapper(new Folder(0, "My category title", new IconPath("image_name_in_res_dir")))
                        .tab(new TabWrapper(new Tab(0, "Tab title"), new LoaderFabric() {
                                    @Override
                                    public SourceItemLoader makeLoader() {
                                        return new SourceItemLoaderImpl();
                                    }
                                })
                        )
        );
    }

}
```

#SourceItemLoader implementation sample 

```java
public class MySearchLoader implements SourceItemLoader {

    private MyApi api;
    private String query;
    private int page;


    public ZaycevSearchLoader(MyApi api, String query) {
        this.api = api;
        this.query = query;
        page = 1;
    }

    @Override
    public List<SourceItem> loadNext() {
        List<SourceItem> list = new ArrayList<>();
        try {
            List<SourceItem> items = api.getSearch(query, page);
            if (items != null) {
                list.addAll(items);
            }
            page++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
```

#getCustomFolderWrapper()

```java
@Override
    protected FolderWrapper getCustomFolderWrapper(Folder folder) {
        FolderWrapper folderWrapper = null;
        String path = folder.getPath();
        if (!TextUtils.isEmpty(path)) {
            if (path.startsWith("YOUR_SPECIFIC_PREFIX")) {
                folderWrapper = FolderParser.getYourFolderWrapper(folder, myapi);
            }
        }
        return folderWrapper;
    }
```

#Folder parser sample for search

```java
public class FolderParser {

   static FolderWrapper getSearchParser(Folder folder, final MyApi api) {
        String path = folder.getPath();
        String[] split = path.split(":");
        if (split != null) {
            if (split.length > 1) {
                final String query = split[1];
                return new FolderWrapper(folder).tab(new TabWrapper(new Tab(0, query), new LoaderFabric() {
                    @Override
                    public SourceItemLoader makeLoader() {
                        return new SearchLoader(api, query);
                    }
                }));
            }
        }
        return null;
    }
}
```


