package com.msc.player.sourceprovider;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bohdantp on 10/20/16.
 */

public class ItemsSaver {

    private static boolean inited = false;
    private static ItemsSaver instance;
    private Context context;

    private ItemsSaver(Context context) {
        this.context = context;
        instance = this;
    }

    public static void init(Context context) {
        instance = new ItemsSaver(context);
        inited = true;
    }

    public static synchronized ItemsSaver get() {
        if (!inited) {
            throw new IllegalStateException("You should call init(Context) before use get()");
        }
        return instance;
    }

    public void write(String key, ArrayList<SourceItem> items) {
        JSONArray a = new JSONArray();
        for (SourceItem i : items) {
            JSONObject j = i.saveToJSONObject();
            a.put(j);
        }
        PrefUtil.saveString(context, "favcat", key, a.toString());
    }

    public ArrayList<SourceItem> read(String key, ArrayList<SourceItem> defaultValue) {
        String read = PrefUtil.getString(context, "favcat", key);
        if (!TextUtils.isEmpty(read)) {
            try {
                ArrayList<SourceItem> res = new ArrayList<>();
                JSONArray a = new JSONArray(read);
                for (int i = 0; i < a.length(); i++) {
                    JSONObject jsonObject = a.optJSONObject(i);
                    if (jsonObject != null) {
                        SourceItem item = new SourceItem();
                        item.readFromJSONObject(jsonObject);
                        res.add(item);
                    }
                }
                return res;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return defaultValue;
    }
}
