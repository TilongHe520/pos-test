package com.alone.pojo.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: hetilong
 * @Date: 2021/12/7 20:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketInfo {
    public Integer eventId;
    public Integer performanceId;
    public Integer ticketId;
    public Integer refundType;
}
