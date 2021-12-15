package com.alone.core;

import com.alibaba.fastjson.JSONArray;
import com.alone.common.BaseCommon;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.util.JsonUtil;
import com.alone.util.ResolveCurl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * 更改持票人
 * @Author: hetilong
 * @Date: 2021/12/15 13:57
 */
public class ChangeHolder extends BaseCommon {
    public String cookies;
    public int posType;
    public ChangeHolder(EnvironmentInfo environmentInfo, LoginInfo loginInfo, String cookies, int posType) {
        super(environmentInfo, loginInfo);
        this.cookies = cookies;
        this.posType = posType;
    }

    /**
     * 获取更改持票人信息
     * @param transactionNum
     * @return
     */
    public List<String> getChangeHolderInfo(String transactionNum){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlChangeHolder());
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
            if(jsonUtil.getValueByKeyReturnString(o.toString(),"changeHolderFlag").equals("0")){
                String id = jsonUtil.getValueByKeyReturnString(o.toString(),"id");
                System.out.println(id);
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
    public String checkHolder(List<String> ids){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlCheckHolder());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());
        map.put("Cookie",cookies);

        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),ids,"ticketIds");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,posType,"posType");
        String res = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();

        return res;
    }


}
