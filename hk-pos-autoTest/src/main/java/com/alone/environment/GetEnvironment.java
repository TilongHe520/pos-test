package com.alone.environment;

import com.alone.pojo.base.EnvironmentInfo;
import com.alone.util.FileUtil;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Field;


/**
 * @Author: hetilong
 * @Date: 2021/11/29 08:41
 */
public class GetEnvironment {

    private final static String devTarget = "hkpos.dev.maoyan.team";
    private final static String testTarget = "show-pos.pub.cs.maoyan.team";
    private final static String stageTarget = "hk-pos.pub.sta.maoyan.team";
    private final static String path = "/Users/maoyan/work/curl.txt";

    public EnvironmentInfo getEnvironmentCurl(String env) throws IOException {
        FileUtil fileUtil = new FileUtil();
        EnvironmentInfo environment = fileUtil.getCurlObject(path);
        if ("DEV".equals(env)){
            return environment;
        }else if ("TEST".equals(env)){
            return getEnvironmentInfo(environment,testTarget);
        }else if("STAGE".equals(env)){
            return getEnvironmentInfo(environment,stageTarget);
        }
        return environment;
    }
    /**
     * 通过反射修改属性值
     */
    public EnvironmentInfo getEnvironmentInfo(EnvironmentInfo environmentInfo,String replaceTarget) {
        try{
            Class clazz = environmentInfo.getClass();
            Field[] fs = clazz.getDeclaredFields();
            for (Field field : fs) {
                field.setAccessible(true);
                field.set(environmentInfo,String.valueOf(field.get(environmentInfo)).replace(devTarget,replaceTarget));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return environmentInfo;
    }
    /**
    public EnvironmentInfo getEnvironmentCurl(String env) throws IOException {
        FileUtil fileUtil = new FileUtil();
        String path = "/Users/maoyan/work/curl.txt";
        EnvironmentInfo environment = fileUtil.getCurlObject(path);

        if ("DEV".equals(env)){
            return environment;
        }else if ("TEST".equals(env)){
            environment.setCurlTerminalQuery(environment.getCurlTerminalQuery().replace(devTarget,testTarget));
            environment.setCurlLogin(environment.getCurlLogin().replace(devTarget,testTarget));
            environment.setCurlLogout(environment.getCurlLogout().replace(devTarget,testTarget));
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
            environment.setCurlChangeHolder(environment.getCurlChangeHolder().replace(devTarget,testTarget));
            environment.setCurlCheckHolder(environment.getCurlCheckHolder().replace(devTarget,testTarget));
            environment.setCurlCollectionTicket(environment.getCurlCollectionTicket().replace(devTarget,testTarget));
            environment.setCurlReservation(environment.getCurlReservation().replace(devTarget, testTarget));
            environment.setCurlReleaseReserve(environment.getCurlReleaseReserve().replace(devTarget, testTarget));
            environment.setCurlClaimCheck(environment.getCurlClaimCheck().replace(devTarget, testTarget));
        }else if("STAGE".equals(env)){
            environment.setCurlTerminalQuery(environment.getCurlTerminalQuery().replace(devTarget,stageTarget));
            environment.setCurlLogin(environment.getCurlLogin().replace(devTarget,stageTarget));
            environment.setCurlLogout(environment.getCurlLogout().replace(devTarget,testTarget));
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
            environment.setCurlListPoundage(environment.getCurlListPoundage().replace(devTarget,stageTarget));
            environment.setCurlCreatePoundageTran(environment.getCurlCreatePoundageTran().replace(devTarget,stageTarget));
            environment.setCurlPrintChargeTicket(environment.getCurlPrintChargeTicket().replace(devTarget,stageTarget));
            environment.setCurlListTicketEx(environment.getCurlListTicketEx().replace(devTarget,stageTarget));
            environment.setCurlCheckExchange(environment.getCurlCheckExchange().replace(devTarget,stageTarget));
            environment.setCurlReprintTicket(environment.getCurlReprintTicket().replace(devTarget,stageTarget));
            environment.setCurlChangeHolder(environment.getCurlChangeHolder().replace(devTarget,stageTarget));
            environment.setCurlCheckHolder(environment.getCurlCheckHolder().replace(devTarget,stageTarget));
            environment.setCurlCollectionTicket(environment.getCurlCollectionTicket().replace(devTarget,stageTarget));
            environment.setCurlReservation(environment.getCurlReservation().replace(devTarget, stageTarget));
            environment.setCurlReleaseReserve(environment.getCurlReleaseReserve().replace(devTarget, stageTarget));
            environment.setCurlClaimCheck(environment.getCurlClaimCheck().replace(devTarget, stageTarget));
        }
        return environment;
    }
     */
}
