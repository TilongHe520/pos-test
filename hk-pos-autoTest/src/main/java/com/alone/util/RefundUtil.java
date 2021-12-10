package com.alone.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alone.pojo.ticket.RefundAddInfo;
import com.alone.pojo.ticket.RefundSettleInfo;
import com.alone.pojo.ticket.TicketInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: hetilong
 * @Date: 2021/12/8 11:33
 */
public class RefundUtil {
    /**
     * 获取添加到退款购物车的参数
     * @param refundList
     * @return
     */
    public RefundAddInfo getRefundAddParams(String refundList){
        JsonUtil jsonUtil = new JsonUtil();
        String data = jsonUtil.getValueByKeyReturnString(refundList,"data");
        JSONArray jsonArray = JSONArray.parseArray(data);
        List<TicketInfo> ticketInfoList = new ArrayList<>();
        for (int i=0;i<jsonArray.size();i++){
            TicketInfo ticketInfo = JSONObject.parseObject(jsonArray.getString(i),TicketInfo.class);
            ticketInfo.setRefundType(0);
            ticketInfoList.add(ticketInfo);
        }

        List<String> eventId = jsonUtil.getValueByKeyFromJson(refundList,"eventId");
        List<String> performanceId = jsonUtil.getValueByKeyFromJson(refundList,"performanceId");
        RefundAddInfo refundAddInfo = new RefundAddInfo(eventId,performanceId,ticketInfoList);
        return refundAddInfo;
    }

    /**
     * 获取 退款接口参数
     * @param refundAddInfo
     * @return
     */
    public List<RefundSettleInfo> getRefundSettleParams(RefundAddInfo refundAddInfo){
        List<TicketInfo> ticketInfoList = refundAddInfo.getTicketInfoList();
        //Map<Integer,List<TicketInfo>> map =ticketInfoList.stream().collect(Collectors.groupingBy(TicketInfo::getPerformanceId));
        //谢坤哥提供方法
        Map<Integer, List<Integer>> map= ticketInfoList.stream().
                collect(Collectors.groupingBy(TicketInfo::getPerformanceId,
                        Collectors.mapping(TicketInfo::getTicketId, Collectors.toList())));
        System.out.println(map);
        List<RefundSettleInfo> refundSettleInfoList = new ArrayList<>();
        Set<Integer> keys = map.keySet();
        for (Integer k:keys) {
            RefundSettleInfo refundSettleInfo = new RefundSettleInfo(k,
                    0,
                    false,
                    map.get(k));
            refundSettleInfoList.add(refundSettleInfo);
        }
        return refundSettleInfoList;
    }
}
