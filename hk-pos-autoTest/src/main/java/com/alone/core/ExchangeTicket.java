package com.alone.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alone.common.BaseCommon;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.cart.CartSkuInfo;
import com.alone.pojo.confirm.ConfirmRequestParams;
import com.alone.pojo.confirm.ConfirmStockInfo;
import com.alone.pojo.confirm.ExchangeTicketInfo;
import com.alone.pojo.event.PriceZoneInfo;
import com.alone.util.JsonUtil;
import com.alone.util.PriceZoneUtil;
import com.alone.util.ResolveCurl;
import com.alone.util.SeatUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Author: hetilong
 * @Date: 2021/12/16 21:45
 */
public class ExchangeTicket extends BaseCommon {

    public ExchangeTicket(EnvironmentInfo environmentInfo, LoginInfo loginInfo, String cookies, int posType) {
        super(environmentInfo, loginInfo, cookies, posType);
    }

    /**
     * 获取换票的信息
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
     * 换票校验
     * @param ids
     * @return 场次 ID
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
     * 换票详情并返回ConfirmStock请求参数
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
     * 自组选座，获取加入购物车的参数
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
     * 加入购物车
     * @param requestData
     * @return
     */
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
