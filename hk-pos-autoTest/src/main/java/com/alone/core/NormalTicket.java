package com.alone.core;

import com.alibaba.fastjson.JSON;
import com.alone.common.BaseBuyTicket;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.cart.TicketRealNameInfo;
import com.alone.pojo.confirm.ConfirmRequestParams;
import com.alone.pojo.event.PerformanceInfo;
import com.alone.pojo.print.UploadPrintInfo;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.pojo.transaction.CreatTranParams;
import com.alone.pojo.transaction.CustomerInfo;
import com.alone.util.*;

import java.util.List;

/**
 * @Author: hetilong
 * @Date: 2021/12/22 19:23
 */
public class NormalTicket extends BaseBuyTicket {
    public NormalTicket(TerminalInfo terminalInfo, EnvironmentInfo environmentInfo, LoginInfo loginInfo, String cookies, int posType, int eventId) {
        super(terminalInfo, environmentInfo, loginInfo, cookies, posType, eventId);
    }

    public String createOrder(){
        List<PerformanceInfo> performanceInfoList = detail();
        JsonUtil jsonUtil = new JsonUtil();
        SeatUtil seatUtil = new SeatUtil();
        for(PerformanceInfo p:performanceInfoList){
            String detailRes = detail(p.getPerformanceId());
            List<ConfirmRequestParams> confirmRequestParams = getConfirmRequestParams(detailRes,p.getPerformanceId());
            for (ConfirmRequestParams con :confirmRequestParams){
                String confirmRes = confirmStock(con);
                if (jsonUtil.getValueByKeyReturnString(confirmRes, "seatDTOList") != null) {
                    String requestData = seatUtil.addRequestParams(confirmRes, terminalInfo.getTerminalId(), posType,con.getEventId());
                    addToCart(requestData);
                }
                if (jsonUtil.getValueByKeyReturnString(confirmRes, "noSeatDTOList") != null) {

                    String requestData = seatUtil.addRequestParams(confirmRes, posType, terminalInfo.getTerminalId(),con.getEventId());
                    addToCart(requestData);
                }
            }
        }
        String queryCartRes = queryCart();
        String transactionReq = getTransactionReq(queryCartRes);
        String transactionId = creatTransaction(transactionReq);
        String tranNumber = prepay(transactionId);
        System.out.println(tranNumber);
        try{
            Thread.sleep(3*1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        String res = prepayResult(transactionId);
        String printRes = print(tranNumber);

        List<String> ticketIdList = jsonUtil.getValueByKeyFromJson(printRes,"ticketId");
        String taskId = jsonUtil.getValueByKeyReturnString(printRes,"taskId");

        List<UploadPrintInfo> uploadPrintInfoList = new UploadPrintUtil().getUploadPrintList(taskId,ticketIdList);

        String uploadRes = uploadPrintResult(uploadPrintInfoList);
        System.out.println(uploadRes);
        return tranNumber;
    }

    @Override
    public String getTransactionReq(String queryCartRes){
        RealNameUtil realNameUtil = new RealNameUtil();
        JsonUtil jsonUtil = new JsonUtil();
        List<TicketRealNameInfo> t = realNameUtil.getTicketRealList(queryCartRes);

        CreatTranParams creatTranParams = new CreatTranParams(
                Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"giftTicketCount")),
                0,
                0,
                posType,
                Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"totalPrice")),
                Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"toSumPrice")),
                Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"serviceFee")),
                Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"ticketCount")),
                Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"ticketCount")),
                terminalInfo.getOutletGroupCode(),
                terminalInfo.getOutletCode(),
                terminalInfo.getTerminalId(),
                "CASH",
                2,
                t,
                new CustomerInfo("12345678908"),"WALK_IN"

        );

        String transactionReq = JSON.toJSONString(creatTranParams);
        return transactionReq;
    }
}
