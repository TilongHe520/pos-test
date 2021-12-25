package com.alone.core;

import com.alibaba.fastjson.JSON;
import com.alone.common.BaseBuyTicket;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.cart.TicketRealNameInfo;
import com.alone.pojo.confirm.ConfirmRequestParams;
import com.alone.pojo.event.PerformanceInfo;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.pojo.transaction.CreatTranParams;
import com.alone.pojo.transaction.CustomerInfo;
import com.alone.util.EncryptSha256Util;
import com.alone.util.JsonUtil;
import com.alone.util.RealNameUtil;
import com.alone.util.SeatUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

/**
 * @Author: hetilong
 * @Date: 2021/12/19 20:14
 * 预留内销门票
 */
public class ReservationConsignment extends BaseBuyTicket {
    public ReservationConsignment(TerminalInfo terminalInfo, EnvironmentInfo environmentInfo, LoginInfo loginInfo,
                                  String cookies, int posType, int eventId) {
        super(terminalInfo, environmentInfo, loginInfo, cookies, posType, eventId);
    }


    public String getTransactionReq(String queryCartRes,int rescType,String reserveExpireDate,String claimId){
        RealNameUtil realNameUtil = new RealNameUtil();
        JsonUtil jsonUtil = new JsonUtil();
        List<TicketRealNameInfo> t = realNameUtil.getTicketRealList(queryCartRes);

        CreatTranParams creatTranParams = new CreatTranParams(claimId,
                new CustomerInfo("12345678908"),
                0,Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"giftTicketCount")),posType,terminalInfo.getOutletCode(),
                terminalInfo.getOutletGroupCode(),t,0,rescType,reserveExpireDate,terminalInfo.getTerminalId(),
                Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"ticketCount")),
                Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"serviceFee")),
                Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"totalPrice")),
                Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"ticketCount")),
                Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"toSumPrice")));

        String transactionReq = JSON.toJSONString(creatTranParams);
        return transactionReq;
    }
    public String creatOrder(){
        JsonUtil jsonUtil = new JsonUtil();
        SeatUtil seatUtil = new SeatUtil();
        List<PerformanceInfo> performanceInfoList = detail();
        String detailRes = detail(performanceInfoList.get(0).getPerformanceId());
        List<ConfirmRequestParams> confirmRequestParams = getConfirmRequestParams(detailRes,performanceInfoList.get(0).getPerformanceId());
        System.out.println(confirmRequestParams);

        List<ConfirmRequestParams> normal = new ArrayList<>();
        List<ConfirmRequestParams> other = new ArrayList<>();

        Map<String,List<ConfirmRequestParams>>  map = new HashMap<>();
        for (ConfirmRequestParams con :confirmRequestParams){
            if(con.getConfirmStockInfo().getTicketTypeNature() == 4){
                normal.add(con);
            }else {
                other.add(con);
            }
        }
        map.put("other",other);
        map.put("normal",normal);
        Set<String> keys = map.keySet();
        String claimId = RandomStringUtils.randomAlphabetic(4).toUpperCase();
        for(String k:keys){
            if (k.equals("other")){
                for (ConfirmRequestParams con : map.get(k)){
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
                String queryCartRes = queryCart();
                String transactionReq = getTransactionReq(queryCartRes,1,"",claimId);
                creatTransaction(transactionReq);

            }else if(k.equals("normal")){
                for (ConfirmRequestParams con : map.get(k)){
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
                String queryCartRes = queryCart();
                String transactionReq = getTransactionReq(queryCartRes,2,String.valueOf(System.currentTimeMillis()+86400000),claimId);
                creatTransaction(transactionReq);
            }

        }

        return claimId;

    }
}
