package com.alone.core;

import com.alibaba.fastjson.JSONArray;
import com.alone.common.BaseBuyTicket;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.print.UploadPrintInfo;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.util.JsonUtil;
import com.alone.util.ResolveCurl;
import com.alone.util.UploadPrintUtil;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Author: hetilong
 * @Date: 2021/12/13 20:02
 */
public class ReprintTicket extends BaseBuyTicket {


    public ReprintTicket(TerminalInfo terminalInfo, EnvironmentInfo environmentInfo, LoginInfo loginInfo, String cookies, int posType) {
        super(terminalInfo, environmentInfo, loginInfo, cookies, posType);
    }

    public String reprintOrder(){
        String tranNumRes = getReprintTicketInfo();
        List<UploadPrintInfo> uploadPrintInfoList = reprint(tranNumRes);
        String reprintUploadRes = uploadPrintResult(uploadPrintInfoList);
        return reprintUploadRes;
    }

    public String getReprintTicketInfo(){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlReprintTicket());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));
        map.put("Cookie",cookies);

        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),terminalInfo.getTerminalId(),"terminalCode");
        String res = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        String tranNum = jsonUtil.getValueByKeyReturnString(res,"tranNum");
        System.out.println(tranNum);
        return tranNum;
    }

    public List<UploadPrintInfo> reprint(String transactionNum){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlPrint());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        JsonUtil jsonUtil = new JsonUtil();
        String data = cp.getData().replace("21111520050102801",transactionNum);
        data = jsonUtil.updateJsonStr(data,posType,"menuType");
        data = jsonUtil.updateJsonStr(data,terminalInfo.getTerminalId(),"deviceNum");
        data = jsonUtil.updateJsonStr(data,loginInfo.getLoginId(),"operatorName");
        String response = given().headers(map).body(data).post(cp.getUrl()).asString();
        System.out.println(response);
        List<String> ticketIdList = jsonUtil.getValueByKeyFromJson(response,"ticketId");
        String taskId = jsonUtil.getValueByKeyReturnString(response,"taskId");

        List<UploadPrintInfo> uploadPrintInfoList = new UploadPrintUtil().getUploadPrintList(taskId,ticketIdList);
        return uploadPrintInfoList;
    }

    @Override
    public String uploadPrintResult(List<UploadPrintInfo> uploadPrintInfoList){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlUploadPrintResult());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);

        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        JsonUtil jsonUtil = new JsonUtil();
        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(uploadPrintInfoList);
        String data = jsonUtil.updateJsonStr(cp.getData(),jsonArray,"printTicketResultList");
        data = jsonUtil.updateJsonStr(data,terminalInfo.getTerminalId(),"deviceNum");
        data = jsonUtil.updateJsonStr(data,posType,"menuType");
        data = jsonUtil.updateJsonStr(data,loginInfo.getUserId(),"operatorId");
        String res = given().headers(map).body(data).post(cp.getUrl()).asString();
        System.out.println(res);
        return res;
    }
}
