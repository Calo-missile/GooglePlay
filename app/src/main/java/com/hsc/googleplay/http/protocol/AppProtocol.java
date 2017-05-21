package com.hsc.googleplay.http.protocol;

import com.hsc.googleplay.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 应用网络请求
 * Created by 15827 on 2017/5/21.
 */

public class AppProtocol extends BaseProtocol<ArrayList<AppInfo>> {

    @Override
    public String getKey() {
        return "app";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<AppInfo> parseData(String result) {

        try {
            JSONArray ja = new JSONArray(result);

            ArrayList<AppInfo> list = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject j0 = ja.getJSONObject(i);

                AppInfo info = new AppInfo();
                info.des = j0.getString("des");
                info.downloadUrl = j0.getString("downloadUrl");
                info.iconUrl = j0.getString("iconUrl");
                info.id = j0.getString("id");
                info.name = j0.getString("name");
                info.packageName = j0.getString("packageName");
                info.size = j0.getLong("size");
                info.stars = (float) j0.getDouble("stars");
                list.add(info);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
