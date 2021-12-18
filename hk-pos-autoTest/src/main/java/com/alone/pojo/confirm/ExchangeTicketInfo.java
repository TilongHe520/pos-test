package com.alone.pojo.confirm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: hetilong
 * @Date: 2021/12/17 00:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeTicketInfo {
    public int eventId;
    public int performanceId;
    public int priceZoneId;
    public int ticketId;
    public int ticketTypeId;
}
