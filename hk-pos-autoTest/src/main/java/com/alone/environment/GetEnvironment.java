package com.alone.environment;

import com.alone.pojo.base.EnvironmentInfo;
import com.alone.util.FileUtil;

import java.io.IOException;


/**
 * @Author: hetilong
 * @Date: 2021/11/29 08:41
 */
public class GetEnvironment {
    public EnvironmentInfo getEnvironmentCurl(String env) throws IOException {
        FileUtil fileUtil = new FileUtil();
        String path = "/Users/maoyan/work/curl.txt";
        EnvironmentInfo environment = fileUtil.getCurlObject(path);
        String devTarget = "http://hkpos.dev.maoyan.team";
        String testTarget = "http://show-pos.pub.cs.maoyan.team";
        String stageTarget = "http://show-pos.pub.sta.maoyan.team";
        if ("DEV".equals(env)){
            return environment;
        }else if ("TEST".equals(env)){
            environment.setCurlLogin(environment.getCurlLogin().replace(devTarget,testTarget));
            environment.setCurlLogout(environment.getCurlLogout().replace("hkpos.dev.maoyan.team","show-pos.pub.cs.maoyan.team"));
            environment.setCurlDetail(environment.getCurlDetail().replace(devTarget,testTarget));
            environment.setCurlConfirmStock(environment.getCurlConfirmStock().replace(devTarget,testTarget));
            environment.setCurlAddToCart(environment.getCurlAddToCart().replace(devTarget,testTarget));
            environment.setCurlQueryCart(environment.getCurlQueryCart().replace(devTarget,testTarget));
            environment.setCurlCreatTransaction(environment.getCurlCreatTransaction().replace(devTarget,testTarget));
            environment.setCurlPrePay(environment.getCurlPrePay().replace(devTarget,testTarget));
            environment.setCurlPrePayResult(environment.getCurlPrePayResult().replace(devTarget,testTarget));
            environment.setCurlPrint(environment.getCurlPrint().replace(devTarget,testTarget));
            environment.setCurlUploadPrintResult(environment.getCurlUploadPrintResult().replace(devTarget,testTarget));
            environment.setCurlRefundList(environment.getCurlRefundList().replace(devTarget,testTarget));
            environment.setCurlRefundSettle(environment.getCurlRefundSettle().replace(devTarget,testTarget));
            environment.setCurlQueryTran(environment.getCurlQueryTran().replace(devTarget,testTarget));
            environment.setCurlSummary(environment.getCurlSummary().replace(devTarget,testTarget));
            environment.setCurlListPoundage(environment.getCurlListPoundage().replace(devTarget,testTarget));
            environment.setCurlCreatePoundageTran(environment.getCurlCreatePoundageTran().replace(devTarget,testTarget));
            environment.setCurlPrintChargeTicket(environment.getCurlPrintChargeTicket().replace(devTarget,testTarget));
            environment.setCurlListTicketEx(environment.getCurlListTicketEx().replace(devTarget,testTarget));
            environment.setCurlCheckExchange(environment.getCurlCheckExchange().replace(devTarget,testTarget));
            environment.setCurlReprintTicket(environment.getCurlReprintTicket().replace(devTarget,testTarget));

        }else if("STAGE".equals(env)){
            environment.setCurlLogin(environment.getCurlLogin().replace(devTarget,stageTarget));
            environment.setCurlLogout(environment.getCurlLogout().replace("hkpos.dev.maoyan.team","show-pos.pub.sta.maoyan.team"));
            environment.setCurlDetail(environment.getCurlDetail().replace(devTarget,stageTarget));
            environment.setCurlConfirmStock(environment.getCurlConfirmStock().replace(devTarget,stageTarget));
            environment.setCurlAddToCart(environment.getCurlAddToCart().replace(devTarget,stageTarget));
            environment.setCurlQueryCart(environment.getCurlQueryCart().replace(devTarget,stageTarget));
            environment.setCurlCreatTransaction(environment.getCurlCreatTransaction().replace(devTarget,stageTarget));
            environment.setCurlPrePay(environment.getCurlPrePay().replace(devTarget,stageTarget));
            environment.setCurlPrePayResult(environment.getCurlPrePayResult().replace(devTarget,stageTarget));
            environment.setCurlPrint(environment.getCurlPrint().replace(devTarget,stageTarget));
            environment.setCurlUploadPrintResult(environment.getCurlUploadPrintResult().replace(devTarget,stageTarget));
            environment.setCurlRefundList(environment.getCurlRefundList().replace(devTarget,stageTarget));
            environment.setCurlRefundSettle(environment.getCurlRefundSettle().replace(devTarget,stageTarget));
            environment.setCurlQueryTran(environment.getCurlQueryTran().replace(devTarget,stageTarget));
            environment.setCurlSummary(environment.getCurlSummary().replace(devTarget,stageTarget));
            environment.setCurlCreatePoundageTran(environment.getCurlCreatePoundageTran().replace(devTarget,stageTarget));
            environment.setCurlPrintChargeTicket(environment.getCurlPrintChargeTicket().replace(devTarget,stageTarget));
            environment.setCurlListTicketEx(environment.getCurlListTicketEx().replace(devTarget,stageTarget));
            environment.setCurlReprintTicket(environment.getCurlReprintTicket().replace(devTarget,stageTarget));

        }
        return environment;
    }
}
