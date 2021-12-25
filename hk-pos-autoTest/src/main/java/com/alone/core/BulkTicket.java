package com.alone.core;

import com.alibaba.fastjson.JSON;
import com.alone.common.BaseBuyTicket;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.cart.TicketRealNameInfo;
import com.alone.pojo.confirm.ConfirmRequestParams;
import com.alone.pojo.event.PerformanceInfo;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.pojo.transaction.CreatTranParams;
import com.alone.pojo.transaction.CustomerInfo;
import com.alone.util.*;

import java.util.*;

import static io.restassured.RestAssured.given;

/**
 * @Author: hetilong
 * @Date: 2021/12/20 12:49
 * 大量购票相关
 */
public class BulkTicket extends BaseBuyTicket {
    public BulkTicket(TerminalInfo terminalInfo, EnvironmentInfo environmentInfo, LoginInfo loginInfo, String cookies, int posType, int eventId) {
        super(terminalInfo, environmentInfo, loginInfo, cookies, posType, eventId);
    }

    public void creatOrder(){
        JsonUtil jsonUtil = new JsonUtil();
        SeatUtil seatUtil = new SeatUtil();
        List<PerformanceInfo> performanceInfoList = detail();
        String detailRes = detail(performanceInfoList.get(0).getPerformanceId());
        List<ConfirmRequestParams> confirmRequestParams = getConfirmRequestParams(detailRes,performanceInfoList.get(0).getPerformanceId());
        System.out.println(confirmRequestParams);

        List<ConfirmRequestParams> other = new ArrayList<>();

        for (ConfirmRequestParams con :confirmRequestParams){
            if(con.getConfirmStockInfo().getTicketTypeNature() != 4){
                other.add(con);
            }
        }
        for (ConfirmRequestParams con : other){
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
        String transactionReq = getTransactionReq(queryCartRes);
        String transactionId = creatTransaction(transactionReq);
        String res = prepay(transactionId);

    }
    /**
     * 预支付接口
     *
     */
    @Override
    public  String prepay(String transactionId){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlPrePay());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);

        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());

        JsonUtil jsonUtil = new JsonUtil();
        String data = jsonUtil.updateJsonStr(cp.getData(),transactionId,"transactionId");
        data = jsonUtil.updateJsonStr(data,50,"payChannel");
        String response = given().headers(map).body(data).post(cp.getUrl()).asString();
        System.out.println(response);
        String tranNumber = jsonUtil.getValueByKeyFromJson(response,"tranNumber").get(0);
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
                "HIRE",
                0,
                t,
                new CustomerInfo("12345678908"),
                "WALK_IN"
        );

        String transactionReq = JSON.toJSONString(creatTranParams);
        return transactionReq;
    }
}
