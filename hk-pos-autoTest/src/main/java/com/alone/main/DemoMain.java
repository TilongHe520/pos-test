package com.alone.main;

import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.util.FileUtil;
import com.alone.util.LoginUtil;
import com.alone.util.ResolveCurl;
import org.junit.Test;

import static io.restassured.RestAssured.given;

import java.io.*;
import java.util.*;

/**
 * @Author: hetilong
 * @Date: 2021/12/4 12:15
 */
public class DemoMain {
    public static void main(String[] args) throws IOException {

        String path = "/Users/maoyan/Desktop/POS/curl_pos.txt";
        FileUtil fileUtil = new FileUtil();
        String curl = fileUtil.getCurl(path);
        ResolveCurl rc = new ResolveCurl(curl);
        CurlParams cp = rc.getParams();

        System.out.println(cp.getUrl());
        System.out.println(cp.getData());
        System.out.println(cp.getHeader().toString());

        String res = given().headers(cp.getHeader()).body(cp.getData()).post(cp.getUrl()).asString();
        System.out.println(res);

        fileUtil.writeResponse(res,path);
    }

    @Test
    public void demo() throws IOException {
        String path = "/Users/maoyan/work/curl.txt";
        EnvironmentInfo environmentInfo = new FileUtil().getCurlObject(path);

        String s = environmentInfo.getCurlAddToCart();
        ResolveCurl rc = new ResolveCurl(s);
        CurlParams cp = rc.getParams();
        System.out.println(cp.getUrl());
    }

    @Test
    public void demo1() {
        String path="/Users/maoyan/work/pos-test/hk-pos-autoTest/src/main/resources/loginInfo.properties";
        LoginUtil loginUtil = new LoginUtil();
        Map<String,LoginInfo> loginInfoMap = loginUtil.getLoginInfo(path);
        System.out.println(loginInfoMap.get("TEST").toString());
    }
}
