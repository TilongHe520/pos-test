package com.alone.core;

import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.util.JsonUtil;
import com.alone.util.ResolveCurl;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Author: hetilong
 * @Date: 2021/12/25 19:51
 */
public class Transaction {
    public String cookies;
    public TerminalInfo terminalInfo;
    public EnvironmentInfo environmentInfo;
    public Transaction(String cookies,TerminalInfo terminalInfo,EnvironmentInfo environmentInfo){
        this.cookies = cookies;
        this.terminalInfo =terminalInfo;
        this.environmentInfo = environmentInfo;
    }
    /**
     * 查询交易接口
     * @param tranNum
     * @return
     */
    public  String queryTran(String tranNum){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlQueryTran());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),tranNum,"tranNum");
        String res = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        return jsonUtil.getValueByKeyFromJson(res,"data").toString();
    }

    public  String summary(){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlSummary());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),terminalInfo.getId(),"terminalId");
        String res = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        return res;
    }
}
