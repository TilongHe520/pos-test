package com.alone.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alone.pojo.cart.CartSkuInfo;
import com.alone.pojo.seat.NoSeatInfo;
import com.alone.pojo.seat.SeatInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TiLongHe
 */
public class SeatUtil {

    /**
     * 获取free seat座位信息
     * @return
     */
    public ArrayList<NoSeatInfo> getNoSeatInfo(String jsonStr){
        JsonUtil jsonUtil = new JsonUtil();
        String noSeatDTO= jsonUtil.getValueByKeyFromJson(jsonStr,"noSeatDTOList").get(0);
        JSONArray array = JSONArray.parseArray(noSeatDTO);
        ArrayList<NoSeatInfo> arrayList = new ArrayList<>();
        for(int i = 0;i<array.size();i++){
            String data = array.getString(i);
            NoSeatInfo noSeatInfo = JSONObject.parseObject(data,NoSeatInfo.class);
            arrayList.add(noSeatInfo);
        }
        return arrayList;
    }

    /**
     * 获取seat座位信息
     * @return
     */
    public ArrayList<SeatInfo> getSeatInfo(String jsonStr){
        JsonUtil jsonUtil = new JsonUtil();
        String SeatDTO= jsonUtil.getValueByKeyFromJson(jsonStr,"seatDTOList").get(0);
        JSONArray array = JSONArray.parseArray(SeatDTO);
        ArrayList<SeatInfo> arrayList = new ArrayList<>();
        for(int i = 0;i<array.size();i++){
            String data = array.getString(i);
            SeatInfo SeatInfo = JSONObject.parseObject(data,SeatInfo.class);
            arrayList.add(SeatInfo);
        }
        return arrayList;
    }


    /**
     * 填充加入购物车freeSeat座位信息
     * @param requestData
     * @param arr
     * @return
     */
    public String getAddToCartSeatParams(String requestData,ArrayList<NoSeatInfo> arr){
        ArrayList<CartSkuInfo> arrCart = new ArrayList<>();
        for(int i = 0;i<arr.size();i++){
            CartSkuInfo cartSkuInfo = new CartSkuInfo();
            cartSkuInfo.setPerformanceId(arr.get(i).getPerformanceId());
            cartSkuInfo.setSeatType(arr.get(i).getSeatType());
            cartSkuInfo.setBlockTypeId(arr.get(i).getBlockTypeId());
            cartSkuInfo.setOriginalTicketTypeId(arr.get(i).getTicketTypeId());
            cartSkuInfo.setUniqueId(arr.get(i).getUniqueId());
            cartSkuInfo.setPohId(arr.get(i).getPohId());
            cartSkuInfo.setPriceZoneId(arr.get(i).getPriceZoneId());

            arrCart.add(cartSkuInfo);
        }

        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(arrCart);
        JsonUtil jsonUtil = new JsonUtil();
        String data = jsonUtil.updateJsonStr(requestData,jsonArray,"cartSkuInfoList");
        ArrayList<Integer> performanceList = new ArrayList<>();
        performanceList.add(arr.get(0).getPerformanceId());
        JSONArray jsonArray1 = (JSONArray) JSONArray.toJSON(performanceList);
        data = jsonUtil.updateJsonStr(data,jsonArray1,"performanceIdList");
        return data;
    }

