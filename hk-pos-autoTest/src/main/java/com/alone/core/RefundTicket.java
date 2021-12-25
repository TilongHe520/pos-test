package com.alone.core;

import com.alone.common.BaseBuyTicket;
import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.pojo.ticket.RefundAddInfo;
import com.alone.pojo.ticket.RefundSettleInfo;
import com.alone.util.JsonUtil;
import com.alone.util.RefundUtil;
import com.alone.util.ResolveCurl;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Author: hetilong
 * @Date: 2021/12/25 12:11
 */
public class RefundTicket extends BaseBuyTicket {

    public RefundTicket(TerminalInfo terminalInfo, EnvironmentInfo environmentInfo, LoginInfo loginInfo, String cookies, int posType) {
        super(terminalInfo, environmentInfo, loginInfo, cookies, posType);
    }

    /**
     * 退款列表接口
     * @param tranNum
     * @return
     */
    public  String refundList(String tranNum){
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlRefundList());
        CurlParams cp = rs.getParams();
        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        JsonUtil jsonUtil = new JsonUtil();
        String jsonStr = jsonUtil.updateJsonStr(cp.getData(),tranNum,"tranNumber");
        String res = given().headers(map).body(jsonStr).post(cp.getUrl()).asString();
        return res;
    }

    public  String refundAddToCart(RefundAddInfo refundAddInfo){
        JsonUtil jsonUtil = new JsonUtil();
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlAddToCart());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        String requestData = jsonUtil.updateJsonStr(cp.getData(),refundAddInfo.getEventId(),"eventIdList");
        requestData = jsonUtil.updateJsonStr(requestData,refundAddInfo.getPerformanceId(),"performanceIdList");
        requestData = jsonUtil.updateJsonStr(requestData,refundAddInfo.getTicketInfoList(),"cartSkuInfoList");
        requestData = jsonUtil.updateJsonStr(requestData,posType,"posType");
        String response = given().headers(map).body(requestData).post(cp.getUrl()).asString();

        return response;
    }

    /**
     * 退款结算接口
     * @param refundSettleInfoList
     * @return
     */
    public  String refundSettle(List<RefundSettleInfo> refundSettleInfoList){
        JsonUtil jsonUtil = new JsonUtil();
        ResolveCurl rs = new ResolveCurl(environmentInfo.getCurlRefundSettle());
        CurlParams cp = rs.getParams();

        Map<String,String> map = cp.getHeader();
        map.put("Cookie",cookies);
        map.put("x-terminal-code",terminalInfo.getTerminalId());
        map.put("x-terminal-id",String.valueOf(terminalInfo.getId()));

        String requestData = jsonUtil.updateJsonStr(cp.getData(),refundSettleInfoList,"performanceReleaseRequests");
        requestData = jsonUtil.updateJsonStr(requestData,terminalInfo.getTerminalId(),"terminalCode");
        String response = given().headers(map).body(requestData).post(cp.getUrl()).asString();
        String transactionNum = jsonUtil.getValueByKeyReturnString(response,"transactionNo");
        return transactionNum;
    }

    public void refundOrder(List<String> trans){
        for (String tranNumber:trans){
            String refundRes = refundList(tranNumber);
            RefundUtil refundUtil = new RefundUtil();
            RefundAddInfo refundAddInfo = refundUtil.getRefundAddParams(refundRes);
            refundAddToCart(refundAddInfo);

            List<RefundSettleInfo> refundSettleInfoList = refundUtil.getRefundSettleParams(refundAddInfo);
            System.out.println(refundSettle(refundSettleInfoList));
        }

    }
}
