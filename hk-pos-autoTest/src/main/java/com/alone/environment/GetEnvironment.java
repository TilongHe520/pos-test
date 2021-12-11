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
        if ("DEV".equals(env)){
            return environment;
        }else if ("TEST".equals(env)){
            environment.setCurlLogin(environment.getCurlLogin().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.cs.maoyan.team"));
            environment.setCurlLogout(environment.getCurlLogout().replace("hkpos.dev.maoyan.team","show-pos.pub.cs.maoyan.team"));
            environment.setCurlDetail(environment.getCurlDetail().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.cs.maoyan.team"));
            environment.setCurlConfirmStock(environment.getCurlConfirmStock().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.cs.maoyan.team"));
            environment.setCurlAddToCart(environment.getCurlAddToCart().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.cs.maoyan.team"));
            environment.setCurlQueryCart(environment.getCurlQueryCart().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.cs.maoyan.team"));
            environment.setCurlCreatTransaction(environment.getCurlCreatTransaction().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.cs.maoyan.team"));
            environment.setCurlPrePay(environment.getCurlPrePay().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.cs.maoyan.team"));
            environment.setCurlPrePayResult(environment.getCurlPrePayResult().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.cs.maoyan.team"));
            environment.setCurlPrint(environment.getCurlPrint().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.cs.maoyan.team"));
            environment.setCurlUploadPrintResult(environment.getCurlUploadPrintResult().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.cs.maoyan.team"));
            environment.setCurlRefundList(environment.getCurlRefundList().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.cs.maoyan.team"));
            environment.setCurlRefundSettle(environment.getCurlRefundSettle().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.cs.maoyan.team"));
            environment.setCurlQueryTran(environment.getCurlQueryTran().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.cs.maoyan.team"));
            environment.setCurlSummary(environment.getCurlSummary().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.cs.maoyan.team"));

        }else if("STAGE".equals(env)){
            environment.setCurlLogin(environment.getCurlLogin().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.sta.maoyan.team"));
            environment.setCurlLogout(environment.getCurlLogout().replace("hkpos.dev.maoyan.team","show-pos.pub.sta.maoyan.team"));
            environment.setCurlDetail(environment.getCurlDetail().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.sta.maoyan.team"));
            environment.setCurlConfirmStock(environment.getCurlConfirmStock().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.sta.maoyan.team"));
            environment.setCurlAddToCart(environment.getCurlAddToCart().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.sta.maoyan.team"));
            environment.setCurlQueryCart(environment.getCurlQueryCart().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.sta.maoyan.team"));
            environment.setCurlCreatTransaction(environment.getCurlCreatTransaction().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.sta.maoyan.team"));
            environment.setCurlPrePay(environment.getCurlPrePay().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.sta.maoyan.team"));
            environment.setCurlPrePayResult(environment.getCurlPrePayResult().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.sta.maoyan.team"));
            environment.setCurlPrint(environment.getCurlPrint().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.sta.maoyan.team"));
            environment.setCurlUploadPrintResult(environment.getCurlUploadPrintResult().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.sta.maoyan.team"));
            environment.setCurlRefundList(environment.getCurlRefundList().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.sta.maoyan.team"));
            environment.setCurlRefundSettle(environment.getCurlRefundSettle().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.sta.maoyan.team"));
            environment.setCurlQueryTran(environment.getCurlQueryTran().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.sta.maoyan.team"));
            environment.setCurlSummary(environment.getCurlSummary().replace("http://hkpos.dev.maoyan.team","http://show-pos.pub.sta.maoyan.team"));

        }
        return environment;
    }
}
