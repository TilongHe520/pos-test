package com.alone.pojo.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: hetilong
 * @Date: 2021/12/12 14:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfo implements Serializable {

    public CustomerInfo(String contactNumber){
        this.contactNumber = contactNumber;
    }

    public CustomerInfo(String contactNumber,String payAccount,String payAccountHash,String expireMonth,String expireYear){
        this.contactNumber = contactNumber;
        this.payAccount = payAccount;
        this.payAccountHash = payAccountHash;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
    }

    /**
     * 联系人邮件地址
     */
    private String email;

    /**
     * fistName
     */
    private String firstName;

    /**
     * lastName
     */
    private String lastName;

    /**
     * 联系电话
     */
    private String contactNumber;

    /**
     * 邮政编码
     */
    private String postalCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 目的地
     */
    private String destination;

    /**
     * 所属区域
     */
    private String region;

    /**
     * 国家code
     */
    private String countryCode;

    /**
     * 信用卡类别 1:实体卡 2:虚拟卡
     */
    private int creditCardType;

    /**
     * 城市
     */
    private String cityName;

    /**
     * 地址1
     */
    private String firstAddress;

    /**
     * 地址2
     */
    private String secondAddress;

    /**
     * 地址3
     */

    private String thirdAddress;

    /**
     * 支付账号
     */
    private String payAccount;

    /**
     * 机构信息,目前支票支付时用于记录银行信息
     */
    private String organization;

    /**
     * 客户端类型
     */
    private int clientType;

    /**
     * 客户端版本号
     */
    private String clientVersion;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 支付账号hash值
     */
    private String payAccountHash;

    /**
     * 过期月份
     */
    private String expireMonth;

    /**
     * 过期年份
     */
    private String expireYear;
}
