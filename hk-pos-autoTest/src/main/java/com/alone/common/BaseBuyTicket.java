package com.alone.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.cart.CartTicketInfo;
import com.alone.pojo.cart.TicketRealNameInfo;
import com.alone.pojo.confirm.ConfirmRequestParams;
import com.alone.pojo.confirm.ConfirmStockInfo;
import com.alone.pojo.event.PerformanceInfo;
import com.alone.pojo.event.PriceZoneInfo;
import com.alone.pojo.print.UploadPrintInfo;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.pojo.transaction.CreatTranParams;
import com.alone.pojo.transaction.CustomerInfo;
import com.alone.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Author: hetilong
 * @Date: 2021/12/19 10:44
 */
public class BaseBuyTicket {
    public TerminalInfo terminalInfo;
    public EnvironmentInfo environmentInfo;
    public LoginInfo loginInfo;
    public String cookies;
    public int posType;
    public int eventId;

    public BaseBuyTicket(TerminalInfo terminalInfo,EnvironmentInfo environmentInfo,LoginInfo loginInfo,String cookies,
                         int posType,int eventId){
        this.environmentInfo = environmentInfo;
        this.terminalInfo = terminalInfo;
        this.loginInfo = loginInfo;
        this.cookies = cookies;
        this.posType = posType;
        this.eventId = eventId;
    }

    /**
     * 详情页接口回去可售场次ID
     * @return
     */
    public List<PerformanceInfo> detail(){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlDetail());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));
        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),eventId,"eventId");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,posType,"menuType");
        String response = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        String performanceList = jsonUtil.getValueByKeyReturnString(response,"performanceList");
        JSONArray jsonArray = JSONArray.parseArray(performanceList);
        List<PerformanceInfo> performanceInfos = new ArrayList<>();
        for(int i = 0 ; i<jsonArray.size();i++){
            PerformanceInfo performanceInfo = JSONObject.parseObject(jsonArray.get(i).toString(),PerformanceInfo.class);
            performanceInfos.add(performanceInfo);
        }
        return performanceInfos;
    }

    /**
     * 获取ConfirmRequestParams参数
     * @param performanceId
     * @return
     */
    public String detail(String performanceId){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlDetail());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));
        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),eventId,"eventId");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,performanceId,"performanceId");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,posType,"menuType");

        String response = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        return response;
    }

    public List<ConfirmRequestParams> getConfirmRequestParams(String detailRes,String performanceId){
        PriceZoneUtil priceZoneUtil= new PriceZoneUtil();
        List<PriceZoneInfo> priceZoneInfos=priceZoneUtil.getPriceZoneInfos(detailRes);

        List<ConfirmRequestParams> confirmStockInfos=priceZoneUtil.getConfirmStockInfos(priceZoneInfos,
                String.valueOf(eventId),
                Integer.valueOf(performanceId),
                posType,
                1);
        return confirmStockInfos;
    }

    /**
     * 自主选座
     * @param confirmRequestParams
     * @return
     */
    public  String confirmStock(ConfirmRequestParams confirmRequestParams){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlConfirmStock());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        JsonUtil jsonUtil = new JsonUtil();
        ArrayList<ConfirmStockInfo> arr = new ArrayList<>();
        arr.add(confirmRequestParams.getConfirmStockInfo());
        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(arr);
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),confirmRequestParams.getEventId(),"eventId");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,confirmRequestParams.getPerformanceId(),"performanceId");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,jsonArray,"confirmStockInfoList");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,confirmRequestParams.getMenuType(),"menuType");

        String response = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        System.out.println(response);
        return response;
    }

    /**
     * 加入购物车
     * @param requestData
     * @return
     */
    public  String addToCart(String requestData){

        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlAddToCart());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        String response = given().headers(map).body(requestData).post(cp.getUrl()).asString();

        return response;
    }

    /**
     * 查询购物车
     * @return
     */
    public String queryCart(){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlQueryCart());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);

        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),posType,"posType");
        String response = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();

        return response;
    }

    /**
     * 获取创建交易参数
     */
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
                "CUP",
                true,
                2,
                t,
                new CustomerInfo("12345678908","6250947000000014",
                        new EncryptSha256Util().getSha256Str("6250947000000014"),"12","33"),
                "SELF_FETCH"
        );

        String transactionReq = JSON.toJSONString(creatTranParams);
        return transactionReq;
    }
    /**
     * 创建交易接口
     * @param
     * @return
     */
    public  String creatTransaction(String requestData){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlCreatTransaction());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        JsonUtil jsonUtil = new JsonUtil();

        String response = given().headers(map).body(requestData).post(cp.getUrl()).asString();
        System.out.println(response);
        String transactionId = jsonUtil.getValueByKeyFromJson(response,"transactionId").get(0);
        return transactionId;
    }

    /**
     * 预支付结果接口
     */
    public  String prepayResult(String transactionId){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlPrePayResult());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        JsonUtil jsonUtil = new JsonUtil();
        String data = jsonUtil.updateJsonStr(cp.getData(),transactionId,"transactionId");
        String response = given().headers(map).body(data).post(cp.getUrl()).asString();
        return response;
    }

    /**
     * 预支付接口
     */
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
    /**
     * 打票接口
     */
    public  String print(String transactionNum){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlPrint());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        String data = cp.getData().replace("21111520050102801",transactionNum);
        String response = given().headers(map).body(data).post(cp.getUrl()).asString();
        return response;
    }

    public String uploadPrintResult(List<UploadPrintInfo> uploadPrintInfoList){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlUploadPrintResult());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        JsonUtil jsonUtil = new JsonUtil();
        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(uploadPrintInfoList);
        String data = jsonUtil.updateJsonStr(cp.getData(),jsonArray,"printTicketResultList");
        data = jsonUtil.updateJsonStr(data,terminalInfo.getTerminalId(),"deviceNum");
        data = jsonUtil.updateJsonStr(data,posType,"menuType");
        data = jsonUtil.updateJsonStr(data,loginInfo.getUserId(),"operatorId");
        String res = given().headers(map).body(data).post(cp.getUrl()).asString();
        return res;
    }
}
