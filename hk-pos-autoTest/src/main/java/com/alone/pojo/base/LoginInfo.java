package com.alone.pojo.base;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * 登录用户信息以及terminal信息
 * @author tilongHe
 */
@Data
@AllArgsConstructor
public class LoginInfo {

    public String loginId;
    public String password;
    public String terminalId;
    public String terminalCode;

}
