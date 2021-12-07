package com.alone.main;

import com.alibaba.fastjson.JSONArray;
import com.alone.core.MajorCore;
import com.alone.enums.PosTypeEnum;
import com.alone.environment.GetEnvironment;
import com.alone.pojo.cart.CartTicketInfo;
import com.alone.pojo.confirm.ConfirmRequestParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.event.PerformanceInfo;
import com.alone.pojo.print.UploadPrintInfo;
import com.alone.util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author maoyan
 */
public class AutoRun {
    public static void main(String[] args) throws InterruptedException, IOException {
        //1352,1636
        String eventId = "782";
        int posType = PosTypeEnum.valueOf("BUK").getStatus();
        int stockNum = 1;

        String environment = "TEST";

        String path="/Users/maoyan/work/pos-test/hk-pos-autoTest/src/main/resources/loginInfo.properties";
        LoginUtil loginUtil = new LoginUtil();
        Map<String,LoginInfo> loginInfoMap = loginUtil.getLoginInfo(path);

        LoginInfo loginInfo = loginInfoMap.get(environment);

        AutoRun ar = new AutoRun();
        ar.function(environment, loginInfo, eventId, posType, stockNum);


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

        String queryCartRes = mc.queryCart(posType, cookies);

        JSONArray jsonArray = new RealNameUtil().getTicketRealNameJsonArray(queryCartRes);

        CartTicketInfo cartTicketInfo = new CartTicketInfo();
        cartTicketInfo.setTicketCount(Integer.valueOf(jsonUtil.getValueByKeyFromJson(queryCartRes,"ticketCount").get(0)).intValue());
        cartTicketInfo.setTotalTicketCount(Integer.valueOf(jsonUtil.getValueByKeyFromJson(queryCartRes,"ticketCount").get(0)).intValue());

        cartTicketInfo.setTotalChargeFee(Integer.valueOf(jsonUtil.getValueByKeyFromJson(queryCartRes,"serviceFee").get(0)).intValue());
        cartTicketInfo.setTotalPayPrice(Integer.valueOf(jsonUtil.getValueByKeyFromJson(queryCartRes,"toSumPrice").get(0)).intValue());
        cartTicketInfo.setTotalTicketPrice(Integer.valueOf(jsonUtil.getValueByKeyFromJson(queryCartRes,"totalPrice").get(0)).intValue());
        cartTicketInfo.setMenuType(posType);

        Thread.sleep(3*1000);
        String transactionId = mc.creatTransaction(cookies, cartTicketInfo,jsonArray);

        mc.prepayResult(transactionId, cookies);
        String transactionNum = mc.prepay(transactionId, cookies);

        String printRes = mc.print(transactionNum, cookies);

        List<String> ticketIdList = jsonUtil.getValueByKeyFromJson(printRes,"ticketId");
        String taskId = jsonUtil.getValueByKeyReturnString(printRes,"taskId");

        List<UploadPrintInfo> uploadPrintInfoList = new UploadPrintUtil().getUploadPrintList(taskId,ticketIdList);

        String uploadRes = mc.uploadPrintResult(cookies,uploadPrintInfoList,"1");
        System.out.println(uploadRes);
        Thread.sleep(10 * 1000);


        String logoutRes = mc.logout(cookies);
        System.out.println(logoutRes);

    }
}
