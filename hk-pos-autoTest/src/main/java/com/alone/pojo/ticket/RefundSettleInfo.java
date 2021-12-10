package com.alone.pojo.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: hetilong
 * @Date: 2021/12/9 13:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundSettleInfo {
    public Integer performanceId;
    public int blockTypeId;
    public boolean releaseToPublic;
    public List<Integer> ticketIdList;
}
