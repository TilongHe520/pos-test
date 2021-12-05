package com.alone.pojo.seat;

import lombok.Data;

/**
 * @Author: hetilong
 * @Date: 2021/11/21 16:22
 */
@Data
public class SeatInfo {
    public String id;
    public int performanceId;
    public String uniqueId;
    public String uuid;
    public int pohId;
    public String pohUuid;
    public int priceZoneId;
    public int blockTypeId;
    public String row;
    public String col;
    public int rowSequence;
    public int seatSequence;
    public int seatTypeId;
    public boolean showSpecialInfo;
    public String specialInfoEn;
    public String specialInfoTc;
    public String specialInfoSc;
    public String section;
    public String gate;
    public int ticketTypeId;
    public boolean exchange;
    public int seatType;
    public String specialInfo;

}
