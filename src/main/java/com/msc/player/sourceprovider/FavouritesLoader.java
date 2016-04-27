package com.msc.player.sourceprovider;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class FavouritesLoader implements SourceItemLoader {

    private boolean loaded = false;

    @Override
    public List<SourceItem> loadNext() {
        if (loaded) {
            return new ArrayList<SourceItem>();
        }
        ArrayList<SourceItem> items = Paper.book().read(AbstractSourceProviderService.FAVS_BOOK_KEY, new ArrayList<SourceItem>());
        loaded = true;
        return items;
    }
}
