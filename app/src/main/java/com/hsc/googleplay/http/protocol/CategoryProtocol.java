package com.hsc.googleplay.http.protocol;

import com.hsc.googleplay.domain.CategoryInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 分类请求网络
 * Created by 15827 on 2017/5/23.
 */

public class CategoryProtocol extends BaseProtocol<ArrayList<CategoryInfo>> {
    @Override
    public String getKey() {
        return "category";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<CategoryInfo> parseData(String result) {
        //LogUtils.d("CategoryProtocol测试：" + result);
        try {
            JSONArray ja = new JSONArray(result);//最外面的数组

            ArrayList<CategoryInfo> list = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);//每组对象 大括号
                //初始化标题
                if (jo.has("title")) {//判断是否有title这个字段
                    CategoryInfo titleInfo = new CategoryInfo();
                    titleInfo.title = jo.getString("title");
                    titleInfo.isTitle = true;
                    list.add(titleInfo);
                    //LogUtils.d("原本是true：-----"+titleInfo.isTitle);
                }
                
                //初始化分类对象
                if (jo.has("infos")) {
                    //每组jo里的infos数组
                    JSONArray ja1 = jo.getJSONArray("infos");
                    for (int j = 0; j < ja1.length(); j++) {
                        //infos数组下的大括号
                        JSONObject jo1 = ja1.getJSONObject(j);
                        CategoryInfo info = new CategoryInfo();
                        info.name1 = jo1.getString("name1");
                        info.name2 = jo1.getString("name2");
                        info.name3 = jo1.getString("name3");
                        info.url1 = jo1.getString("url1");
                        info.url2 = jo1.getString("url2");
                        info.url3 = jo1.getString("url3");
                        info.isTitle = false;
                        list.add(info);
                        //LogUtils.d("原本是false：-----"+info.isTitle);
                    }
                }
                
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
