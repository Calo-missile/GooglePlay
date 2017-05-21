package com.hsc.googleplay.http.protocol;

import com.hsc.googleplay.http.HttpHelper;
import com.hsc.googleplay.utils.IOUtils;
import com.hsc.googleplay.utils.StringUtils;
import com.hsc.googleplay.utils.UIUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 网络请求基类
 * Created by 15827 on 2017/5/20.
 */

public abstract class BaseProtocol<T> {
    
    public T getData(int index) {
        //先判断是否有缓存，有的话就加载
        String result = getCache(index);
        if (StringUtils.isEmpty(result)) {
            //请求服务器
            result = getDataFromServer(index);
        }
        if (result != null) {
            T data = parseData(result);
            return data;
        }
        return null;
    }
    
    //从网络获取数据
    //index表示从哪个位置开始返回20条数据，用于分页
    private String getDataFromServer(int index) {
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey()
                + "?index=" + index + getParams());
        if (httpResult != null) {
            String result = httpResult.getString();
            //LogUtils.i("访问结果：" + result);

            // 写缓存
            if (!StringUtils.isEmpty(result)) {
                setCache(index, result);
            }
            return result;
        }
        return null;
    }
    
    //获取网络链接关键字，子类必须实现
    public abstract String getKey();
    
    //获取网络链接参数，子类必须实现
    public abstract String getParams();
    
    //写缓存
    //以url为key，以json为value
    public void setCache(int index,String json) {
        //以url为文件名，以json为内容，保存到本地
        File cacheDir = UIUtils.getContext().getCacheDir();//本应用的缓存文件夹

        //生成缓存文件
        File cacheFile = new File(cacheDir, getKey()
                + "?index=" + index + getParams());
        //然后进行读写

        FileWriter writer = null;
        try {
            writer = new FileWriter(cacheFile);
            
            //缓存失效截止时间
            long deadline = System.currentTimeMillis() + 30 * 60 * 1000;//半个小时有效时间
            writer.write(deadline + "/n");//在第一行写入缓存时间，换行
            writer.write(json);//写入json
            writer.flush();//刷新
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }
    }

    /*//读缓存
    public String getCache(int index) {
        //以url为文件名，以json为内容，保存到本地
        File cacheDir = UIUtils.getContext().getCacheDir();//本应用的缓存文件夹

        //生成缓存文件
        File cacheFile = new File(cacheDir, getKey()
                + "?index=" + index + getParams());
        //然后进行读写
        
        //判断缓存是否存在
        if (cacheFile.exists()) {
            //判断缓存是否有效
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                String deadline = reader.readLine();//  读取第一行有效期
                long linetime = Long.parseLong(deadline);
                
                if (System.currentTimeMillis() < linetime) {
                    //缓存有效
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }
        }
        return null;
    }*/

    // 读缓存
    public String getCache(int index) {
        // 以url为文件名, 以json为文件内容,保存在本地
        File cacheDir = UIUtils.getContext().getCacheDir();// 本应用的缓存文件夹
        // 生成缓存文件
        File cacheFile = new File(cacheDir, getKey() + "?index=" + index
                + getParams());

        // 判断缓存是否存在
        if (cacheFile.exists()) {
            // 判断缓存是否有效
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                String deadline = reader.readLine();// 读取第一行的有效期
                long deadtime = Long.parseLong(deadline);

                if (System.currentTimeMillis() < deadtime) {// 当前时间小于截止时间,
                    // 说明缓存有效
                    // 缓存有效
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }

                    return sb.toString();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }

        }

        return null;
    }

    //解析json数据
    public abstract T parseData(String result);
}
