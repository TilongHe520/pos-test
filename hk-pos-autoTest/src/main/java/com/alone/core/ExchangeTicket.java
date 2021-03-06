package com.alone.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alone.common.BaseBuyTicket;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.cart.CartSkuInfo;
import com.alone.pojo.confirm.ConfirmRequestParams;
import com.alone.pojo.confirm.ConfirmStockInfo;
import com.alone.pojo.confirm.ExchangeTicketInfo;
import com.alone.pojo.event.PriceZoneInfo;
import com.alone.pojo.print.UploadPrintInfo;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Author: hetilong
 * @Date: 2021/12/16 21:45
 */
public class ExchangeTicket extends BaseBuyTicket {


    public ExchangeTicket(TerminalInfo terminalInfo, EnvironmentInfo environmentInfo, LoginInfo loginInfo, String cookies, int posType, int eventId) {
        super(terminalInfo, environmentInfo, loginInfo, cookies, posType,eventId);
    }

    public String exchangeOrder(String tranNumber){
        JsonUtil jsonUtil = new JsonUtil();
        List<String> ticketIdExc = getTicketInfo(tranNumber);
        CartSkuInfo cartSkuInfo = checkExchange(ticketIdExc);
        List<ConfirmRequestParams> confirmRequestParamsList = exchangeDetail(cartSkuInfo,
                String.valueOf(eventId));
        String requestData = confirmStock(cartSkuInfo,confirmRequestParamsList.get(0));
        addToCart(requestData);

        String queryCartRes = queryCart();
        System.out.println(queryCartRes);

        String jsonStr = getTransactionReq(queryCartRes);

        String transactionId = creatTransaction(jsonStr);
        prepayResult(transactionId);
        String transactionNum = prepay(transactionId);

        String printRes = print(transactionNum);

        List<String> ticketIdList = jsonUtil.getValueByKeyFromJson(printRes,"ticketId");
        String taskId = jsonUtil.getValueByKeyReturnString(printRes,"taskId");

        List<UploadPrintInfo> uploadPrintInfoList = new UploadPrintUtil().getUploadPrintList(taskId,ticketIdList);

        uploadPrintResult(uploadPrintInfoList);
        return transactionNum;
    }

    /**
     * ?????????????????????
     * @param transactionNum
     * @return
     */
    public List<String> getTicketInfo(String transactionNum){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlListTicketEx());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());
        map.put("Cookie",cookies);

        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),transactionNum,"tranNumber");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,posType,"posType");
        String res = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        String responseList = jsonUtil.getValueByKeyReturnString(res,"posTicketTransactionResponseList");
        JSONArray jsonArray = JSONArray.parseArray(responseList);
        List<String> ticketId = new ArrayList<>();
        for (Object o:jsonArray) {
            if(jsonUtil.getValueByKeyReturnString(o.toString(),"allowExchangeFlag").equals("0")){
                String id = jsonUtil.getValueByKeyReturnString(o.toString(),"id");
                ticketId.add(id);
                break;
            }
        }
        return ticketId;
    }

    /**
     * ????????????
     * @param ids
     * @return ?????? ID
     */
    public CartSkuInfo checkExchange(List<String> ids){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlCheckExchange());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());
        map.put("Cookie",cookies);

        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),ids,"ids");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,posType,"posType");
        String res = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();

        JSONArray s = JSONArray.parseArray(jsonUtil.getValueByKeyFromJson(res,"cartSkuInfoList").get(0));
        System.out.println(s);
        CartSkuInfo cartSkuInfo = JSONObject.parseObject(s.getString(0),CartSkuInfo.class);
        return cartSkuInfo;
    }

    /**
     * ?????????????????????ConfirmStock????????????
     * @param cartSkuInfo
     * @param eventId
     * @return
     */
    public List<ConfirmRequestParams> exchangeDetail(CartSkuInfo cartSkuInfo,String eventId){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlDetail());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());
        map.put("Cookie",cookies);

        Map dataMap = JSON.parseObject(cp.getData(),Map.class);
        dataMap.put("performanceId",cartSkuInfo.getPerformanceId());
        dataMap.put("ticketId",cartSkuInfo.getTicketId());
        dataMap.put("menuType",posType);
        dataMap.put("eventId",eventId);

        String data = JSONObject.toJSONString(dataMap);
        String detailRes = given().headers(map).body(data).post(cp.getUrl()).asString();

        PriceZoneUtil priceZoneUtil= new PriceZoneUtil();
        List<PriceZoneInfo> priceZoneInfos=priceZoneUtil.getPriceZoneInfos(detailRes);

        List<ConfirmRequestParams> confirmStockInfos=priceZoneUtil.getExchangeConfirmStockInfos(priceZoneInfos,
                eventId,
                Integer.valueOf(cartSkuInfo.getPerformanceId()),
                posType,
                1,
                cartSkuInfo.getOldTicketPrice());
        return confirmStockInfos;
    }

    /**
     * ?????????????????????????????????????????????
     * @param confirmRequestParams
     * @return
     */
    public  String confirmStock(CartSkuInfo cartSkuInfo,ConfirmRequestParams confirmRequestParams){
        ExchangeTicketInfo exchangeTicketInfo = new ExchangeTicketInfo(Integer.valueOf(confirmRequestParams.getEventId()),
                cartSkuInfo.getPerformanceId(),
                cartSkuInfo.getPriceZoneId(),
                cartSkuInfo.getTicketId(),
                cartSkuInfo.getOriginalTicketTypeId());
        List<ExchangeTicketInfo> exchangeTicketInfoList = new ArrayList<>();
        exchangeTicketInfoList.add(exchangeTicketInfo);
        JSONArray jsonArray1 = (JSONArray) JSONArray.toJSON(exchangeTicketInfoList);

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
        jsonStr = jsonUtil.updateJsonStr(jsonStr,jsonArray1,"exchangeTicketInfoList");

        String response = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        System.out.println(response);
        String requestData = null;
        if (jsonUtil.getValueByKeyReturnString(response, "seatDTOList") != null) {
            requestData = new SeatUtil().addRequestParams(response, loginInfo.getTerminalCode(), posType,confirmRequestParams.getEventId(),cartSkuInfo);
        }
        if (jsonUtil.getValueByKeyReturnString(response, "noSeatDTOList") != null) {

            requestData = new SeatUtil().addRequestParams(response, posType, loginInfo.getTerminalCode(),confirmRequestParams.getEventId(),cartSkuInfo);
        }
        return requestData;
    }

    /**
     * ???????????????
     * @param requestData
     * @return
     */
    @Override
    public  String addToCart(String requestData){

        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlAddToCart());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalCode());

        String response = given().headers(map).body(requestData).post(cp.getUrl()).asString();

        return response;
    }

}
