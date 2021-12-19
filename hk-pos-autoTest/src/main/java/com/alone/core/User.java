package com.alone.core;

import com.alibaba.fastjson.JSONObject;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.util.JsonUtil;
import com.alone.util.ResolveCurl;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Author: hetilong
 * @Date: 2021/12/18 21:05
 */
public class User {
    public String cookies;
    public TerminalInfo terminalInfo;
    public EnvironmentInfo environmentInfo;
    public LoginInfo loginInfo;
    public User(EnvironmentInfo environmentInfo, LoginInfo loginInfo){
        this.environmentInfo = environmentInfo;
        this.loginInfo = loginInfo;
    }

    /**
     * 获取terminal信息
     * @param environment
     * @return
     */
    public TerminalInfo getTerminalInfo(String environment){

        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlTerminalQuery());
        CurlParams cp = rs.getParams();

        JsonUtil jsonUtil = new JsonUtil();
        String requestData = null;
        if(environment.equals("DEV")){
            requestData = jsonUtil.updateJsonStr(cp.getData(),"iHahcDKrRIZmFhWkuQuwRKiHSnpoHv","bios");
        }else if(environment.equals("TEST")){
            requestData = jsonUtil.updateJsonStr(cp.getData(),"mFkWuqcSGfXBnGgejwlSzkvthQWNqU","bios");
        }else if(environment.equals("STAGE")){
            requestData = jsonUtil.updateJsonStr(cp.getData(),"EyvgVVKVZCnHjpmQbuII","bios");
        }
        String res = given().headers(cp.getHeader()).body(requestData).post(cp.getUrl()).asString();
        terminalInfo = JSONObject.parseObject(jsonUtil.getValueByKeyReturnString(res,"data"), TerminalInfo.class);
     return terminalInfo;
    }

    public String login(){

        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlLogin());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),loginInfo.getLoginId(),"loginId");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,loginInfo.getPassword(),"password");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,terminalInfo.getId(),"terminalId");
        String res = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        System.out.println(res);
        //更新请求头的cookies
        cookies = new JsonUtil().getValueByKeyFromJson(res,"token").get(0);
        cookies = "pos-token="+cookies+"; locale=zh-CN";
        return cookies;
    }

    /**
     * 登出接口
     */
    public  String logout(){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlLogout());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));
        map.put("Cookie",cookies);
        String res = given().headers(map).post(cp.getUrl()).asString();
        return res;
    }
}
