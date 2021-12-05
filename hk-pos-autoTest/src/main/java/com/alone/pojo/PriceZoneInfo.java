package com.alone.pojo;

import lombok.Data;

import java.util.List;

/**
 * @Author: hetilong
 * @Date: 2021/11/30 16:11
 */
@Data
public class PriceZoneInfo {
    public int priceZoneId;
    public String code;
    public int realNameSwitch;
    public List<TicketTypeInfo> ticketTypeList;
}
