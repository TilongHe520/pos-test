package com.alone.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alone.common.BaseBuyTicket;
import com.alone.enums.PosTypeEnum;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.cart.ClaimAddCartInfo;
import com.alone.pojo.print.UploadPrintInfo;
import com.alone.pojo.reservation.ResTransactionInfoReq;
import com.alone.pojo.reservation.ReservationInfoReq;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.util.JsonUtil;
import com.alone.util.ResolveCurl;
import com.alone.util.UploadPrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Author: hetilong
 * @Date: 2021/12/25 23:00
 */
public class ReserveClaimAndRelease extends BaseBuyTicket {


    public ReserveClaimAndRelease(TerminalInfo terminalInfo, EnvironmentInfo environmentInfo, LoginInfo loginInfo, String cookies, int posType) {
        super(terminalInfo, environmentInfo, loginInfo, cookies, posType);
    }

    public String queryResTransaction(String claimId, int reserveType){
        String jsonStr = "";
        if (posType != PosTypeEnum.valueOf("CLM").getStatus()){
            ReservationInfoReq reservation = new ReservationInfoReq(claimId,reserveType);
            jsonStr = JSONObject.toJSONString(reservation);
        }else {
            ReservationInfoReq reservation = new ReservationInfoReq(claimId,posType,reserveType);
            jsonStr = JSONObject.toJSONString(reservation);
        }
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlReservation());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        String response = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        return response;
    }

    public void releaseReserve(String res){
        JsonUtil jsonUtil = new JsonUtil();
        if(!jsonUtil.getValueByKeyReturnString(res,"code").equals("600002")){
            String reserveTime = jsonUtil.getValueByKeyReturnString(res,"reserveTime");
            String children = jsonUtil.getValueByKeyReturnString(res,"children");
            JSONArray jsonArray = JSONArray.parseArray(children);
            List<ResTransactionInfoReq> list = new ArrayList<>();
            for(int i = 0;i<jsonArray.size();i++){
                ResTransactionInfoReq resInfo =  JSONObject.parseObject(jsonArray.getString(i),ResTransactionInfoReq.class);
                resInfo.setPerformanceDateTime(new JsonUtil().getValueByKeyReturnString(jsonArray.getString(i),"beginTime"));
                resInfo.setTicketPassword(new JsonUtil().getValueByKeyReturnString(jsonArray.getString(i),"fetchCode"));
                resInfo.setTicketPrice(Integer.valueOf(new JsonUtil().getValueByKeyReturnString(jsonArray.getString(i),"price")));
                resInfo.setReserveDateTime(reserveTime);
                list.add(resInfo);
            }
            JSONArray jsonArray1 = JSONArray.parseArray(JSON.toJSONString(list));

            ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlReleaseReserve());
            CurlParams cp = rs.getParams();
            Map<String,String> map = cp.getHeader();
            map.put("Cookie",cookies);
            map.put("x-terminal-code",terminalInfo.getTerminalId());
            map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

            String jsonStr = jsonUtil.updateJsonStr(cp.getData(),jsonArray1,"resTransactionRequestList");
            jsonStr = jsonUtil.updateJsonStr(jsonStr,terminalInfo.getTerminalId(),"terminalId");

            String response = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
            System.out.println(response);

        }
    }

    public ClaimAddCartInfo claimCheck(String res,int reserveType){
        ClaimAddCartInfo claimAddCartInfo = new ClaimAddCartInfo();
        JsonUtil jsonUtil = new JsonUtil();
        if(jsonUtil.getValueByKeyReturnString(res,"code").equals("200000")){
            List<String> ticketList = jsonUtil.getValueByKeyFromJson(res,"ticketId");
            JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(ticketList));
            ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlClaimCheck());
            CurlParams cp = rs.getParams();
            Map<String,String> map = cp.getHeader();
            map.put("Cookie",cookies);
            map.put("x-terminal-code",terminalInfo.getTerminalId());
            map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

            String jsonStr = jsonUtil.updateJsonStr(cp.getData(),jsonArray,"ticketIdList");
            jsonStr = jsonUtil.updateJsonStr(jsonStr,reserveType,"reserveType");

            String response = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
            System.out.println(response);
            List<String> childrens = jsonUtil.getValueByKeyFromJson(res,"children");

            String s = childrens.get(0);
            JSONArray jsonArray1 = JSONArray.parseArray(s);
            String s1 = jsonArray1.getString(0);
            claimAddCartInfo = JSONObject.parseObject(s1,ClaimAddCartInfo.class);

        }
        return claimAddCartInfo;
    }

    public String claimOrder(String claimId,int reserveType){
        String res = queryResTransaction(claimId,reserveType);
        ClaimAddCartInfo claimAddCartInfo = claimCheck(res,reserveType);
        addToCart(claimAddCartInfo);
        String queryCartRes = queryCart();
        String jsonStr = getTransactionReq(queryCartRes);

        String transactionId = creatTransaction(jsonStr);
        prepayResult(transactionId);
        String transactionNum = prepay(transactionId);

        String printRes = print(transactionNum);

        JsonUtil jsonUtil = new JsonUtil();

        List<String> ticketIdList = jsonUtil.getValueByKeyFromJson(printRes,"ticketId");
        String taskId = jsonUtil.getValueByKeyReturnString(printRes,"taskId");

        List<UploadPrintInfo> uploadPrintInfoList = new UploadPrintUtil().getUploadPrintList(taskId,ticketIdList);

        uploadPrintResult(uploadPrintInfoList);

        return transactionNum;
    }

    public String addToCart(ClaimAddCartInfo claimAddCartInfo) {
        JsonUtil jsonUtil = new JsonUtil();
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlAddToCart());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));
        List<ClaimAddCartInfo> cartInfos = new ArrayList<>();
        cartInfos.add(claimAddCartInfo);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(cartInfos));


        String requestData = cp.getData().replace("1352",String.valueOf(claimAddCartInfo.getEventId()));
        requestData = requestData.replace("4781",String.valueOf(claimAddCartInfo.getPerformanceId()));
        requestData = jsonUtil.updateJsonStr(requestData,jsonArray,"cartSkuInfoList");
        requestData = jsonUtil.updateJsonStr(requestData,posType,"posType");

        String response = given().headers(map).body(requestData).post(cp.getUrl()).asString();

        return response;
    }
}
