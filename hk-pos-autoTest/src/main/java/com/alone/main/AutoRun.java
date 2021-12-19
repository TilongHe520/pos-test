package com.alone.main;

import com.alibaba.fastjson.JSONArray;
import com.alone.core.*;
import com.alone.enums.PosTypeEnum;
import com.alone.environment.GetEnvironment;
import com.alone.pojo.cart.CartSkuInfo;
import com.alone.pojo.cart.CartTicketInfo;
import com.alone.pojo.confirm.ConfirmRequestParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.event.PerformanceInfo;
import com.alone.pojo.print.UploadPrintInfo;
import com.alone.pojo.serviceCharge.PoundageTypeInfo;
import com.alone.pojo.ticket.RefundAddInfo;
import com.alone.pojo.ticket.RefundSettleInfo;
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

        //String eventId = "13263";
        //String eventId = "782";
        String eventId = "837";
        int posType = PosTypeEnum.valueOf("BUK").getStatus();
        int stockNum = 1;

        //String environment = "STAGE";
        //String environment = "TEST";
        String environment = "DEV";

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
        try{
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

            String uploadRes = mc.uploadPrintResult(cookies,uploadPrintInfoList,String.valueOf(posType));
            System.out.println(uploadRes);
            Thread.sleep(10 * 1000);
            String queryTranRes = mc.queryTran(transactionNum,cookies);
            System.out.println(queryTranRes);
            //升级门票
            System.out.println("=======升级门票========");
            UpgradeTicket upgradeTicket = new UpgradeTicket(cookies,environmentInfo,loginInfo,12);
            List<String> ticketId = upgradeTicket.getTicketInfo(transactionNum);
            String checkExchangeRes = upgradeTicket.checkExchange(ticketId);
            System.out.println("==========="+checkExchangeRes);
            upgradeTicket.creatOrder(mc);

            //换票
            System.out.println("=======换票========");
            ExchangeTicket ticketExchange = new ExchangeTicket(environmentInfo,loginInfo,cookies,11);
            List<String> ticketIdExc = ticketExchange.getTicketInfo(transactionNum);
            CartSkuInfo cartSkuInfo = ticketExchange.checkExchange(ticketIdExc);
            List<ConfirmRequestParams> confirmRequestParamsList = ticketExchange.exchangeDetail(cartSkuInfo,
                    eventId);
            String requestData = ticketExchange.confirmStock(cartSkuInfo,confirmRequestParamsList.get(0));
            String addRes = ticketExchange.addToCart(requestData);
            ticketExchange.creatOrder(mc);
            System.out.println("========"+addRes);

            //更改持票人
            System.out.println("=======更改持票人========");
            ChangeHolder changeHolder = new ChangeHolder(environmentInfo,loginInfo,cookies,13);
            List<String> changeHolderIds = changeHolder.getChangeHolderInfo(transactionNum);
            String changeHolderRes = changeHolder.checkHolder(changeHolderIds);
            System.out.println("==========="+changeHolderRes);
            changeHolder.creatOrder(mc);

            //门票重印
            System.out.println("=======门票重印========");
            ReprintTicket reprintTicket = new ReprintTicket(environmentInfo,loginInfo,cookies,17);
            String tranNumRes = reprintTicket.getReprintTicketInfo();
            List<UploadPrintInfo> uploadPrintInfoList1 = reprintTicket.print(tranNumRes);
            String reprintUploadRes = reprintTicket.uploadPrintResult(uploadPrintInfoList1);
            System.out.println("%%%%%%%"+reprintUploadRes);


            //离线退款
            System.out.println("=====离线退款====");
            String refundRes = mc.refundList(transactionNum,cookies);
            System.out.println(refundRes);

            RefundUtil refundUtil = new RefundUtil();
            RefundAddInfo refundAddInfo = refundUtil.getRefundAddParams(refundRes);
            System.out.println(refundAddInfo);
            String res = mc.refundAddToCart(refundAddInfo,cookies,3);
            System.out.println(res);

            List<RefundSettleInfo> refundSettleInfoList = refundUtil.getRefundSettleParams(refundAddInfo);
            System.out.println(refundSettleInfoList);
            String refundSettleRes = mc.refundSettle(refundSettleInfoList,cookies);
            System.out.println(refundSettleRes);
            Thread.sleep(10 * 1000);
            String queryTranRes1 = mc.queryTran(refundSettleRes,cookies);
            System.out.println(queryTranRes1);
            Thread.sleep(10 * 1000);
            String summaryRes = mc.summary(cookies);
            System.out.println(summaryRes);

            System.out.println("====收取手续费====");
            List<PoundageTypeInfo> poundageTypeInfoList = mc.listPoundage(cookies);
            for (PoundageTypeInfo p:poundageTypeInfoList
                 ) {
                if (!"Change".equals(p.getCode())){
                   String ptRes =  mc.creatPoundageTran(p,cookies);
                   transactionId = jsonUtil.getValueByKeyReturnString(ptRes,"transactionId");
                   transactionNum = jsonUtil.getValueByKeyReturnString(ptRes,"transactionNo");
                   mc.prepayResult(transactionId,cookies);
                   mc.prepay(transactionId,cookies);
                   printRes = mc.printChargeTicket(transactionNum,cookies);
                   ticketIdList = jsonUtil.getValueByKeyFromJson(printRes,"ticketId");
                   taskId = jsonUtil.getValueByKeyReturnString(printRes,"taskId");

                   uploadPrintInfoList = new UploadPrintUtil().getUploadPrintList(taskId,ticketIdList);

                   uploadRes = mc.uploadPrintResult(cookies,uploadPrintInfoList,"20");
                   System.out.println(uploadRes);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("=====登出======");
            String logoutRes = mc.logout(cookies);
            System.out.println(logoutRes);
        }
    }
}
