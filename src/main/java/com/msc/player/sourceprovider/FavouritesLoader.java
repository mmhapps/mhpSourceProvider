package com.msc.player.sourceprovider;

import java.util.ArrayList;
import java.util.List;


public class FavouritesLoader implements SourceItemLoader {

    private boolean loaded = false;

    @Override
    public List<SourceItem> loadNext() {
        if (loaded) {
            return new ArrayList<SourceItem>();
        }
        ArrayList<SourceItem> items = ItemsSaver.get().read(AbstractSourceProviderService.FAVS_BOOK_KEY, new ArrayList<SourceItem>());
        loaded = true;
        return items;
    }
}
