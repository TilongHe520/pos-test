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
}
