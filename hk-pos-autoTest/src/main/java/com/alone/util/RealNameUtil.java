package com.alone.util;

import com.alibaba.fastjson.JSONArray;
import com.alone.pojo.cart.TicketRealNameInfo;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: hetilong
 * @Date: 2021/12/7 10:24
 */
public class RealNameUtil {
    public JSONArray getTicketRealNameJsonArray(String queryCartRes){
        JsonUtil jsonUtil = new JsonUtil();
        List<String> uniqueIdList = jsonUtil.getValueByKeyFromJson(queryCartRes,"uniqueId");
        List<String> realNameList = jsonUtil.getValueByKeyFromJson(queryCartRes,"needRealName");
        List<TicketRealNameInfo> ticketRealNameInfoList = new ArrayList<>();
        if(realNameList!=null){
            for (int i=0;i<realNameList.size();i++){
                if("true".equals(realNameList.get(i))){
                    TicketRealNameInfo ticketRealNameInfo = new TicketRealNameInfo(uniqueIdList.get(i),
                            RandomStringUtils.randomAlphabetic(4).toUpperCase(),
                            RandomStringUtils.randomAlphabetic(4).toUpperCase());

                    ticketRealNameInfoList.add(ticketRealNameInfo);
                }
            }
        }

        JSONArray jsonArray =(JSONArray) JSONArray.toJSON(ticketRealNameInfoList);
        return jsonArray;
    }

    public List<TicketRealNameInfo> getTicketRealList(String queryCartRes){
        JsonUtil jsonUtil = new JsonUtil();
        List<String> uniqueIdList = jsonUtil.getValueByKeyFromJson(queryCartRes,"uniqueId");
        List<String> realNameList = jsonUtil.getValueByKeyFromJson(queryCartRes,"needRealName");
        List<TicketRealNameInfo> ticketRealNameInfoList = new ArrayList<>();
        if(realNameList!=null){
            for (int i=0;i<realNameList.size();i++){
                if("true".equals(realNameList.get(i))){
                    TicketRealNameInfo ticketRealNameInfo = new TicketRealNameInfo(uniqueIdList.get(i),
                            RandomStringUtils.randomAlphabetic(4).toUpperCase(),
                            RandomStringUtils.randomAlphabetic(4).toUpperCase());

                    ticketRealNameInfoList.add(ticketRealNameInfo);
                }
            }
        }
        return ticketRealNameInfoList;
    }
}
