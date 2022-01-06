package com.alone.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.cart.CartSkuInfo;
import com.alone.pojo.reservation.ResTransactionInfoReq;
import com.alone.pojo.ticket.TicketInfo;
import com.alone.util.FileUtil;
import com.alone.util.JsonUtil;
import com.alone.util.LoginUtil;
import com.alone.util.ResolveCurl;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.HttpClientConfig;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: hetilong
 * @Date: 2021/12/4 12:15
 */
public class DemoMain {
    /**
     * 设置环境参数
     */
    public static void setRestAssuredConfig() {
        RestAssured.config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig().httpClientFactory(() -> {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            RequestConfig requestConfig = RequestConfig.custom()
                    //设置连接超时时间(单位毫秒)
                    .setConnectTimeout(60000)
                    //设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(60000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(60000).build();
            CloseableHttpClient client = httpClientBuilder.setDefaultRequestConfig(requestConfig).build();

            return client;
        }));

        RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));
//        RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultCharsetForContentType("UTF-8","application/json"));
        RestAssured.config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig());
        RestAssured.useRelaxedHTTPSValidation();

        RestAssured.urlEncodingEnabled = false;
    }

    public static void main(String[] args) throws IOException {

        String path = "/Users/maoyan/Desktop/POS/curl_pos.txt";
        FileUtil fileUtil = new FileUtil();
        String curl = fileUtil.getCurl(path);
        String res = new DemoMain().getResponse(curl);

        System.out.println(res);
        fileUtil.writeResponse(res,path);
    }

    public String getResponse(String curl){
        ResolveCurl rc = new ResolveCurl(curl);
        CurlParams cp = rc.getParams();

        Map<String,String> map = cp.getHeader();

        String res = null;

        if(cp.getData() == null){

            setRestAssuredConfig();
            map.remove("Content-Length");

            res = given().headers(map).post(cp.getUrl()).asString();

        }else{
            res = given().headers(cp.getHeader()).body(cp.getData()).post(cp.getUrl()).asString();

        }
        return res;
    }

    @Test
    public void demo() throws IOException {
        String path = "/Users/maoyan/work/curl.txt";
        EnvironmentInfo environmentInfo = new FileUtil().getCurlObject(path);
        String s = environmentInfo.getCurlCheckHolder();
        ResolveCurl rc = new ResolveCurl(s);
        CurlParams cp = rc.getParams();
        System.out.println(cp.getData());
    }

    @Test
    public void demo1() {
        String path="/Users/maoyan/work/pos-test/hk-pos-autoTest/src/main/resources/loginInfo.properties";
        LoginUtil loginUtil = new LoginUtil();
        Map<String,LoginInfo> loginInfoMap = loginUtil.getLoginInfo(path);
        System.out.println(loginInfoMap.get("TEST").toString());
    }

    @Test
    public void demo02(){
        String jsonStr = "{\"code\":200000,\"msg\":null,\"data\":[{\"tranNumber\":\"21120720354706001\",\"eventId\":1592,\"performanceId\":5118,\"performanceNameSc\":\"es-同步\",\"performanceNameTc\":\"es-同步\",\"performanceNameEn\":\"es-同步\",\"performanceTime\":1640797200000,\"payAmount\":1000,\"payChannelCode\":\"CASH\",\"payAccount\":null,\"year\":null,\"month\":null,\"sellChannel\":3,\"ticketId\":125632,\"ticketNumber\":\"21120720354706001001\",\"section\":\"\",\"row\":\"\",\"col\":\"\",\"priceZoneId\":1,\"priceZoneCode\":\"A\",\"priceZoneColor\":\"#FF363D\",\"priceZoneNameEn\":\"A\",\"priceZoneNameSc\":\"A\",\"priceZoneNameTc\":\"A\",\"ticketTypeId\":25,\"ticketTypeCode\":\"STAN\",\"ticketTypeNameEn\":\"Standard\",\"ticketTypeNameSc\":\"正价票\",\"ticketTypeNameTc\":\"正價票\",\"ticketPrice\":1000,\"ticketPayPrice\":1000,\"deliverMethodCode\":\"WALK_IN\",\"fetchStatus\":1,\"printStatus\":3,\"printFetchStatus\":1,\"seatType\":2,\"seatTypeNameEn\":\"normal_pos\",\"seatTypeNameTc\":\"普通座位_pos\",\"seatTypeNameSc\":\"普通座位_pos\",\"showSpecialInfo\":0,\"specialNameEn\":\"\",\"specialNameTc\":\"\",\"specialNameSc\":\"\"},{\"tranNumber\":\"21120720354706001\",\"eventId\":1592,\"performanceId\":5118,\"performanceNameSc\":\"es-同步\",\"performanceNameTc\":\"es-同步\",\"performanceNameEn\":\"es-同步\",\"performanceTime\":1640797200000,\"payAmount\":1000,\"payChannelCode\":\"CASH\",\"payAccount\":null,\"year\":null,\"month\":null,\"sellChannel\":3,\"ticketId\":125633,\"ticketNumber\":\"21120720354706001002\",\"section\":\"\",\"row\":\"\",\"col\":\"\",\"priceZoneId\":1,\"priceZoneCode\":\"A\",\"priceZoneColor\":\"#FF363D\",\"priceZoneNameEn\":\"A\",\"priceZoneNameSc\":\"A\",\"priceZoneNameTc\":\"A\",\"ticketTypeId\":25,\"ticketTypeCode\":\"STAN\",\"ticketTypeNameEn\":\"Standard\",\"ticketTypeNameSc\":\"正价票\",\"ticketTypeNameTc\":\"正價票\",\"ticketPrice\":1000,\"ticketPayPrice\":1000,\"deliverMethodCode\":\"WALK_IN\",\"fetchStatus\":1,\"printStatus\":3,\"printFetchStatus\":1,\"seatType\":2,\"seatTypeNameEn\":\"normal_pos\",\"seatTypeNameTc\":\"普通座位_pos\",\"seatTypeNameSc\":\"普通座位_pos\",\"showSpecialInfo\":0,\"specialNameEn\":\"\",\"specialNameTc\":\"\",\"specialNameSc\":\"\"}],\"paging\":null,\"tracing\":null,\"success\":true}";
        JsonUtil jsonUtil = new JsonUtil();
        String data = jsonUtil.getValueByKeyReturnString(jsonStr,"data");
        JSONArray jsonArray = JSONArray.parseArray(data);
        List<TicketInfo> ticketInfoList = new ArrayList<>();
        for (int i=0;i<jsonArray.size();i++){
            TicketInfo ticketInfo = JSONObject.parseObject(jsonArray.getString(i),TicketInfo.class);
            ticketInfo.setRefundType(0);
            ticketInfoList.add(ticketInfo);
        }
        System.out.println(ticketInfoList);
        Map<Integer,List<TicketInfo>> map = ticketInfoList.stream().collect(Collectors.groupingBy(TicketInfo::getPerformanceId));
        System.out.println(map.size());
    }

    @Test
    public void test03(){
        String curl = "curl 'http://hkpos.dev.maoyan.team/api/transaction/exchange/getTicketInfo?locale=zh_CN' \\\n" +
                "  -H 'Proxy-Connection: keep-alive' \\\n" +
                "  -H 'Pragma: no-cache' \\\n" +
                "  -H 'Cache-Control: no-cache' \\\n" +
                "  -H 'Accept: application/json' \\\n" +
                "  -H 'x-terminal-code: terminal-test' \\\n" +
                "  -H 'x-terminal-id: 91' \\\n" +
                "  -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36' \\\n" +
                "  -H 'Content-Type: application/json;charset=UTF-8' \\\n" +
                "  -H 'Origin: http://hkpos.dev.maoyan.team' \\\n" +
                "  -H 'Referer: http://hkpos.dev.maoyan.team/seller/upgrade_tickets' \\\n" +
                "  -H 'Accept-Language: zh-CN,zh;q=0.9' \\\n" +
                "  -H 'Cookie: pos-token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJoa1Nob3ciLCJpYXQiOjE2MzkyMDk3MjIsInN5c3RlbUNvZGUiOjIsInJlbmV3YWwiOmZhbHNlLCJsb2dpbkZsYWciOiIxMzYyMTYzOTIwOTcyMjU4OSIsInRlcm1pbmFsSWQiOjkxLCJ0ZXJtaW5hbENvZGUiOiJ0ZXJtaW5hbC10ZXN0IiwidXNlcklkIjoxMzZ9.X5GFlCoNVU0f57Zu2_Z-BKiGZyCSDJgLFKiOhNlUrlo; locale=zh-CN' \\\n" +
                "  --data-raw '{\"tranNumber\":\"21121210460795501\",\"queryType\":12}' \\\n" +
                "  --compressed \\\n" +
                "  --insecure";

        ResolveCurl rs = new ResolveCurl(curl);
        CurlParams cp = rs.getParams();
        String res = given().headers(cp.getHeader()).body(cp.getData()).post(cp.getUrl()).asString();
        System.out.println(res);

        JsonUtil jsonUtil = new JsonUtil();
        String responseList = jsonUtil.getValueByKeyReturnString(res,"posTicketTransactionResponseList");
        JSONArray jsonArray = JSONArray.parseArray(responseList);
        String ticketId = null;
        for (Object o:jsonArray) {
            if(jsonUtil.getValueByKeyReturnString(o.toString(),"ticketTypeCode").equals("STU")){
                ticketId = jsonUtil.getValueByKeyReturnString(o.toString(),"id");
                break;
            }
        }

        System.out.println(ticketId);
    }

    @Test
    public void test04(){
        String jsonStr = "{\"code\":200000,\"msg\":null,\"data\":{\"checkPass\":true,\"checkMsgCode\":null,\"checkMsg\":null,\"cartItemRequest\":{\"serialNum\":null,\"eventIdList\":[13001],\"performanceIdList\":[27707],\"cartSkuInfoList\":[{\"uniqueId\":\"27707.39.0.7.67.1\",\"seatId\":1451886,\"pohId\":137396,\"priceTableId\":0,\"needSeat\":true,\"section\":null,\"eventId\":13001,\"row\":\"D\",\"col\":\"14\",\"gate\":null,\"priceZoneId\":39,\"priceZoneCode\":\"C\",\"priceZoneNameEn\":null,\"priceZoneNameSc\":null,\"priceZoneNameTc\":null,\"color\":null,\"originalTicketTypeId\":1,\"originalTicketTypeNameEn\":null,\"originalTicketTypeNameTc\":null,\"originalTicketTypeNameSc\":null,\"originalTicketTypeCode\":\"STAN\",\"ticketTypeNature\":0,\"originalPrice\":1000,\"showTicketTypeId\":0,\"showTicketTypeCode\":null,\"showTicketTypeNameEn\":null,\"showTicketTypeNameTc\":null,\"showTicketTypeNameSc\":null,\"showPrice\":0,\"stackablePrice\":0,\"performanceId\":27707,\"gift\":false,\"discountCalcDesc\":null,\"ticketId\":27440,\"blockTypeId\":0,\"needRealName\":false,\"firstName\":null,\"lastName\":null,\"refundType\":0,\"rowSequence\":0,\"seatSequence\":0,\"seatType\":1,\"discountCalcDescList\":null,\"seatTypeId\":null,\"reserveTicket\":false,\"exchange\":false,\"oldTicketPrice\":1000,\"openSeatTypeDTO\":null,\"swapInTicket\":false,\"showSpecialInfo\":false,\"specialInfoEn\":null,\"specialInfoTc\":null,\"specialInfoSc\":null,\"seatRemark\":null,\"baseTicketType\":0,\"uuid\":null,\"pohName\":null,\"bestPriceDiscountCalcDesc\":null,\"bulkPurchase\":false,\"memberList\":null,\"promotionCodeList\":null,\"paymentMethod\":null,\"toTicketTypeCode\":null,\"pohUuid\":null,\"originalPriceZoneId\":null,\"a8TerminalId\":null}],\"memberList\":null,\"promotionCodeList\":null,\"posType\":11,\"paymentMethod\":null,\"cardNo\":null,\"deliveryMethodCode\":null,\"destination\":null,\"registerNum\":null,\"token\":null,\"salesChannel\":0,\"discountCouponList\":null,\"terminalCode\":null,\"bulkPurchase\":false,\"ticketNature\":1,\"editCart\":false}},\"paging\":null,\"tracing\":null,\"success\":true}";
        JsonUtil jsonUtil = new JsonUtil();
        JSONArray s = JSONArray.parseArray(jsonUtil.getValueByKeyFromJson(jsonStr,"cartSkuInfoList").get(0));
        System.out.println(s);
        CartSkuInfo cartSkuInfo = JSONObject.parseObject(s.getString(0),CartSkuInfo.class);
        List<CartSkuInfo> cartSkuInfos = new ArrayList<>();
        cartSkuInfos.add(cartSkuInfo);
        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(cartSkuInfos);
        System.out.println(jsonArray);
        String data= JSONObject.toJSON(cartSkuInfo).toString();
        Map map = JSON.parseObject(data,Map.class);
        String data1 = JSONObject.toJSONString(map);
        System.out.println(data1);
    }

    @Test
    public void test05(){
        long t = System.currentTimeMillis();
        long day = 24*60*60*1000;
        System.out.println(t+day);
    }

    @Test
    public void test06(){
        String s = "{\"ticketId\":127019,\"checkCode\":\"alone01\",\"eventId\":1055,\"performanceId\":5764,\"performanceStartTime\":null,\"eventCode\":\"alone-new\",\"beginTime\":1641564000000,\"beginTimeSc\":\"2022/01/07 星期五 下午 10:00\",\"beginTimeTc\":\"2022/01/07 星期五 下午 10:00\",\"beginTimeEn\":\"2022/01/07 Fri PM 10:00\",\"transactionType\":null,\"transactionTypeValue\":null,\"section\":\"Section_A\",\"seatId\":1347481,\"pohId\":4942,\"uniqueId\":\"5764.0.109.2.11.2\",\"pohName\":null,\"pohNameEn\":null,\"pohNameTc\":null,\"pohNameSc\":null,\"row\":\"H\",\"col\":\"1\",\"price\":1000,\"blockTypeId\":109,\"blockTypeCode\":\"WE\",\"lockStatus\":0,\"priceZoneId\":93,\"priceZoneNameEn\":\"pos_price_A\",\"priceZoneNameTc\":\"pos_price_A\",\"priceZoneNameSc\":\"pos_price_A\",\"priceZoneCode\":\"pos_price_A\",\"color\":null,\"ticketTypeId\":25,\"ticketTypeNameEn\":\"Standard\",\"ticketTypeNameTc\":\"正價票\",\"ticketTypeNameSc\":\"正价票\",\"ticketTypeCode\":\"STAN\",\"fetchCode\":\"alone01\",\"reserveDateEn\":\"2021/12/24 Fri PM 03:24\",\"reserveDateTc\":\"2021/12/24 星期五 下午 03:24\",\"reserveDateSc\":\"2021/12/24 星期五 下午 03:24\",\"lastName\":\"\",\"firstName\":\"\",\"performanceNameEn\":\"exchange wheelchair Facility-1\",\"performanceNameTc\":\"換領輪椅設施-1\",\"performanceNameSc\":\"换领轮椅设施-1\",\"ticketShowEn\":\"Standard/pos_price_A/$10.00\",\"ticketShowTc\":\"正價票/pos_price_A/$10.00\",\"ticketShowSc\":\"正价票/pos_price_A/$10.00\",\"deliveryMethodCode\":null,\"deliveryMethodNameEn\":null,\"deliveryMethodNameTc\":null,\"deliveryMethodNameSc\":null,\"blockTypeList\":[{\"id\":0,\"code\":\"public\",\"nameEn\":\"Public Seats\",\"nameTc\":\"公眾票\",\"nameSc\":\"公众票\",\"sortSeq\":\"0\",\"displayModel\":null},{\"id\":109,\"code\":\"WE\",\"nameEn\":\"Wheelchair Exchange\",\"nameTc\":\"輪椅鎖座\",\"nameSc\":\"轮椅锁座\",\"sortSeq\":\"00001\",\"displayModel\":3},{\"id\":196,\"code\":\"H1\",\"nameEn\":\"ALONE1\",\"nameTc\":\"ALONE1\",\"nameSc\":\"ALONE1\",\"sortSeq\":\"00002\",\"displayModel\":2},{\"id\":197,\"code\":\"H2\",\"nameEn\":\"ALONE2\",\"nameTc\":\"ALONE2\",\"nameSc\":\"ALONE2\",\"sortSeq\":\"00002\",\"displayModel\":1},{\"id\":198,\"code\":\"H3\",\"nameEn\":\"alone3\",\"nameTc\":\"alone3\",\"nameSc\":\"alone3\",\"sortSeq\":\"00003\",\"displayModel\":1}],\"baseTicketType\":0,\"seatType\":4,\"seatTypeId\":103,\"seatTypeNameEn\":\"Minder_pos\",\"seatTypeNameTc\":\"看護人_pos\",\"seatTypeNameSc\":\"看护人_pos\",\"showSpecialInfo\":1,\"specialNameEn\":\"pos_Minder\",\"specialNameTc\":\"pos_看護人\",\"specialNameSc\":\"pos_看护人\",\"openSeatTypeDTO\":null,\"holderFistName\":null,\"holderLastName\":null}";

        ResTransactionInfoReq res =  JSONObject.parseObject(s,ResTransactionInfoReq.class);
        res.setPerformanceDateTime(new JsonUtil().getValueByKeyReturnString(s,"beginTime"));
        res.setTicketPassword(new JsonUtil().getValueByKeyReturnString(s,"fetchCode"));
        res.setTicketPrice(Integer.valueOf(new JsonUtil().getValueByKeyReturnString(s,"price")));
        res.setReserveDateTime("1640330674000");
        System.out.println(res);
    }
}
