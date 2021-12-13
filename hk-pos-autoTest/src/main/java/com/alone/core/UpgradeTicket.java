package com.alone.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alone.common.BaseCommon;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.cart.CartTicketInfo;
import com.alone.pojo.cart.TicketRealNameInfo;
import com.alone.pojo.print.UploadPrintInfo;
import com.alone.pojo.transaction.CreatTranParams;
import com.alone.pojo.transaction.CustomerInfo;
import com.alone.util.JsonUtil;
import com.alone.util.RealNameUtil;
import com.alone.util.ResolveCurl;
import com.alone.util.UploadPrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Author: hetilong
 * @Date: 2021/12/12 10:49
 */
public class UpgradeTicket extends BaseCommon{
    public String cookies;
    public int posType;
    public UpgradeTicket(String cookies,EnvironmentInfo environmentInfo,LoginInfo loginInfo,int posType){
        super(environmentInfo,loginInfo);
        this.cookies = cookies;
        this.posType = posType;
    }

    /**
     * 获取升级票的信息
     * @param transactionNum
     * @return
     */
    public List<String> getTicketInfo(String transactionNum){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlListTicketEx());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());
        map.put("Cookie",cookies);

        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),transactionNum,"tranNumber");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,posType,"posType");
        String res = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        String responseList = jsonUtil.getValueByKeyReturnString(res,"posTicketTransactionResponseList");
        JSONArray jsonArray = JSONArray.parseArray(responseList);
        List<String> ticketId = new ArrayList<>();
        for (Object o:jsonArray) {
            if(jsonUtil.getValueByKeyReturnString(o.toString(),"ticketTypeCode").equals("STU")){
                String id = jsonUtil.getValueByKeyReturnString(o.toString(),"id");
                ticketId.add(id);
                break;
            }
        }
        return ticketId;
    }

    /**
     * 升级票校验
     * @param ids
     * @return
     */
    public String checkExchange(List<String> ids){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlCheckExchange());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());
        map.put("Cookie",cookies);

        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),ids,"ids");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,posType,"posType");
        String res = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();

        return res;
    }

    public void creatOrder(MajorCore mc){
        JsonUtil jsonUtil = new JsonUtil();
        String queryCartRes = mc.queryCart(posType, cookies);

        List<TicketRealNameInfo> t = new RealNameUtil().getTicketRealList(queryCartRes);

        CreatTranParams creatTranParams = new CreatTranParams(
                0,
                0,
                0,
                posType,
                Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"totalPrice")),
                0,
                Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"serviceFee")),
                Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"ticketCount")),
                Integer.valueOf(jsonUtil.getValueByKeyReturnString(queryCartRes,"ticketCount")),
                "Admin_outlet",
                "MY",
                loginInfo.getTerminalCode(),
                "CASH",
                true,
                2,
                t,
                new CustomerInfo("12345678908")
                );

        String jsonStr = JSON.toJSONString(creatTranParams);
        String transactionId = mc.creatTransaction(cookies,jsonStr);
        mc.prepayResult(transactionId, cookies);
        String transactionNum = mc.prepay(transactionId, cookies);

        String printRes = mc.print(transactionNum, cookies);

        List<String> ticketIdList = jsonUtil.getValueByKeyFromJson(printRes,"ticketId");
        String taskId = jsonUtil.getValueByKeyReturnString(printRes,"taskId");

        List<UploadPrintInfo> uploadPrintInfoList = new UploadPrintUtil().getUploadPrintList(taskId,ticketIdList);

        String uploadRes = mc.uploadPrintResult(cookies,uploadPrintInfoList,String.valueOf(posType));
        System.out.println(uploadRes);
    }

}
