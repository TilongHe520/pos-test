package com.alone.main;

import com.alone.core.*;
import com.alone.enums.PosTypeEnum;
import com.alone.environment.GetEnvironment;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.collection.CollectionTicketInfo;
import com.alone.pojo.event.PerformanceInfo;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.util.EncryptSha256Util;
import com.alone.util.LoginUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: hetilong
 * @Date: 2021/12/18 21:44
 */
public class DemoTest {
    public static void main(String[] args) throws IOException {
        String environment = "TEST";
        int eventId = 843;

        String path="/Users/maoyan/work/pos-test/hk-pos-autoTest/src/main/resources/loginInfo.properties";
        LoginUtil loginUtil = new LoginUtil();
        Map<String, LoginInfo> loginInfoMap = loginUtil.getLoginInfo(path);

        LoginInfo loginInfo = loginInfoMap.get(environment);

        GetEnvironment env = new GetEnvironment();
        EnvironmentInfo environmentInfo = env.getEnvironmentCurl(environment);

        User user = new User(environmentInfo,loginInfo);
        //查询terminal信息
        TerminalInfo terminalInfo = user.getTerminalInfo(environment);
        //获取登录cookies
        String cookies = user.login();
        try{
            ReservationConsignment rc = new ReservationConsignment(terminalInfo,environmentInfo,
                    loginInfo,cookies,PosTypeEnum.valueOf("RESERVATION_CONSIGNMENT").getStatus(),eventId);

            rc.creatOrder();

            BulkTicket bulkTicket = new BulkTicket(terminalInfo,environmentInfo,
                    loginInfo,cookies,PosTypeEnum.valueOf("BUK").getStatus(),eventId);
            bulkTicket.creatOrder();


            CollectionTicket ct = new CollectionTicket(loginInfo,terminalInfo,environmentInfo,cookies);
            CollectionTicketInfo c = new CollectionTicketInfo("","12345678908","0","","",
                    new EncryptSha256Util().getSha256Str("6250947000000014"),"");
            ct.getListCollectionTicket(c);

        }catch (Exception e){
            e.printStackTrace();
        }
        //登出。
        user.logout();
    }
}
