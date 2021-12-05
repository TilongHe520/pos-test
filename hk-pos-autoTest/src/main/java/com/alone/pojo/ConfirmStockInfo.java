package com.alone.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: hetilong
 * @Date: 2021/11/21 16:18
 * "confirmStockInfoList": [{
 * 		"priceZoneId": 94,
 * 		"blockTypeId": 0,
 * 		"stockType": 1,
 * 		"ticketTypeId": 25,
 * 		"ticketTypeNature": 4,
 * 		"stockNum": 1,
 * 		"seatTypeNature": 1
 *        }],
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmStockInfo {
    public int priceZoneId;
    public int blockTypeId;
    public int stockType;
    public int ticketTypeId;
    public int ticketTypeNature;
    public int stockNum;
    public int seatTypeNature;
}
