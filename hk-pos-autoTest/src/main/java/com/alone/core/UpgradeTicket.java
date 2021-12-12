package com.alone.core;

import com.alone.common.BaseCommon;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.util.JsonUtil;
import com.alone.util.ResolveCurl;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Author: hetilong
 * @Date: 2021/12/12 10:49
 */
public class UpgradeTicket extends BaseCommon{
    public String cookies;
    public UpgradeTicket(String cookies,EnvironmentInfo environmentInfo,LoginInfo loginInfo){
        super(environmentInfo,loginInfo);
        this.cookies = cookies;
    }

    /**
     * 获取升级票的信息
     * @param transactionNum
     * @param posType
     * @return
     */
    public String getTicketInfo(String transactionNum,int posType){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlListTicketEx());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("x-terminal-code",loginInfo.getTerminalCode());
        map.put("x-terminal-id",loginInfo.getTerminalId());
        map.put("Cookie",cookies);

        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),transactionNum,"tranNumber");
        jsonStr = jsonUtil.updateJsonStr(jsonStr,posType,"password");
        String res = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        System.out.println(res);
        return res;
    }
}
