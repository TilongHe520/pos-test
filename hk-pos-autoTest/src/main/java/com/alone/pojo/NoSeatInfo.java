package com.alone.pojo;

import lombok.Data;

/**
 * @Author: hetilong
 * @Date: 2021/11/20 15:55
 *
 * "performanceId": 4781,
 * 			"uniqueId": "4781.1.0.1.97.1",
 * 			"priceZoneId": 1,
 * 			"pohId": 4021,
 * 			"pohUuid": "uuid7",
 * 			"pohName": "POH1 POH1",
 * 			"seatTypeId": 101,
 * 			"showSpecialInfo": false,
 * 			"specialInfoEn": "",
 * 			"specialInfoTc": "",
 * 			"specialInfoSc": "",
 * 			"ticketTypeId": 25,
 * 			"blockTypeId": 0,
 * 			"exchange": false,
 * 			"seatType": 2
 */
@Data
public class NoSeatInfo {
    public int performanceId;
    public String uniqueId;
    public int priceZoneId;
    public int pohId;
    public String pohUuid;
    public String pohName;
    public int seatTypeId;
    public boolean showSpecialInfo;
    public String specialInfoEn;
    public String specialInfoTc;
    public String specialInfoSc;
    public int ticketTypeId;
    public int blockTypeId;
    public boolean exchange;
    public int seatType;
}
