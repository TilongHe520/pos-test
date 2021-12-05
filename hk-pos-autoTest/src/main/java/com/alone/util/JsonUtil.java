package com.alone.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author: hetilong
 * @Date: 2021/12/3 13:32
 */
public class JsonUtil {
    List<String> list = new ArrayList<>();

    public static void main(String[] args){
        String jsonStr = "{\"A\":\"b\",\"array\":[1,2,3,{\"F\":\"c\",\"B\":\"L\"}],\"S\":\"b\",\"N\":null,\"json\":{\"b\":\"B\",\"A\":\"c\"}}";
        String jsonStr1 = "{\"code\":200000,\"msg\":null,\"data\":{\"eventId\":1296,\"code\":null,\"nameEn\":\"exchange wheelchair Facility-allfree\",\"nameTc\":\"換領輪椅設施-allfree\",\"nameSc\":\"换领轮椅设施-allfree\",\"eventShortName\":\"alone-allfree\",\"eventPoster\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/dda61fa7456c22197f3e504d261b5460.png\",\"eventSeriesCode\":\"TILONG\",\"venueNameEn\":\"alone-facility\",\"venueNameTc\":\"阿龍劇院阿龍劇院阿龍劇院阿龍劇院阿龍劇院阿龍劇院阿龍劇院阿龍劇院阿龍劇院阿龍劇院阿龍劇院\",\"venueNameSc\":\"阿龙剧院\",\"duration\":\"3h\",\"ageLimit\":null,\"level\":null,\"presenterId\":69,\"bukPurchase\":false,\"fewAmount\":90,\"selectSelf\":false,\"showSeatPlanSwitch\":true,\"freeSeatDefaultImage\":\"http://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/4f16b9b9abae77f00b29cbec9e1ce90e.png\",\"deliveryMethodNatureList\":[1,2],\"collectTicketLimitDays\":null,\"seatPlanViewType\":2,\"nature\":1,\"admissionQueueResponseList\":null,\"performanceList\":[{\"eventId\":1296,\"performanceId\":4248,\"performanceNameEn\":\"exchange wheelchair Facility-allfree\",\"performanceNameTc\":\"換領輪椅設施-allfree\",\"performanceNameSc\":\"换领轮椅设施-allfree\",\"beginTimeEn\":\"2021/11/30 Tue AM 05:00\",\"beginTimeTc\":\"2021/11/30 星期二 上午 05:00\",\"beginTimeSc\":\"2021/11/30 星期二 上午 05:00\",\"beginTime\":1638219600000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.1\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/856c672df8d985779c9ea937a5450ab4.svg\",\"saleStartDate\":1636509600000,\"saleEndDate\":1638279540000,\"pubStock\":2374,\"blockStock\":460,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":null},{\"eventId\":1296,\"performanceId\":4689,\"performanceNameEn\":\"exchange wheelchair Facility-allfree\",\"performanceNameTc\":\"換領輪椅設施-allfree\",\"performanceNameSc\":\"换领轮椅设施-allfree\",\"beginTimeEn\":\"2021/12/01 Wed AM 05:00\",\"beginTimeTc\":\"2021/12/01 星期三 上午 05:00\",\"beginTimeSc\":\"2021/12/01 星期三 上午 05:00\",\"beginTime\":1638306000000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.1\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/856c672df8d985779c9ea937a5450ab4.svg\",\"saleStartDate\":1636509600000,\"saleEndDate\":1638365940000,\"pubStock\":2440,\"blockStock\":560,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":\"I\"},{\"eventId\":1296,\"performanceId\":4690,\"performanceNameEn\":\"exchange wheelchair Facility-allfree\",\"performanceNameTc\":\"換領輪椅設施-allfree\",\"performanceNameSc\":\"换领轮椅设施-allfree\",\"beginTimeEn\":\"2021/12/02 Thu AM 05:00\",\"beginTimeTc\":\"2021/12/02 星期四 上午 05:00\",\"beginTimeSc\":\"2021/12/02 星期四 上午 05:00\",\"beginTime\":1638392400000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.1\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/856c672df8d985779c9ea937a5450ab4.svg\",\"saleStartDate\":1636509600000,\"saleEndDate\":1638452340000,\"pubStock\":1600,\"blockStock\":560,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":\"I\"},{\"eventId\":1296,\"performanceId\":4691,\"performanceNameEn\":\"exchange wheelchair Facility-allfree\",\"performanceNameTc\":\"換領輪椅設施-allfree\",\"performanceNameSc\":\"换领轮椅设施-allfree\",\"beginTimeEn\":\"2021/12/03 Fri AM 05:00\",\"beginTimeTc\":\"2021/12/03 星期五 上午 05:00\",\"beginTimeSc\":\"2021/12/03 星期五 上午 05:00\",\"beginTime\":1638478800000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.1\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/a02cb9ad74c1d8e26be41baf02f5b5c1.svg\",\"saleStartDate\":1636509600000,\"saleEndDate\":1638538740000,\"pubStock\":1600,\"blockStock\":560,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":\"I\"},{\"eventId\":1296,\"performanceId\":4695,\"performanceNameEn\":\"exchange wheelchair Facility-allfree\",\"performanceNameTc\":\"換領輪椅設施-allfree\",\"performanceNameSc\":\"换领轮椅设施-allfree\",\"beginTimeEn\":\"2021/12/09 Thu AM 05:00\",\"beginTimeTc\":\"2021/12/09 星期四 上午 05:00\",\"beginTimeSc\":\"2021/12/09 星期四 上午 05:00\",\"beginTime\":1638997200000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.1\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/a02cb9ad74c1d8e26be41baf02f5b5c1.svg\",\"saleStartDate\":1636509600000,\"saleEndDate\":1639057140000,\"pubStock\":1600,\"blockStock\":560,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":\"I\"},{\"eventId\":1296,\"performanceId\":4696,\"performanceNameEn\":\"exchange wheelchair Facility-allfree\",\"performanceNameTc\":\"換領輪椅設施-allfree\",\"performanceNameSc\":\"换领轮椅设施-allfree\",\"beginTimeEn\":\"2021/12/10 Fri AM 05:00\",\"beginTimeTc\":\"2021/12/10 星期五 上午 05:00\",\"beginTimeSc\":\"2021/12/10 星期五 上午 05:00\",\"beginTime\":1639083600000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.1\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/a02cb9ad74c1d8e26be41baf02f5b5c1.svg\",\"saleStartDate\":1636509600000,\"saleEndDate\":1639143540000,\"pubStock\":2440,\"blockStock\":560,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":\"I\"},{\"eventId\":1296,\"performanceId\":4697,\"performanceNameEn\":\"exchange wheelchair Facility-allfree\",\"performanceNameTc\":\"換領輪椅設施-allfree\",\"performanceNameSc\":\"换领轮椅设施-allfree\",\"beginTimeEn\":\"2021/12/11 Sat AM 05:00\",\"beginTimeTc\":\"2021/12/11 星期六 上午 05:00\",\"beginTimeSc\":\"2021/12/11 星期六 上午 05:00\",\"beginTime\":1639170000000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.1\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/a02cb9ad74c1d8e26be41baf02f5b5c1.svg\",\"saleStartDate\":1636509600000,\"saleEndDate\":1639229940000,\"pubStock\":2440,\"blockStock\":560,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":\"I\"},{\"eventId\":1296,\"performanceId\":4698,\"performanceNameEn\":\"exchange wheelchair Facility-allfree\",\"performanceNameTc\":\"換領輪椅設施-allfree\",\"performanceNameSc\":\"换领轮椅设施-allfree\",\"beginTimeEn\":\"2021/12/12 Sun AM 05:00\",\"beginTimeTc\":\"2021/12/12 星期日 上午 05:00\",\"beginTimeSc\":\"2021/12/12 星期日 上午 05:00\",\"beginTime\":1639256400000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.1\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/a02cb9ad74c1d8e26be41baf02f5b5c1.svg\",\"saleStartDate\":1636509600000,\"saleEndDate\":1639316340000,\"pubStock\":3440,\"blockStock\":560,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":\"I\"},{\"eventId\":1296,\"performanceId\":4699,\"performanceNameEn\":\"exchange wheelchair Facility-allfree\",\"performanceNameTc\":\"換領輪椅設施-allfree\",\"performanceNameSc\":\"换领轮椅设施-allfree\",\"beginTimeEn\":\"2021/12/13 Mon AM 05:00\",\"beginTimeTc\":\"2021/12/13 星期一 上午 05:00\",\"beginTimeSc\":\"2021/12/13 星期一 上午 05:00\",\"beginTime\":1639342800000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.1\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/a02cb9ad74c1d8e26be41baf02f5b5c1.svg\",\"saleStartDate\":1636509600000,\"saleEndDate\":1639402740000,\"pubStock\":3440,\"blockStock\":560,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":\"I\"},{\"eventId\":1296,\"performanceId\":4700,\"performanceNameEn\":\"exchange wheelchair Facility-allfree\",\"performanceNameTc\":\"換領輪椅設施-allfree\",\"performanceNameSc\":\"换领轮椅设施-allfree\",\"beginTimeEn\":\"2021/12/14 Tue AM 05:00\",\"beginTimeTc\":\"2021/12/14 星期二 上午 05:00\",\"beginTimeSc\":\"2021/12/14 星期二 上午 05:00\",\"beginTime\":1639429200000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.1\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/a02cb9ad74c1d8e26be41baf02f5b5c1.svg\",\"saleStartDate\":1636509600000,\"saleEndDate\":1639489140000,\"pubStock\":2440,\"blockStock\":560,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":\"I\"},{\"eventId\":1296,\"performanceId\":4701,\"performanceNameEn\":\"exchange wheelchair Facility-allfree\",\"performanceNameTc\":\"換領輪椅設施-allfree\",\"performanceNameSc\":\"换领轮椅设施-allfree\",\"beginTimeEn\":\"2021/12/15 Wed AM 05:00\",\"beginTimeTc\":\"2021/12/15 星期三 上午 05:00\",\"beginTimeSc\":\"2021/12/15 星期三 上午 05:00\",\"beginTime\":1639515600000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.1\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/a02cb9ad74c1d8e26be41baf02f5b5c1.svg\",\"saleStartDate\":1636509600000,\"saleEndDate\":1639575540000,\"pubStock\":2440,\"blockStock\":560,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":\"I\"},{\"eventId\":1296,\"performanceId\":4702,\"performanceNameEn\":\"exchange wheelchair Facility-allfree\",\"performanceNameTc\":\"換領輪椅設施-allfree\",\"performanceNameSc\":\"换领轮椅设施-allfree\",\"beginTimeEn\":\"2021/12/16 Thu AM 05:00\",\"beginTimeTc\":\"2021/12/16 星期四 上午 05:00\",\"beginTimeSc\":\"2021/12/16 星期四 上午 05:00\",\"beginTime\":1639602000000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.1\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/a02cb9ad74c1d8e26be41baf02f5b5c1.svg\",\"saleStartDate\":1636509600000,\"saleEndDate\":1639661940000,\"pubStock\":2440,\"blockStock\":560,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":\"I\"},{\"eventId\":1296,\"performanceId\":4703,\"performanceNameEn\":\"exchange wheelchair Facility-allfree\",\"performanceNameTc\":\"換領輪椅設施-allfree\",\"performanceNameSc\":\"换领轮椅设施-allfree\",\"beginTimeEn\":\"2021/12/17 Fri AM 05:00\",\"beginTimeTc\":\"2021/12/17 星期五 上午 05:00\",\"beginTimeSc\":\"2021/12/17 星期五 上午 05:00\",\"beginTime\":1639688400000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.1\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/a02cb9ad74c1d8e26be41baf02f5b5c1.svg\",\"saleStartDate\":1636509600000,\"saleEndDate\":1639748340000,\"pubStock\":2440,\"blockStock\":560,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":\"I\"},{\"eventId\":1296,\"performanceId\":4704,\"performanceNameEn\":\"exchange wheelchair Facility-allfree\",\"performanceNameTc\":\"換領輪椅設施-allfree\",\"performanceNameSc\":\"换领轮椅设施-allfree\",\"beginTimeEn\":\"2021/12/18 Sat AM 05:00\",\"beginTimeTc\":\"2021/12/18 星期六 上午 05:00\",\"beginTimeSc\":\"2021/12/18 星期六 上午 05:00\",\"beginTime\":1639774800000,\"duration\":\"3h\",\"status\":1,\"stockMap\":null,\"limitStockMap\":null,\"seatChartCode\":\"PL377.1\",\"seatChartUrl\":\"https://mycos-main-1304240968.cos.ap-hongkong.myqcloud.com/hk-show-admin/test/a02cb9ad74c1d8e26be41baf02f5b5c1.svg\",\"saleStartDate\":1636509600000,\"saleEndDate\":1639834740000,\"pubStock\":3440,\"blockStock\":560,\"maxTicketNumPerTran\":40,\"maxTicketNumOfComplimentary\":null,\"maxTicketNumOfWchPerTran\":null,\"level\":\"I\"}],\"blockTypeList\":[{\"id\":0,\"code\":\"public\",\"nameEn\":\"Public Seats\",\"nameTc\":\"公眾票\",\"nameSc\":\"公众票\",\"sortSeq\":\"0\"},{\"id\":196,\"code\":\"H1\",\"nameEn\":\"ALONE1\",\"nameTc\":\"ALONE1\",\"nameSc\":\"ALONE1\",\"sortSeq\":\"000000\"},{\"id\":197,\"code\":\"H2\",\"nameEn\":\"ALONE2\",\"nameTc\":\"ALONE2\",\"nameSc\":\"ALONE2\",\"sortSeq\":\"00002\"},{\"id\":198,\"code\":\"H3\",\"nameEn\":\"alone3\",\"nameTc\":\"alone3\",\"nameSc\":\"alone3\",\"sortSeq\":\"00003\"}],\"priceZoneList\":[{\"priceZoneId\":1,\"code\":\"A\",\"priceZoneNameEn\":\"A\",\"priceZoneNameTc\":\"A\",\"priceZoneNameSc\":\"A\",\"color\":\"#FF363D\",\"nature\":1,\"arrangeWheelChair\":true,\"price\":100,\"realNameSwitch\":0,\"sortSeq\":\"00001\",\"ticketTypeList\":[{\"ticketTypeId\":25,\"ticketTypeNature\":4,\"code\":\"STAN\",\"nameEn\":\"Standard\",\"nameTc\":\"正價票\",\"nameSc\":\"正价票\",\"price\":100,\"maxTicketForPerformance\":null,\"maxTicketForTransaction\":null,\"maxQuotaForFirstCounterSalesDay\":null,\"maxSelectCount\":40}],\"stockInfoList\":[{\"remainNormalStock\":778,\"totalStock\":834,\"remainWheelChairNumber\":null,\"remainMinderNumber\":null,\"type\":1,\"blockTypeId\":0},{\"remainNormalStock\":30,\"totalStock\":30,\"remainWheelChairNumber\":null,\"remainMinderNumber\":null,\"type\":3,\"blockTypeId\":196},{\"remainNormalStock\":60,\"totalStock\":60,\"remainWheelChairNumber\":null,\"remainMinderNumber\":null,\"type\":3,\"blockTypeId\":198}]},{\"priceZoneId\":2,\"code\":\"B\",\"priceZoneNameEn\":\"B\",\"priceZoneNameTc\":\"B\",\"priceZoneNameSc\":\"B\",\"color\":\"#00D408\",\"nature\":1,\"arrangeWheelChair\":true,\"price\":100,\"realNameSwitch\":1,\"sortSeq\":\"00002\",\"ticketTypeList\":[{\"ticketTypeId\":25,\"ticketTypeNature\":4,\"code\":\"STAN\",\"nameEn\":\"Standard\",\"nameTc\":\"正價票\",\"nameSc\":\"正价票\",\"price\":100,\"maxTicketForPerformance\":null,\"maxTicketForTransaction\":null,\"maxQuotaForFirstCounterSalesDay\":null,\"maxSelectCount\":40}],\"stockInfoList\":[{\"remainNormalStock\":1596,\"totalStock\":1600,\"remainWheelChairNumber\":null,\"remainMinderNumber\":null,\"type\":1,\"blockTypeId\":0},{\"remainNormalStock\":300,\"totalStock\":300,\"remainWheelChairNumber\":null,\"remainMinderNumber\":null,\"type\":3,\"blockTypeId\":196},{\"remainNormalStock\":70,\"totalStock\":70,\"remainWheelChairNumber\":null,\"remainMinderNumber\":null,\"type\":3,\"blockTypeId\":198}]}],\"performanceDateTime\":null,\"eventDateEn\":null,\"eventDateTc\":null,\"eventDateSc\":null},\"paging\":null,\"tracing\":null,\"success\":true}";
        String jsonStr2 = "{\"code\":200000,\"msg\":null,\"data\":{\"loginSuccess\":true,\"token\":\"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJoa1Nob3ciLCJpYXQiOjE2MzcyMTc1MzMsInN5c3RlbUNvZGUiOjIsInJlbmV3YWwiOmZhbHNlLCJsb2dpbkZsYWciOiIxMzYyMTYzNzIxNzUzMzMzOSIsInRlcm1pbmFsSWQiOjkxLCJ0ZXJtaW5hbENvZGUiOiJ0ZXJtaW5hbC10ZXN0IiwidXNlcklkIjoxMzZ9.eryBQbUvXX3BS4EZx749Cm-2lebq4ETMJWR7G0MOOUY\",\"loginFailedCode\":null,\"loginFailedMsg\":null,\"loginTime\":null,\"loginFailTimes\":null,\"printFlag\":true,\"tillPrintFlag\":true,\"userInfo\":{\"token\":\"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJoa1Nob3ciLCJpYXQiOjE2MzcyMTc1MzMsInN5c3RlbUNvZGUiOjIsInJlbmV3YWwiOmZhbHNlLCJsb2dpbkZsYWciOiIxMzYyMTYzNzIxNzUzMzMzOSIsInRlcm1pbmFsSWQiOjkxLCJ0ZXJtaW5hbENvZGUiOiJ0ZXJtaW5hbC10ZXN0IiwidXNlcklkIjoxMzZ9.eryBQbUvXX3BS4EZx749Cm-2lebq4ETMJWR7G0MOOUY\",\"ip\":\"172.19.145.50\",\"userBasicInfo\":{\"id\":136,\"code\":\"amprj000011\",\"name\":\"hetilong01\",\"email\":\"463673840@qq.com\",\"loginId\":\"TiLongHe\",\"loginFailedCount\":0,\"presenterId\":null,\"enquiryLevel\":null,\"roleId\":53,\"status\":1,\"phoneOne\":\"\",\"phoneTwo\":null,\"address\":null,\"merchantId\":1,\"outletGroupId\":1,\"forceChangePassword\":0,\"outletCode\":\"amprj00\",\"postTitle\":\"hello\",\"userType\":0,\"isLock\":false},\"userMerchantInfo\":null,\"userPresenterInfo\":null,\"userRoleInfo\":{\"id\":53,\"userGroupId\":86,\"code\":\"POS\",\"name\":\"pos\",\"description\":\"POS端角色，拥有pos端全部角色\",\"roleType\":2,\"roleDataAccess\":[1,2,3,4,5,6,9,10,11,13,14,15,16,17,18,19]},\"userFunctionInfoList\":[{\"functionCode\":\"POS_Lost_Ticket\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_User_Settings_SET\",\"read\":false,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Return_Ticke_Rebuy\",\"read\":false,\"add\":false,\"edit\":true,\"delete\":false},{\"functionCode\":\"POS_Till_Ticket_Summary\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Handling_Charge\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Summary_Report\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Return_Ticket\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Upgrade_Ticket\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Release_Reservation\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Reservation4Consignment\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Exchange_Ticket\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Test_Ticket\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Reservation4Wheelchair\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Printing_Ticket\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Telephone_Booking\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_US_Change_Password\",\"read\":false,\"add\":false,\"edit\":true,\"delete\":false},{\"functionCode\":\"POS_Consignment_Ticket\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Update_Ticket_Owner\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Refund_Ticket\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Reprint_Ticket\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_US_Lock_POS\",\"read\":false,\"add\":false,\"edit\":true,\"delete\":false},{\"functionCode\":\"POS_Claim_Ticket\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Wheelchair_Facility\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Ticket_Collection\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Transaction_Info_Enquiry\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Bulk_Ticket\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"functionCode\":\"POS_Normal_Ticket_Purchase\",\"read\":true,\"add\":false,\"edit\":false,\"delete\":false}]},\"isPrint\":false,\"lastUnusualLoginId\":null},\"paging\":null,\"tracing\":null,\"success\":true}";

        //getValue(jsonStr);
        JsonUtil jsonUtil = new JsonUtil();
        /*
        jsonUtil.getValueByKey(jsonStr1,"performanceList");
        System.out.println(jsonUtil.list.toString());

        System.out.println(jsonUtil.getValueByKeyFromJson(jsonStr1,"performanceId"));
        System.out.println(jsonUtil.updateJsonStr(jsonStr,"B","A"));

         */
        //System.out.println(jsonUtil.getValueByKeyFromJson(jsonStr2,"").get(1));

        System.out.println(jsonUtil.getValueByKeyReturnString(jsonStr1,"performanceList"));

    }

