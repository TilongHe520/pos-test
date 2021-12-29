package com.alone.core;

import com.alone.common.BaseBuyTicket;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.confirm.ConfirmRequestParams;
import com.alone.pojo.event.PerformanceInfo;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.util.JsonUtil;
import com.alone.util.ResolveCurl;
import com.alone.util.SeatUtil;


import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

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
        return tranNumber;
    }

    /**
     * 预支付接口
     */
    @Override
    public  String prepay(String transactionId){
        String jsonStr = "{\"transactionId\":11018,\"payChannel\":3,\"sellChannel\":2,\"cardNumber\":\"6250947000000014\",\"desensitizationCardNumber\":\"625094******0014\",\"hashCardNumber\":\"561c0fcfa9e8fbd9cdef2099fdd91c02f62ba0f9becfe928eb2bc61baa5f5bb6\",\"unionPayRequest\":{\"expired\":\"3312\"}}";
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlPrePay());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        JsonUtil jsonUtil = new JsonUtil();
        String data = jsonUtil.updateJsonStr(jsonStr,transactionId,"transactionId");
        String response = given().headers(map).body(data).post(cp.getUrl()).asString();
        System.out.println(response);
        String tranNumber = jsonUtil.getValueByKeyFromJson(response,"tranNumber").get(0);
        return tranNumber;
    }

}
