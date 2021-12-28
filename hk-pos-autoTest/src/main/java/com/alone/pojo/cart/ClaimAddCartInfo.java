package com.alone.pojo.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: hetilong
 * @Date: 2021/12/27 19:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimAddCartInfo {
    public int eventId;
    public int originalTicketTypeId;
    public long performanceId;
    public int priceZoneId;
    public long seatId;
    public long ticketId;
    public int ticketTypeId;
    public String uniqueId;
}
