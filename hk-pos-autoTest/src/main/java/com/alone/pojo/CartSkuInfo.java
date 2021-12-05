package com.alone.pojo;

import lombok.Data;

/**
 * @Author: hetilong
 * @Date: 2021/11/20 11:47
 * 		"color": "",
 * 		"rowSequence": 0,
 * 		"seatSequence": 0,
 * 		"firstName": "",
 * 		"lastName": "",
 * 		"originalTicketTypeCode": "STAN",
 * 		"blockTypeId": 0,
 * 		"originalTicketTypeId": 25,
 * 		"uniqueId": "4781.1.0.1.97.1",
 * 		"pohId": 4021,
 * 		"performanceId": 4781,
 * 		"priceZoneId": 1,
 * 		"needSeat": false,
 * 		"seatId": null,
 * 		"needRealName": false
 */
@Data
public class CartSkuInfo {
    public boolean swapInTicket;
    public int seatType;
    public String color;
    public int rowSequence;
    public int seatSequence;
    public String firstName;
    public String lastName;
    public String originalTicketTypeCode;
    public int blockTypeId;
    public int originalTicketTypeId;
    public String uniqueId;
    public int pohId;
    public int performanceId;
    public int priceZoneId;
    public boolean needSeat;
    public String seatId;
    public boolean needRealName;
    public CartSkuInfo(){
        this.swapInTicket=false;
        this.seatType=2;
        this.color = "";
        this.rowSequence=0;
        this.seatSequence=0;
        this.firstName="";
        this.lastName="";
        this.originalTicketTypeCode="STAN";
        this.blockTypeId=0;
        this.originalTicketTypeId=25;
        this.uniqueId = "4781.1.0.1.97.1";
        this.pohId=0;
        this.performanceId=4781;
        this.priceZoneId=1;
        this.needSeat=false;
        this.seatId=null;
        this.needRealName=false;
    }
}
