package com.alone.pojo.cart;

import lombok.Data;

/**
 * @Author: hetilong
 * @Date: 2021/11/20 19:47

 */
@Data
public class CartTicketInfo {
    /**
     * 支付金额
     */
    public int totalPayPrice;
    /**
     *  总的支付金额
     */
    public int totalTicketPrice;
    /**
     * 购票手续费
     */
    public int totalChargeFee;
    /**
     * 票数+手续费的数量
     */
    public int ticketCount;
    /**
     * 票的数量
     */
    public int totalTicketCount;

    /**
     * 售票渠道
     */

    public int menuType;
}