    /**
     * 打印所有的value
     * @param jsonStr
     */
    public static void getValue(String jsonStr){
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Set<String> keys = jsonObject.keySet();
        for(String key: keys){
            Object object = jsonObject.get(key);
            if (object instanceof JSONObject){
                getValue(object.toString());
            }else if (object instanceof JSONArray){
                for(int i=0;i<((JSONArray) object).size();i++){

                    if(((JSONArray) object).get(i) instanceof JSONObject){
                        String s = ((JSONArray) object).getString(i);
                        getValue(s);
                    }else {
                        continue;
                    }
                }
            }else {
                System.out.println(jsonObject.get(key));
            }
        }
    }

    /**
     * 定义全局变量List来存储key对应的value
     * @param jsonStr
     * @param k
     */

    public  void getValueByKey(String jsonStr,String k){
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Set<String> keys = jsonObject.keySet();
        for(String key: keys){
            Object object = jsonObject.get(key);
            if (object instanceof JSONObject){
                getValueByKey(object.toString(),k);
            }else if (object instanceof JSONArray){
                for(int i=0;i<((JSONArray) object).size();i++){

                    if(((JSONArray) object).get(i) instanceof JSONObject){
                        String s = ((JSONArray) object).getString(i);
                        getValueByKey(s,k);
                    }else {
                        continue;
                    }
                }
            }
            if(key.equals(k)){
                Object s = jsonObject.get(key);
                list.add(s.toString());
            }
        }
    }

