package com.alone.main;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
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
import org.junit.Test;

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

        System.out.println(res);
        fileUtil.writeResponse(res,path);
    }

    @Test
    public void demo() throws IOException {
        String path = "/Users/maoyan/work/curl.txt";
        EnvironmentInfo environmentInfo = new FileUtil().getCurlObject(path);
        String s = environmentInfo.getCurlListTicketEx();
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
}
