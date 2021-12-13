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
}
