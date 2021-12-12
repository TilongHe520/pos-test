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
    /**
     * 退款接口
     */
    public String curlRefundSettle;
    /**
     * 交易总数预览
     */
    public String curlSummary;
    /**
     * 收取手续费
     */
    public String curlListPoundage;
    /**
     * 创建手续费交易
     */
    public String curlCreatePoundageTran;
    /**
     *  打印手续费票版
     */
    public String curlPrintChargeTicket;

    /**
     * 升级与换票
     */
    public String curlListTicketEx;
}
