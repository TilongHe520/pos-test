package com.alone.pojo.base;

import lombok.Data;

/**
 * @Author: hetilong
 * @Date: 2021/11/29 08:42
 */
@Data
public class EnvironmentInfo {

    public String curlLogin;
    public String curlLogout;
    public String curlDetail;
    public String curlConfirmStock;
    public String curlAddToCart;
    public String curlQueryCart;
    public String curlCreatTransaction;
    public String curlPrePayResult;
    public String curlPrePay;
    public String curlPrint;
    public String curlQueryTran;
    public String curlEventList;
    public String curlDeliveryMethod;
    /**
     * 上报打印结果
     */
    public String curlUploadPrintResult;
    /**
     * 退款查询列表接口
     */
    public String curlRefundList;

}