package com.alone.core;

import com.alone.common.BaseCommon;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;

/**
 * 电话购票场景
 * @Author: hetilong
 * @Date: 2021/12/16 13:51
 */
public class TelephoneTicket extends BaseCommon {
    public TelephoneTicket(EnvironmentInfo environmentInfo, LoginInfo loginInfo, String cookies, int posType) {
        super(environmentInfo, loginInfo, cookies, posType);
    }

    @Override
    public void creatOrder(MajorCore mc){

    }
}
