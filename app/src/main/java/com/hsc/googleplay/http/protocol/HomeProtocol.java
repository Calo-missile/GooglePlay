package com.hsc.googleplay.http.protocol;

import com.hsc.googleplay.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 首页网络数据解析
 * Created by 15827 on 2017/5/20.
 */

public class HomeProtocol extends BaseProtocol<ArrayList<AppInfo>> {
    @Override
    public String getKey() {
        return "home";
    }

    @Override
    public String getParams() {
        return "";// 如果没有参数,就传空串,不要传null
    }

    @Override
    public ArrayList<AppInfo> parseData(String result) {
        // Gson, JsonObject
        // 使用JsonObject解析方式: 如果遇到{},就是JsonObject;如果遇到[], 就是JsonArray
        try {
            JSONObject jo = new JSONObject(result);

            //解析应用列表数据
            JSONArray ja = jo.getJSONArray("list");
            ArrayList<AppInfo> appInfolist = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject j01 = ja.getJSONObject(i);

                AppInfo info = new AppInfo();
                info.des = j01.getString("des");
                info.downloadUrl = j01.getString("downloadUrl");
                info.iconUrl = j01.getString("iconUrl");
                info.id = j01.getString("id");
                info.name = j01.getString("name");
                info.packageName = j01.getString("packageName");
                info.size = j01.getLong("size");
                info.stars = (float) j01.getDouble("stars");
                
                appInfolist.add(info);
            }

            //舒适化轮播条的数据
            JSONArray ja1 = jo.getJSONArray("picture");
            ArrayList<String> pictures = new ArrayList<>();
            for (int i = 0; i < ja1.length(); i++) {
                String pic = ja1.getString(i);
                pictures.add(pic);
            }
            return appInfolist;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
