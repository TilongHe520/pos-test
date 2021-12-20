package com.alone.main;

import com.alone.core.BulkTicket;
import com.alone.core.ReservationConsignment;
import com.alone.core.TelephoneTicket;
import com.alone.core.User;
import com.alone.enums.PosTypeEnum;
import com.alone.environment.GetEnvironment;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.event.PerformanceInfo;
import com.alone.pojo.terminal.TerminalInfo;
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
        String environment = "DEV";

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
            /*
            ReservationConsignment rc = new ReservationConsignment(terminalInfo,environmentInfo,
                    loginInfo,cookies,PosTypeEnum.valueOf("RESERVATION_CONSIGNMENT").getStatus(),837);

            rc.creatOrder();

             */

            BulkTicket bulkTicket = new BulkTicket(terminalInfo,environmentInfo,
                    loginInfo,cookies,PosTypeEnum.valueOf("BUK").getStatus(),837);
            bulkTicket.creatOrder();

        }catch (Exception e){
            e.printStackTrace();
        }
        //登出。
        user.logout();
    }
}
