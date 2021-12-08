package com.alone.pojo.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: hetilong
 * @Date: 2021/12/8 11:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundAddInfo {
    public List<String> eventId;
    public List<String> performanceId;
    public List<TicketInfo> ticketInfoList;
}
