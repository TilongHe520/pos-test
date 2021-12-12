package com.alone.common;

import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;

/**
 * @Author: hetilong
 * @Date: 2021/12/12 10:49
 */
public class BaseCommon {
    public EnvironmentInfo environmentInfo;
    public LoginInfo loginInfo;
    public BaseCommon(EnvironmentInfo environmentInfo, LoginInfo loginInfo){
        this.environmentInfo = environmentInfo;
        this.loginInfo = loginInfo;
    }
}
