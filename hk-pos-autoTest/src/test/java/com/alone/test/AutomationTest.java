package com.alone.test;

import com.alone.core.User;
import com.alone.environment.GetEnvironment;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.util.LoginUtil;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: hetilong
 * @Date: 2022/1/2 11:47
 */
public class AutomationTest {

    public String environment = "DEV";
    //String environment = "TEST";
    //String environment = "STAGE";
    public int eventId = 837;
    //int eventId = 782;
    //int eventId = 13263;

    public String path="/Users/maoyan/work/pos-test/hk-pos-autoTest/src/main/resources/loginInfo.properties";

    public LoginUtil loginUtil = new LoginUtil();
    public GetEnvironment env = new GetEnvironment();

    public EnvironmentInfo environmentInfo;
    public TerminalInfo terminalInfo;
    public User user;
    public String cookies;
    public LoginInfo loginInfo;

    public List<String> tranNumbers = new ArrayList<>();

    @BeforeTest(description = "login")
    public void setup(){

        Map<String, LoginInfo> loginInfoMap = loginUtil.getLoginInfo(path);

        loginInfo = loginInfoMap.get(environment);
        try {
            environmentInfo = env.getEnvironmentCurl(environment);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterTest(description = "logout")
    public void tearDown(){
        user.logout();
    }

    @Test(priority = 1,description = "login")
    public void login(){
        user = new User(environmentInfo,loginInfo);
        //查询terminal信息
        terminalInfo = user.getTerminalInfo(environment);
        //获取登录cookies
        cookies = user.login();
    }
}
