package com.alone.util;

import com.alibaba.fastjson.JSONObject;
import com.alone.pojo.base.EnvironmentInfo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hetilong
 * @Date: 2021/12/4 16:41
 */
public class FileUtil {

    public String getCurl(String path) throws IOException {
        String content = "";
        StringBuilder builder = new StringBuilder();

        File file = new File(path);
        InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(streamReader);

        while ((content = bufferedReader.readLine()) != null) {
            builder.append(content+"\n");
        }

        return builder.toString();
    }

    public EnvironmentInfo getCurlObject(String path) throws IOException {
        String content = "";
        StringBuilder builder = new StringBuilder();

        File file = new File(path);
        InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        List<String> curlList = new ArrayList<>();
        while ((content = bufferedReader.readLine()) != null) {
            builder.append(content+"\n");
            if (content.equals("")){
                curlList.add(builder.toString());
                builder.delete(0,builder.length());
            }
        }
        Map<String,Object> curlMap = new HashMap<>();
        for (String curl :curlList){
            String[] a = curl.split(" = ");
            curlMap.put(a[0],a[1]);
        }

        JSONObject json = new JSONObject(curlMap);
        EnvironmentInfo environmentInfo = JSONObject.parseObject(json.toString()
                ,EnvironmentInfo.class);

        return environmentInfo;
    }
}
