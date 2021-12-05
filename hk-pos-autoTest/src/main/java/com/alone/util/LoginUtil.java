package com.alone.util;

import com.alone.pojo.base.LoginInfo;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: hetilong
 * @Date: 2021/12/5 12:58
 */
public class LoginUtil {

    public Map<String, LoginInfo> getLoginInfo(String path){
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));
            prop.load(in);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                in.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Map<String,String> map_dev = new HashMap<>();
        Map<String,String> map_test = new HashMap<>();
        Map<String,String> map_stage = new HashMap<>();
        Enumeration<Object> keys = prop.keys();
        while (keys.hasMoreElements()){
            String s = (String) keys.nextElement();
            if(s.contains("dev")){
                map_dev.put(s,prop.getProperty(s));
            }else if(s.contains("test")){
                map_test.put(s,prop.getProperty(s));
            }else if(s.contains("stage")){
                map_stage.put(s,prop.getProperty(s));
            }
        }

        LoginInfo loginInfo_dev = new LoginInfo();
        loginInfo_dev.setLoginId(map_dev.get("dev.loginId"));
        loginInfo_dev.setPassword(map_dev.get("dev.password"));
        loginInfo_dev.setTerminalCode(map_dev.get("dev.terminalCode"));
        loginInfo_dev.setTerminalId(map_dev.get("dev.terminalId"));

        LoginInfo loginInfo_test = new LoginInfo();
        loginInfo_test.setLoginId(map_test.get("test.loginId"));
        loginInfo_test.setPassword(map_test.get("test.password"));
        loginInfo_test.setTerminalCode(map_test.get("test.terminalCode"));
        loginInfo_test.setTerminalId(map_test.get("test.terminalId"));

        LoginInfo loginInfo_stage = new LoginInfo();
        loginInfo_stage.setLoginId(map_stage.get("stage.loginId"));
        loginInfo_stage.setPassword(map_stage.get("stage.password"));
        loginInfo_stage.setTerminalCode(map_stage.get("stage.terminalCode"));
        loginInfo_stage.setTerminalId(map_stage.get("stage.terminalId"));

        Map<String,LoginInfo> loginInfoMap = new HashMap<>();
        loginInfoMap.put("DEV",loginInfo_dev);
        loginInfoMap.put("TEST",loginInfo_test);
        loginInfoMap.put("STAGE",loginInfo_stage);

        return loginInfoMap;
    }
}
