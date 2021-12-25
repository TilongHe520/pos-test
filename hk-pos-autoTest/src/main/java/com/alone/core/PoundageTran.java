package com.alone.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alone.common.BaseBuyTicket;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.print.UploadPrintInfo;
import com.alone.pojo.serviceCharge.PoundageTypeInfo;
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
 * @Date: 2021/12/25 20:03
 */
public class PoundageTran extends BaseBuyTicket {


    public PoundageTran(TerminalInfo terminalInfo, EnvironmentInfo environmentInfo, LoginInfo loginInfo, String cookies, int posType) {
        super(terminalInfo, environmentInfo, loginInfo, cookies, posType);
    }

    /**
     * 收取手续费
     * @return
     */
    public  List<PoundageTypeInfo> listPoundage(){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlListPoundage());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        String res = given().headers(map).body(cp.getData()).post(cp.getUrl()).asString();
        System.out.println(res);
        JsonUtil jsonUtil = new JsonUtil();
        String data = jsonUtil.getValueByKeyReturnString(res,"data");
        JSONArray jsonArray = JSONArray.parseArray(data);
        List<PoundageTypeInfo> poundageTypeInfoList = new ArrayList<>();
        for (int i=0;i<jsonArray.size();i++){
            PoundageTypeInfo poundageTypeInfo = JSONObject.parseObject(jsonArray.getString(i),PoundageTypeInfo.class);
            poundageTypeInfoList.add(poundageTypeInfo);
        }
        return poundageTypeInfoList;
    }

    /**
     * 收取手续费
     * @return
     */
    public  String creatPoundageTran(PoundageTypeInfo poundageTypeInfo){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlCreatePoundageTran());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        JsonUtil jsonUtil = new JsonUtil();
        String data = jsonUtil.updateJsonStr(cp.getData(),poundageTypeInfo.getType(),"poundageType");
        data = jsonUtil.updateJsonStr(data,poundageTypeInfo.getPricePerTicket(),"totalAmount");
        data = jsonUtil.updateJsonStr(data,terminalInfo.getTerminalId(),"terminalCode");
        String res = given().headers(map).body(data).post(cp.getUrl()).asString();

        return res;
    }

    /**
     * 打印手续费票版
     * @param transactionNum
     * @param cookies
     * @return
     */
    public  String printChargeTicket(String transactionNum,String cookies){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlPrintChargeTicket());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        JsonUtil jsonUtil = new JsonUtil();
        String data = jsonUtil.updateJsonStr(cp.getData(),terminalInfo.getTerminalId(),"deviceNum");
        data = data.replace("21121118241091701",transactionNum);
        String res = given().headers(map).body(data).post(cp.getUrl()).asString();

        return res;
    }

    public void poundageOrder(){
        JsonUtil jsonUtil = new JsonUtil();
        List<PoundageTypeInfo> poundageTypeInfoList = listPoundage();
        for (PoundageTypeInfo p:poundageTypeInfoList
        ) {
            if (!"Change".equals(p.getCode())){
                String ptRes =  creatPoundageTran(p);
                String transactionId = jsonUtil.getValueByKeyReturnString(ptRes,"transactionId");
                String transactionNum = jsonUtil.getValueByKeyReturnString(ptRes,"transactionNo");
                prepayResult(transactionId);
                prepay(transactionId);
                String printRes = printChargeTicket(transactionNum,cookies);
                List<String> ticketIdList = jsonUtil.getValueByKeyFromJson(printRes,"ticketId");
                String taskId = jsonUtil.getValueByKeyReturnString(printRes,"taskId");

                List<UploadPrintInfo> uploadPrintInfoList = new UploadPrintUtil().getUploadPrintList(taskId,ticketIdList);

                String uploadRes = uploadPrintResult(uploadPrintInfoList);
                System.out.println(uploadRes);
            }
        }
    }
}
