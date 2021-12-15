package com.alone.common;

import com.alibaba.fastjson.JSON;
import com.alone.core.MajorCore;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.cart.TicketRealNameInfo;
import com.alone.pojo.print.UploadPrintInfo;
import com.alone.pojo.transaction.CreatTranParams;
import com.alone.pojo.transaction.CustomerInfo;
import com.alone.util.JsonUtil;
import com.alone.util.RealNameUtil;
import com.alone.util.UploadPrintUtil;

import java.util.List;

/**
 * @Author: hetilong
 * @Date: 2021/12/12 10:49
 */
public class BaseCommon {
    public EnvironmentInfo environmentInfo;
    public LoginInfo loginInfo;
    public String cookies;
    public int posType;
    public BaseCommon(EnvironmentInfo environmentInfo, LoginInfo loginInfo,String cookies, int posType){
        this.environmentInfo = environmentInfo;
        this.loginInfo = loginInfo;
        this.cookies = cookies;
        this.posType = posType;
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