    /**
     * 定义全局变量List来存储key对应的value
     * @param jsonStr
     * @param k
     */

    public  String getValueByKeyReturnString(String jsonStr,String k){
        String value = null;
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Set<String> keys = jsonObject.keySet();
        for(String key: keys){

            if(key.equals(k)){
                Object s = jsonObject.get(key);
                value = s.toString();
                break;
            }
            Object object = jsonObject.get(key);
            if (object instanceof JSONObject){
                value = getValueByKeyReturnString(object.toString(),k);
            }else if (object instanceof JSONArray){
                for(int i=0;i<((JSONArray) object).size();i++){

                    if(((JSONArray) object).get(i) instanceof JSONObject){
                        String s = ((JSONArray) object).getString(i);
                        value = getValueByKeyReturnString(s,k);
                    }else {
                        continue;
                    }
                }
            }

        }
        return value;
    }

    /**
     *
     *
     * @param jsonStr
     * @param k
     */

    public  void getValueByKey(String jsonStr,String k,ArrayList<String> arr){
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Set<String> keys = jsonObject.keySet();
        for(String key: keys){
            Object object = jsonObject.get(key);
            if (object instanceof JSONObject){
                getValueByKey(object.toString(),k,arr);
            }else if (object instanceof JSONArray){
                for(int i=0;i<((JSONArray) object).size();i++){

                    if(((JSONArray) object).get(i) instanceof JSONObject){
                        String s = ((JSONArray) object).getString(i);
                        getValueByKey(s,k,arr);
                    }else {
                        continue;
                    }
                }
            }
            if(key.equals(k)){
                arr.add(jsonObject.getString(key));
            }

        }
    }

