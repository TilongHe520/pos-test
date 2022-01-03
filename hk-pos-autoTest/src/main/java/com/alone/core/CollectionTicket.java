package com.alone.core;

import com.alibaba.fastjson.JSONObject;
import com.alone.common.BaseBuyTicket;
import com.alone.enums.PosTypeEnum;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.collection.CollectionTicketInfo;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.util.ResolveCurl;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Author: hetilong
 * @Date: 2021/12/20 16:59
 * 领取已购相关
 */
public class CollectionTicket extends BaseBuyTicket {

    public CollectionTicket(LoginInfo loginInfo, TerminalInfo terminalInfo, EnvironmentInfo environmentInfo, String cookies) {
        super(terminalInfo,environmentInfo,loginInfo,cookies, PosTypeEnum.valueOf("CLAIM_PAID_TICKET").getStatus());

    }

    public void getListCollectionTicket(CollectionTicketInfo collectionTicketInfo){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlCollectionTicket());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        String data = JSONObject.toJSONString(collectionTicketInfo);

        String res = given().headers(map).body(data).post(cp.getUrl()).asString();
        System.out.println(res);
    }
}
