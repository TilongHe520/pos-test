package com.alone.core;

import com.alibaba.fastjson.JSONArray;
import com.alone.common.BaseBuyTicket;
import com.alone.common.BaseCommon;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.confirm.ConfirmRequestParams;
import com.alone.pojo.event.PerformanceInfo;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.util.JsonUtil;
import com.alone.util.RealNameUtil;
import com.alone.util.SeatUtil;


import java.util.List;

/**
 * 电话购票场景(待完善)
 * @Author: hetilong
 * @Date: 2021/12/16 13:51
 */
public class TelephoneTicket extends BaseBuyTicket {

    public TelephoneTicket(TerminalInfo terminalInfo, EnvironmentInfo environmentInfo, LoginInfo loginInfo, String cookies,
                           int posType,int eventId) {
        super(terminalInfo, environmentInfo, loginInfo, cookies, posType,eventId);
    }

    public String creatOrder(){
        List<PerformanceInfo> performanceInfoList = detail();
        JsonUtil jsonUtil = new JsonUtil();
        SeatUtil seatUtil = new SeatUtil();
        for(PerformanceInfo p:performanceInfoList){
            String detailRes = detail(p.getPerformanceId());
            List<ConfirmRequestParams> confirmRequestParams = getConfirmRequestParams(detailRes,p.getPerformanceId());
            System.out.println(confirmRequestParams);
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
        return res;
    }

}
