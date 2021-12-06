package com.alone.pojo.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 登录用户信息以及terminal信息
 * @author tilongHe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {
    public String userId;
    public String loginId;
    public String password;
    public String terminalId;
    public String terminalCode;

}
