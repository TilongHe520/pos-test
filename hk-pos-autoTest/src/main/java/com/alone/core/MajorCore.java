package com.alone.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.cart.CartTicketInfo;
import com.alone.pojo.confirm.ConfirmRequestParams;
import com.alone.pojo.confirm.ConfirmStockInfo;
import com.alone.pojo.event.PerformanceInfo;
import com.alone.pojo.event.PriceZoneInfo;
import com.alone.pojo.print.UploadPrintInfo;
import com.alone.util.JsonUtil;
import com.alone.util.PriceZoneUtil;
import com.alone.util.ResolveCurl;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Author: hetilong
 * @Date: 2021/11/21 11:30
 */
@NoArgsConstructor
public class MajorCore {

    public EnvironmentInfo environmentInfo;
    public LoginInfo loginInfo;
    public MajorCore(EnvironmentInfo environmentInfo, LoginInfo loginInfo){
        this.environmentInfo = environmentInfo;
        this.loginInfo = loginInfo;
    }

    public String login(){

        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlLogin());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());

        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),loginInfo.getLoginId(),"loginId");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,loginInfo.getPassword(),"password");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,loginInfo.getTerminalId(),"terminalId");
        String res = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        System.out.println(res);
        //更新请求头的cookies
        String cookies = new JsonUtil().getValueByKeyFromJson(res,"token").get(0);
        cookies = "pos-token="+cookies+"; locale=zh-CN";
        return cookies;
    }

    /**
     * 登出接口
     */
    public  String logout(String cookies){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlLogout());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());
        map.put("Cookie",cookies);
        String res = given().headers(map).post(cp.getUrl()).asString();
        return res;
    }

    /**
     * 详情页接口
     * @param eventId
     * @return
     */
    public List<PerformanceInfo> detail(String eventId, String cookies){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlDetail());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());
        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),eventId,"eventId");
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

    public List<ConfirmRequestParams> detail(String eventId, String cookies, String performanceId, int posType, int stockNum){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlDetail());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());
        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),eventId,"eventId");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,performanceId,"performanceId");

        String response = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        PriceZoneUtil priceZoneUtil= new PriceZoneUtil();
        List<PriceZoneInfo> priceZoneInfos=priceZoneUtil.getPriceZoneInfos(response);

        List<ConfirmRequestParams> confirmStockInfos=priceZoneUtil.getConfirmStockInfos(priceZoneInfos,
                eventId,
                Integer.valueOf(performanceId),
                posType,
                stockNum);
        return confirmStockInfos;
    }

    public  String confirmStock(ConfirmRequestParams confirmRequestParams,String cookies){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlConfirmStock());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());

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

    public  String addToCart(String requestData,String cookies){

        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlAddToCart());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalCode());

        String response = given().headers(map).body(requestData).post(cp.getUrl()).asString();

        return response;
    }

    public CartTicketInfo queryCart(int posType, String cookies){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlQueryCart());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);

        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());

        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),posType,"posType");
        String response = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        CartTicketInfo cartTicketInfo = new CartTicketInfo();
        cartTicketInfo.setTicketCount(Integer.valueOf(jsonUtil.getValueByKeyFromJson(response,"ticketCount").get(0)).intValue());
        cartTicketInfo.setTotalTicketCount(Integer.valueOf(jsonUtil.getValueByKeyFromJson(response,"ticketCount").get(0)).intValue());

        cartTicketInfo.setTotalChargeFee(Integer.valueOf(jsonUtil.getValueByKeyFromJson(response,"serviceFee").get(0)).intValue());
        cartTicketInfo.setTotalPayPrice(Integer.valueOf(jsonUtil.getValueByKeyFromJson(response,"toSumPrice").get(0)).intValue());
        cartTicketInfo.setTotalTicketPrice(Integer.valueOf(jsonUtil.getValueByKeyFromJson(response,"totalPrice").get(0)).intValue());
        cartTicketInfo.setMenuType(posType);
        return cartTicketInfo;
    }

    /**
     * 创建交易接口
     * @param
     * @return
     */
    public  String creatTransaction(String cookies,CartTicketInfo cartTicketInfo){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlCreatTransaction());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);

        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());

        JsonUtil jsonUtil = new JsonUtil();

        String requestData = jsonUtil.updateJsonStr(cp.getData(),loginInfo.getTerminalCode(),"terminalCode");
        requestData = jsonUtil.updateJsonStr(requestData,cartTicketInfo.getTicketCount(),"ticketCount");
        requestData = jsonUtil.updateJsonStr(requestData,cartTicketInfo.getTotalChargeFee(),"totalChargeFee");
        requestData = jsonUtil.updateJsonStr(requestData,cartTicketInfo.getTotalPayPrice(),"totalPayPrice");
        requestData =jsonUtil.updateJsonStr(requestData,cartTicketInfo.getTotalTicketCount(),"totalTicketCount");
        requestData = jsonUtil.updateJsonStr(requestData,cartTicketInfo.getTotalTicketPrice(),"totalTicketPrice");
        requestData = jsonUtil.updateJsonStr(requestData,cartTicketInfo.getMenuType(),"menuType");
        String response = given().headers(map).body(requestData).post(cp.getUrl()).asString();
        System.out.println(response);
        String transactionId = jsonUtil.getValueByKeyFromJson(response,"transactionId").get(0);
        return transactionId;
    }

    /**
     * 预支付结果接口
     */
    public  String prepayResult(String transactionId,String cookies){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlPrePayResult());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());

        JsonUtil jsonUtil = new JsonUtil();
        String data = jsonUtil.updateJsonStr(cp.getData(),transactionId,"transactionId");
        String response = given().headers(map).body(data).post(cp.getUrl()).asString();
        return response;
    }

    /**
     * 预支付接口
     */
    public  String prepay(String transactionId,String cookies){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlPrePay());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);

        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());

        JsonUtil jsonUtil = new JsonUtil();
        String data = jsonUtil.updateJsonStr(cp.getData(),transactionId,"transactionId");
        String response = given().headers(map).body(data).post(cp.getUrl()).asString();
        System.out.println(response);
        String tranNumber = jsonUtil.getValueByKeyFromJson(response,"tranNumber").get(0);
        return tranNumber;
    }
    /**
     * 打票接口
     */
    public  String print(String transactionNum,String cookies){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlPrint());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);

        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());

        String data = cp.getData().replace("21111520050102801",transactionNum);
        String response = given().headers(map).body(data).post(cp.getUrl()).asString();
        return response;
    }

    public String uploadPrintResult(String cookies, List<UploadPrintInfo> uploadPrintInfoList, String posType){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlUploadPrintResult());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);

        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());

        JsonUtil jsonUtil = new JsonUtil();
        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(uploadPrintInfoList);
        String data = jsonUtil.updateJsonStr(cp.getData(),jsonArray,"printTicketResultList");
        data = jsonUtil.updateJsonStr(data,loginInfo.getTerminalCode(),"deviceNum");
        data = jsonUtil.updateJsonStr(data,posType,"menuType");
        data = jsonUtil.updateJsonStr(data,loginInfo.getUserId(),"operatorId");
        String res = given().headers(map).body(data).post(cp.getUrl()).asString();
        return res;
    }

    /**
     * 查询交易接口
     * @param tranNum
     * @param cookies
     * @return
     */
    public  String queryTran(String tranNum,String cookies){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlQueryTran());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());

        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),tranNum,"tranNum");
        String res = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        return jsonUtil.getValueByKeyFromJson(jsonStr,"data").toString();
    }


}