    /**
     * 填充加入购物车Seat座位信息
     * @param requestData
     * @param arr
     * @return
     */
    public String getAddToCartSeatParams(ArrayList<SeatInfo> arr,String requestData){
        ArrayList<CartSkuInfo> arrCart = new ArrayList<>();
        for(int i = 0;i<arr.size();i++){
            CartSkuInfo cartSkuInfo = new CartSkuInfo();
            cartSkuInfo.setSwapInTicket(false);
            cartSkuInfo.setSeatType(arr.get(i).getSeatType());
            cartSkuInfo.setBlockTypeId(arr.get(i).getBlockTypeId());
            cartSkuInfo.setOriginalTicketTypeId(arr.get(i).getTicketTypeId());
            cartSkuInfo.setUniqueId(arr.get(i).getUniqueId());
            cartSkuInfo.setPohId(arr.get(i).getPohId());
            cartSkuInfo.setPerformanceId(arr.get(i).getPerformanceId());
            cartSkuInfo.setPriceZoneId(arr.get(i).getPriceZoneId());
            cartSkuInfo.setNeedSeat(true);
            cartSkuInfo.setSeatId(arr.get(i).getId());

            arrCart.add(cartSkuInfo);
        }

        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(arrCart);
        JsonUtil jsonUtil = new JsonUtil();
        String data = jsonUtil.updateJsonStr(requestData,jsonArray,"cartSkuInfoList");
        ArrayList<Integer> performanceList = new ArrayList<>();
        performanceList.add(arr.get(0).getPerformanceId());
        JSONArray jsonArray1 = (JSONArray) JSONArray.toJSON(performanceList);
        data = jsonUtil.updateJsonStr(data,jsonArray1,"performanceIdList");
        return data;
    }

    public String addRequestParams(String response, int posType, String terminalCode,String eventId){
        String jsonStr="{\"posType\": 1,\"eventIdList\": [1352],\"performanceIdList\": [4781],\"cartSkuInfoList\": [],\"promotionCodeList\": [],\"memberList\": [],\"terminalId\": \"terminal-test\"}";

        JsonUtil jsonUtil = new JsonUtil();
        SeatUtil seatUtil = new SeatUtil();
        ArrayList<NoSeatInfo> arr = seatUtil.getNoSeatInfo(response);


        String requestData = seatUtil.getAddToCartSeatParams(jsonStr,arr);

        requestData = jsonUtil.updateJsonStr(requestData, posType,"posType");
        requestData = jsonUtil.updateJsonStr(requestData,terminalCode,"terminalId");
        requestData = requestData.replace("[1352]","["+eventId+"]");

        return  requestData;
    }


    public String addRequestParams(String response,String terminalCode,int posType,String eventId){
        JsonUtil jsonUtil = new JsonUtil();
        SeatUtil seatUtil = new SeatUtil();
        String jsonStr="{\"posType\": 1,\"eventIdList\": [1352],\"performanceIdList\": [4781],\"cartSkuInfoList\": [],\"promotionCodeList\": [],\"memberList\": [],\"terminalId\": \"terminal-test\"}";
        ArrayList<SeatInfo> seatInfos = seatUtil.getSeatInfo(response);

        String requestData = seatUtil.getAddToCartSeatParams(seatInfos,jsonStr);
        requestData = jsonUtil.updateJsonStr(requestData, posType,"posType");
        requestData = jsonUtil.updateJsonStr(requestData,terminalCode,"terminalId");
        requestData = requestData.replace("[1352]","["+eventId+"]");
        return requestData;
    }



    /**
     * 换票加入购物车freeSeat座位信息
     * @param requestData
     * @param arr
     * @return
     */
    public String getAddToCartSeatParams(String requestData,ArrayList<NoSeatInfo> arr,CartSkuInfo older){
        List<Object> arrCart = new ArrayList<>();
        for(int i = 0;i<arr.size();i++){
            CartSkuInfo cartSkuInfo = new CartSkuInfo();
            cartSkuInfo.setPerformanceId(arr.get(i).getPerformanceId());
            cartSkuInfo.setSeatType(arr.get(i).getSeatType());
            cartSkuInfo.setBlockTypeId(arr.get(i).getBlockTypeId());
            cartSkuInfo.setOriginalTicketTypeId(arr.get(i).getTicketTypeId());
            cartSkuInfo.setUniqueId(arr.get(i).getUniqueId());
            cartSkuInfo.setPohId(arr.get(i).getPohId());
            cartSkuInfo.setPriceZoneId(arr.get(i).getPriceZoneId());
            cartSkuInfo.setSwapInTicket(true);

            arrCart.add(cartSkuInfo);
        }
        arrCart.add(older);
        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(arrCart);
        JsonUtil jsonUtil = new JsonUtil();
        String data = jsonUtil.updateJsonStr(requestData,jsonArray,"cartSkuInfoList");
        ArrayList<Integer> performanceList = new ArrayList<>();
        performanceList.add(arr.get(0).getPerformanceId());
        JSONArray jsonArray1 = (JSONArray) JSONArray.toJSON(performanceList);
        data = jsonUtil.updateJsonStr(data,jsonArray1,"performanceIdList");
        return data;
    }

