package com.alone.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alone.pojo.confirm.ConfirmRequestParams;
import com.alone.pojo.confirm.ConfirmStockInfo;
import com.alone.pojo.event.PriceZoneInfo;
import com.alone.pojo.event.TicketTypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: hetilong
 * @Date: 2021/11/26 19:17
 *
 */
public class PriceZoneUtil {
    public static void main(String[] args) {
        String jsonStr = "{\"code\":200000,\"msg\":null,\"data\":{\"eventId\":1352,\"code\":null,\"nameEn\":\"es相关测试\",\"nameTc\":\"es相关测试\",\"nameSc\":\"es相关测试\",\"eventShortName\":\"es-test\",\"eventPoster\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/dda61fa7456c22197f3e504d261b5460.png\",\"eventSeriesCode\":\"TILONG\",\"venueNameEn\":\"alone-facility\",\"venueNameTc\":\"阿龍劇院阿龍劇院阿龍劇院阿龍劇院阿龍劇院阿龍劇院阿龍劇院阿龍劇院阿龍劇院阿龍劇院阿龍劇院\",\"venueNameSc\":\"阿龙剧院\",\"duration\":\"3h\",\"ageLimit\":null,\"level\":null,\"presenterId\":80,\"bukPurchase\":false,\"fewAmount\":90,\"selectSelf\":false,\"showSeatPlanSwitch\":false,\"freeSeatDefaultImage\":\"http://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/4f16b9b9abae77f00b29cbec9e1ce90e.png\",\"deliveryMethodNatureList\":[1,2],\"collectTicketLimitDays\":null,\"seatPlanViewType\":2,\"nature\":1,\"eventQuickSaleSwitch\":0,\"userOutletEventQuickSaleSwitch\":false,\"admissionQueueResponseList\":null,\"performanceList\":[{\"eventId\":1352,\"performanceId\":4757,\"performanceNameEn\":\"es相关测试\",\"performanceNameTc\":\"es相关测试\",\"performanceNameSc\":\"es相关测试\",\"beginTimeEn\":\"2021/11/30 Tue PM 11:00\",\"beginTimeTc\":\"2021/11/30 星期二 下午 11:00\",\"beginTimeSc\":\"2021/11/30 星期二 下午 11:00\",\"beginTime\":1638284400000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.3\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/355286f04cf9fc8f7c7960c36de08a8c.svg\",\"saleStartDate\":1637632800000,\"saleEndDate\":1638287999000,\"pubStock\":146644,\"blockStock\":0,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":null},{\"eventId\":1352,\"performanceId\":4779,\"performanceNameEn\":\"es相关测试\",\"performanceNameTc\":\"es相关测试\",\"performanceNameSc\":\"es相关测试\",\"beginTimeEn\":\"2021/12/01 Wed PM 11:00\",\"beginTimeTc\":\"2021/12/01 星期三 下午 11:00\",\"beginTimeSc\":\"2021/12/01 星期三 下午 11:00\",\"beginTime\":1638370800000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.3\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/355286f04cf9fc8f7c7960c36de08a8c.svg\",\"saleStartDate\":1637632800000,\"saleEndDate\":1638374399000,\"pubStock\":198104,\"blockStock\":0,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":null},{\"eventId\":1352,\"performanceId\":4780,\"performanceNameEn\":\"es相关测试\",\"performanceNameTc\":\"es相关测试\",\"performanceNameSc\":\"es相关测试\",\"beginTimeEn\":\"2021/12/02 Thu PM 11:00\",\"beginTimeTc\":\"2021/12/02 星期四 下午 11:00\",\"beginTimeSc\":\"2021/12/02 星期四 下午 11:00\",\"beginTime\":1638457200000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.3\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/355286f04cf9fc8f7c7960c36de08a8c.svg\",\"saleStartDate\":1637632800000,\"saleEndDate\":1638460799000,\"pubStock\":199007,\"blockStock\":0,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":null},{\"eventId\":1352,\"performanceId\":4781,\"performanceNameEn\":\"es相关测试\",\"performanceNameTc\":\"es相关测试\",\"performanceNameSc\":\"es相关测试\",\"beginTimeEn\":\"2021/12/03 Fri PM 11:00\",\"beginTimeTc\":\"2021/12/03 星期五 下午 11:00\",\"beginTimeSc\":\"2021/12/03 星期五 下午 11:00\",\"beginTime\":1638543600000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.3\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/355286f04cf9fc8f7c7960c36de08a8c.svg\",\"saleStartDate\":1637632800000,\"saleEndDate\":1638547199000,\"pubStock\":198867,\"blockStock\":0,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":null}],\"blockTypeList\":[{\"id\":0,\"code\":\"public\",\"nameEn\":\"Public Seats\",\"nameTc\":\"公眾票\",\"nameSc\":\"公众票\",\"sortSeq\":\"0\"}],\"priceZoneList\":[{\"priceZoneId\":1,\"code\":\"A\",\"priceZoneNameEn\":\"A\",\"priceZoneNameTc\":\"A\",\"priceZoneNameSc\":\"A\",\"color\":\"#FF363D\",\"nature\":1,\"arrangeWheelChair\":true,\"price\":1000,\"realNameSwitch\":0,\"sortSeq\":\"00001\",\"ticketTypeList\":[{\"ticketTypeId\":25,\"ticketTypeNature\":4,\"code\":\"STAN\",\"nameEn\":\"Standard\",\"nameTc\":\"正價票\",\"nameSc\":\"正价票\",\"price\":1000,\"maxTicketForPerformance\":null,\"maxTicketForTransaction\":null,\"maxQuotaForFirstCounterSalesDay\":null,\"maxSelectCount\":40},{\"ticketTypeId\":26,\"ticketTypeNature\":4,\"code\":\"STU\",\"nameEn\":\"Student\",\"nameTc\":\"學生優惠\",\"nameSc\":\"学生优惠\",\"price\":100,\"maxTicketForPerformance\":null,\"maxTicketForTransaction\":null,\"maxQuotaForFirstCounterSalesDay\":null,\"maxSelectCount\":40}],\"stockInfoList\":[{\"remainNormalStock\":46645,\"totalStock\":99999,\"remainWheelChairNumber\":null,\"remainMinderNumber\":null,\"type\":1,\"blockTypeId\":0}]},{\"priceZoneId\":2,\"code\":\"B\",\"priceZoneNameEn\":\"B\",\"priceZoneNameTc\":\"B\",\"priceZoneNameSc\":\"B\",\"color\":\"#00D408\",\"nature\":1,\"arrangeWheelChair\":true,\"price\":1000,\"realNameSwitch\":0,\"sortSeq\":\"00002\",\"ticketTypeList\":[{\"ticketTypeId\":25,\"ticketTypeNature\":4,\"code\":\"STAN\",\"nameEn\":\"Standard\",\"nameTc\":\"正價票\",\"nameSc\":\"正价票\",\"price\":1000,\"maxTicketForPerformance\":null,\"maxTicketForTransaction\":null,\"maxQuotaForFirstCounterSalesDay\":null,\"maxSelectCount\":40},{\"ticketTypeId\":26,\"ticketTypeNature\":4,\"code\":\"STU\",\"nameEn\":\"Student\",\"nameTc\":\"學生優惠\",\"nameSc\":\"学生优惠\",\"price\":100,\"maxTicketForPerformance\":null,\"maxTicketForTransaction\":null,\"maxQuotaForFirstCounterSalesDay\":null,\"maxSelectCount\":40}],\"stockInfoList\":[{\"remainNormalStock\":99999,\"totalStock\":99999,\"remainWheelChairNumber\":null,\"remainMinderNumber\":null,\"type\":1,\"blockTypeId\":0}]}],\"performanceDateTime\":null,\"eventDateEn\":null,\"eventDateTc\":null,\"eventDateSc\":null},\"paging\":null,\"tracing\":null,\"success\":true}";

        PriceZoneUtil priceZoneDemo= new PriceZoneUtil();
        List<PriceZoneInfo> priceZoneInfos=priceZoneDemo.getPriceZoneInfos(jsonStr);
        List<ConfirmRequestParams> confirmStockInfos=priceZoneDemo.getConfirmStockInfos(priceZoneInfos,"1352",6789,1,1);
        System.out.println(confirmStockInfos);
        //List<ConfirmStockInfo> confirmStockInfos1 = priceZoneDemo.getConfirmStockInfos(priceZoneInfos,
        //        "A","STAN",3);
        //System.out.println(confirmStockInfos1);
    }

    public List<PriceZoneInfo> getPriceZoneInfos(String jsonStr){
        JsonUtil jsonUtil = new JsonUtil();
        List<String> priceZoneList = jsonUtil.getValueByKeyFromJson(jsonStr,"priceZoneList");

        JSONArray arrayPriceZone = JSONArray.parseArray(priceZoneList.get(0));

        List<PriceZoneInfo> priceZones = new ArrayList<>();

        for(int i=0;i<arrayPriceZone.size();i++){
            String priceZone = arrayPriceZone.getString(i);
            PriceZoneInfo priceZoneInfo = JSONObject.parseObject(priceZone, PriceZoneInfo.class);
            priceZones.add(priceZoneInfo);
        }

        return priceZones;
    }

    public List<ConfirmRequestParams> getConfirmStockInfos(List<PriceZoneInfo> priceZoneInfos,String eventId,int performanceId,int posType,int stockNum){

        List<ConfirmRequestParams> confirmRequestParamsList = new ArrayList<>();
        for (PriceZoneInfo priceZone: priceZoneInfos) {
            for (TicketTypeInfo ticketType:priceZone.getTicketTypeList()) {
                ConfirmStockInfo confirmStockInfo = new ConfirmStockInfo(priceZone.getPriceZoneId(),
                        0,
                        1,
                        ticketType.getTicketTypeId(),
                        ticketType.getTicketTypeNature(),
                        stockNum,
                        1);

                ConfirmRequestParams confirmRequestParams = new ConfirmRequestParams(eventId,
                        performanceId,
                        confirmStockInfo,
                        posType);

                confirmRequestParamsList.add(confirmRequestParams);
            }
        }
        return confirmRequestParamsList;
    }

    public List<ConfirmStockInfo> getConfirmStockInfos(List<PriceZoneInfo> priceZoneInfos,String priceZoneCode,String ticketTypeCode,int stockNum){
        List<ConfirmStockInfo> confirmStockInfos = new ArrayList<>();
        for (PriceZoneInfo priceZone: priceZoneInfos) {
            if(priceZone.getCode().equals(priceZoneCode)){
                for (TicketTypeInfo ticketType:priceZone.getTicketTypeList()) {
                    if(ticketType.getCode().equals(ticketTypeCode)){
                        ConfirmStockInfo confirmStockInfo = new ConfirmStockInfo(priceZone.getPriceZoneId(),
                                0,
                                1,
                                ticketType.getTicketTypeId(),
                                ticketType.getTicketTypeNature(),
                                stockNum,
                                4);

                        confirmStockInfos.add(confirmStockInfo);
                    }
                }
            }

        }
        return confirmStockInfos;
    }
}
