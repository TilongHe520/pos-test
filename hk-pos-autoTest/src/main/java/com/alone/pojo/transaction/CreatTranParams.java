package com.alone.pojo.transaction;

import com.alone.pojo.cart.TicketRealNameInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: hetilong
 * @Date: 2021/12/12 14:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatTranParams {
    public CreatTranParams(int giftTicketCount, int releaseBlockType, int deliveryFee, int menuType, int totalPayPrice, int totalTicketPrice, int totalChargeFee, int ticketCount, int totalTicketCount, String outletGroupCode, String outletCode, String terminalCode, String payChannelCode, boolean exemptChargeFee, int printType, List<TicketRealNameInfo> realNameInfoList, CustomerInfo customerInfo) {
        this.giftTicketCount = giftTicketCount;
        this.releaseBlockType = releaseBlockType;
        this.deliveryFee = deliveryFee;
        this.menuType = menuType;
        this.totalPayPrice = totalPayPrice;
        this.totalTicketPrice = totalTicketPrice;
        this.totalChargeFee = totalChargeFee;
        this.ticketCount = ticketCount;
        this.totalTicketCount = totalTicketCount;
        this.outletGroupCode = outletGroupCode;
        this.outletCode = outletCode;
        this.terminalCode = terminalCode;
        this.payChannelCode = payChannelCode;
        this.exemptChargeFee = exemptChargeFee;
        this.printType = printType;
        this.realNameInfoList = realNameInfoList;
        this.customerInfo = customerInfo;
    }

    public int giftTicketCount;
    public int releaseBlockType;
    public int deliveryFee;
    public int menuType;
    public int totalPayPrice;
    public int totalTicketPrice;
    public int totalChargeFee;
    public int ticketCount;
    public int totalTicketCount;
    public String outletGroupCode;
    public String outletCode;
    public String terminalCode;
    public String payChannelCode;
    public boolean exemptChargeFee;
    public int printType;
    public List<TicketRealNameInfo> realNameInfoList;
    public CustomerInfo customerInfo;
    public String deliveryMethodCode;
}