    /**
     * 换票填充加入购物车Seat座位信息
     * @param requestData
     * @param arr
     * @return
     */
    public String getAddToCartSeatParams(ArrayList<SeatInfo> arr,String requestData,CartSkuInfo older){
        List<Object> arrCart = new ArrayList<>();
        for(int i = 0;i<arr.size();i++){
            CartSkuInfo cartSkuInfo = new CartSkuInfo();
            cartSkuInfo.setSwapInTicket(true);
            cartSkuInfo.setSeatType(arr.get(i).getSeatType());
            cartSkuInfo.setBlockTypeId(arr.get(i).getBlockTypeId());
            cartSkuInfo.setOriginalTicketTypeId(arr.get(i).getTicketTypeId());
            cartSkuInfo.setUniqueId(arr.get(i).getUniqueId());
            cartSkuInfo.setPohId(arr.get(i).getPohId());
            cartSkuInfo.setPerformanceId(arr.get(i).getPerformanceId());
            cartSkuInfo.setPriceZoneId(arr.get(i).getPriceZoneId());
            cartSkuInfo.setNeedSeat(true);
            cartSkuInfo.setSeatId(arr.get(i).getId());

            arrCart.add(cartSkuInfo);
        }
        arrCart.add(older);
        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(arrCart);
        JsonUtil jsonUtil = new JsonUtil();
        String data = jsonUtil.updateJsonStr(requestData,jsonArray,"cartSkuInfoList");
        ArrayList<Integer> performanceList = new ArrayList<>();
        performanceList.add(arr.get(0).getPerformanceId());
        JSONArray jsonArray1 = (JSONArray) JSONArray.toJSON(performanceList);
        data = jsonUtil.updateJsonStr(data,jsonArray1,"performanceIdList");
        return data;
    }

    /**
     * 换票
     * @param response
     * @param posType
     * @param terminalCode
     * @param eventId
     * @param older
     * @return
     */
    public String addRequestParams(String response, int posType, String terminalCode,String eventId,CartSkuInfo older){
        String jsonStr="{\"posType\": 1,\"eventIdList\": [1352],\"performanceIdList\": [4781],\"cartSkuInfoList\": [],\"promotionCodeList\": [],\"memberList\": [],\"terminalId\": \"terminal-test\"}";

        JsonUtil jsonUtil = new JsonUtil();
        SeatUtil seatUtil = new SeatUtil();
        ArrayList<NoSeatInfo> arr = seatUtil.getNoSeatInfo(response);


        String requestData = seatUtil.getAddToCartSeatParams(jsonStr,arr,older);

        requestData = jsonUtil.updateJsonStr(requestData, posType,"posType");
        requestData = jsonUtil.updateJsonStr(requestData,terminalCode,"terminalId");
        requestData = requestData.replace("[1352]","["+eventId+"]");

        return  requestData;
    }

    /**
     * 换票
     * @param response
     * @param terminalCode
     * @param posType
     * @param eventId
     * @param older
     * @return
     */
    public String addRequestParams(String response,String terminalCode,int posType,String eventId,CartSkuInfo older){
        JsonUtil jsonUtil = new JsonUtil();
        SeatUtil seatUtil = new SeatUtil();
        String jsonStr="{\"posType\": 1,\"eventIdList\": [1352],\"performanceIdList\": [4781],\"cartSkuInfoList\": [],\"promotionCodeList\": [],\"memberList\": [],\"terminalId\": \"terminal-test\"}";
        ArrayList<SeatInfo> seatInfos = seatUtil.getSeatInfo(response);

        String requestData = seatUtil.getAddToCartSeatParams(seatInfos,jsonStr,older);
        requestData = jsonUtil.updateJsonStr(requestData, posType,"posType");
        requestData = jsonUtil.updateJsonStr(requestData,terminalCode,"terminalId");
        requestData = requestData.replace("[1352]","["+eventId+"]");
        return requestData;
    }
}
