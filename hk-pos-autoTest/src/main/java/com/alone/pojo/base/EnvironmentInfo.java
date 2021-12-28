package com.alone.pojo.base;

import lombok.Data;

/**
 * @Author: hetilong
 * @Date: 2021/11/29 08:42
 */
@Data
public class EnvironmentInfo {
    /**
     * 查询terminal信息
     */
    public String curlTerminalQuery;

    /**
     * 登录
     */
    public String curlLogin;

    /**
     * 登出
     */
    public String curlLogout;

    /**
     * 详情页
     */
    public String curlDetail;

    /**
     * 详情页选择票的数量
     */
    public String curlConfirmStock;

    /**
     * 选座页加入购物车
     */
    public String curlAddToCart;

    /**
     * 查询购物车
     */
    public String curlQueryCart;

    /**
     * 常见交易
     */
    public String curlCreatTransaction;

    /**
     * 预支付结果接口
     */
    public String curlPrePayResult;

    /**
     * 预支付接口
     */
    public String curlPrePay;

    /**
     * 打票接口
     */
    public String curlPrint;

    /**
     * 查询交易接口
     */
    public String curlQueryTran;

    /**
     * 项目列表接口
     */
    public String curlEventList;

    /**
     * 送付方式接口
     */
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

    /**
     * 升级票校验
     */
    public String curlCheckExchange;

    /**
     * 门票重印
     */
    public String curlReprintTicket;

    /**
     * 更改持票人
     */
    public String curlChangeHolder;

    /**
     * 更改持票人校验
     */
    public String curlCheckHolder;

    /**
     * 查询支付方式接口
     */
    public String curlPayChannel;

    /**
     * 领取已购
     */
    public String curlCollectionTicket;

    /**
     * 预留相关
     */
    public String curlReservation;

    /**
     * 取消预留
     */
    public String curlReleaseReserve;

    /**
     * 领取预留
     */
    public String curlClaimCheck;
}