    /**
     * 通过传参进行存储key对应的value
     * @param jsonStr
     * @param k
     * @return
     */
    public  List<String> getValueByKeyFromJson(String jsonStr, String k){
        ArrayList<String> arrayList = new ArrayList<>();
        getValueByKey(jsonStr,k,arrayList);
        return arrayList;
    }

    /**
     *
     *update value 并返回新的字符串。
     **/
    public String updateJsonStr(String jsonStr,Object value,String k){
        JSONObject json = null;
        json = JSONObject.parseObject(jsonStr);

        Set<String> keySet = json.keySet();

        for(String key: keySet){
            Object obj = json.get(key);
            if(obj instanceof JSONArray){
                JSONArray  arr = (JSONArray) obj;
                for(int i=0;i<arr.size();i++){
                    if(arr.get(i) instanceof JSONObject){
                        String child = updateJsonStr(arr.get(i).toString(),value,k);
                        arr.set(i,JSONObject.parse(child));
                    }
                }
                json.put(key,arr);
            }else if (obj instanceof JSONObject){
                JSONObject sub = (JSONObject) obj;
                String subStr = updateJsonStr(sub.toString(),value,k);
                json.put(key,JSONObject.parse(subStr));
            }
            if(key.equals(k)){
                json.put(k,value);
            }
        }

        return json.toJSONString();
    }
}
