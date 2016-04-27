// ISourceProvider.aidl
package com.msc.player.sourceprovider;

import com.msc.player.sourceprovider.SourceItem;
import com.msc.player.sourceprovider.Folder;
import com.msc.player.sourceprovider.Tab;
import com.msc.player.sourceprovider.LoadKind;

interface ISourceProvider {

    String providerName();

    List<Folder> getFolders();

    List<Tab> getTabs(in Folder folder);

    List<SourceItem> getItems(in Folder folder, in Tab tab, in LoadKind loadKind);

    void processItems(in String operation, in List<SourceItem> items);

    void goToSettings();

}
