package com.alone.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: hetilong
 * @Date: 2021/11/20 19:16
 */
@Getter
@AllArgsConstructor
public enum PosTypeEnum {

    REG(1,"购买门票"),
    RETURN_TICKET(2,"退票"),
    REFUND_TICKET(3,"退款"),
    RESERVATION_WHEELCHAIR(4,"预留轮椅设施"),
    RESERVATION_CONSIGNMENT(5,"预留内销"),
    CLM(6,"领取预留"),
    TELEPHONE_TICKET(7,"电话购票"),
    BUK(8,"大量购票"),
    CONSIGNMENT_TICKET(9,"内销票"),
    RLS(10, "取消预订"),
    EXC(11, "换票"),
    UPG(12, "升级票种"),
    REN(13, "改名"),
    EXW(14, "换轮椅设施"),
    CLAIM_PAID_TICKET(15, "领取已购"),
    PRINT_TEST_TICKET(16, "打测试票"),
    REPRINT(17, "重印门票"),
    PRINT(18, "打印门票"),
    POUNDAGE(20, "补收手续费"),
    TRANSACTION_SEARCH(21, "交易查询"),
    LOGIN_START(22, "登录"),
    LOGIN_TILL(23, "登出");


    private int status;

    private String descCn;
}
