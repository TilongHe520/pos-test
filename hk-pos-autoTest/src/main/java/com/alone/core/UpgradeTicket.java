package com.alone.core;

import com.alibaba.fastjson.JSONArray;
import com.alone.common.BaseCommon;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.cart.CartTicketInfo;
import com.alone.pojo.print.UploadPrintInfo;
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

}
