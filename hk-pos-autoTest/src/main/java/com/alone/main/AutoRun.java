package com.alone.main;

import com.alone.core.MajorCore;
import com.alone.enums.PosTypeEnum;
import com.alone.environment.GetEnvironment;
import com.alone.pojo.CartTicketInfo;
import com.alone.pojo.ConfirmRequestParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.PerformanceInfo;
import com.alone.util.JsonUtil;
import com.alone.util.SeatUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author maoyan
 */
public class AutoRun {
    public static void main(String[] args) throws InterruptedException, IOException {
        String loginId = "autoTest";
        String password = "A123456789A";
        String terminalId = "138";
        String terminalCode = "terminal-ejLcb";
        //1352,1636
        String eventId = "1636";
        int posType = PosTypeEnum.valueOf("REG").getStatus();
        int stockNum = 1;

        LoginInfo loginInfo = new LoginInfo(loginId, password, terminalId, terminalCode);

        AutoRun ar = new AutoRun();
        ar.function("DEV", loginInfo, eventId, posType, stockNum);


    }

    public void function(String environment, LoginInfo loginInfo, String eventId, int posType, int stockNum) throws InterruptedException, IOException {


        GetEnvironment env = new GetEnvironment();
        EnvironmentInfo environmentInfo = env.getEnvironmentCurl(environment);

        MajorCore mc = new MajorCore(environmentInfo, loginInfo);
        JsonUtil jsonUtil = new JsonUtil();
        SeatUtil seatUtil = new SeatUtil();

        //登录获取token
        String cookies = mc.login();

        List<PerformanceInfo> performanceList = mc.detail(eventId, cookies);
        System.out.println(performanceList);

        List<List<ConfirmRequestParams>> confirmStockInfoList = new ArrayList<>();
        for (PerformanceInfo p : performanceList) {
            long currentTime = System.currentTimeMillis();
            if (currentTime < p.getSaleEndDate()) {
                List<ConfirmRequestParams> confirmStockInfos = mc.detail(eventId, cookies, p.getPerformanceId(), posType, stockNum);
                confirmStockInfoList.add(confirmStockInfos);
            }

        }
        System.out.println(confirmStockInfoList);

        for (List<ConfirmRequestParams> confirmRequestParams : confirmStockInfoList) {
            for (ConfirmRequestParams con : confirmRequestParams) {
                String confirmRes = mc.confirmStock(con, cookies);
                if (jsonUtil.getValueByKeyReturnString(confirmRes, "seatDTOList") != null) {
                    String requestData = seatUtil.addRequestParams(confirmRes, loginInfo.getTerminalCode(), posType,con.getEventId());
                    String res2 = mc.addToCart(requestData, cookies);
                    System.out.println(res2);
                }
                if (jsonUtil.getValueByKeyReturnString(confirmRes, "noSeatDTOList") != null) {

                    String requestData = seatUtil.addRequestParams(confirmRes, posType, loginInfo.getTerminalCode(),con.getEventId());
                    String res2 = mc.addToCart(requestData, cookies);
                    System.out.println(res2);
                }

            }
        }

        CartTicketInfo cartTicketInfo = mc.queryCart(posType, cookies);
        System.out.println(cartTicketInfo);

        Thread.sleep(3 * 1000);
        String transactionId = mc.creatTransaction(cookies, cartTicketInfo);

        mc.prepayResult(transactionId, cookies);
        String transactionNum = mc.prepay(transactionId, cookies);

        String printRes = mc.print(transactionNum, cookies);

        System.out.println(printRes);
        Thread.sleep(10 * 1000);


        String logoutRes = mc.logout(cookies);
        System.out.println(logoutRes);

    }
}
