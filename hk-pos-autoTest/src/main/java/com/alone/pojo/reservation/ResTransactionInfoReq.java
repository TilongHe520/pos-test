package com.alone.pojo.reservation;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.testng.annotations.Test;

/**
 * @Author: hetilong
 * @Date: 2021/12/26 17:56
 */
@Data
public class ResTransactionInfoReq {
    public String customerName;
    public long ticketId;
    public String releaseBlockTypeCode;
    public String performanceDateTime;
    public String performanceNameEn;
    public String ticketPassword;
    public String reserveDateTime;
    public int releaseBlockTypeId;
    public String section;
    public String row;
    public String col;
    public String priceZoneCode;
    public String ticketTypeCode;
    public int ticketPrice;

    public ResTransactionInfoReq(){
        this.customerName = ",";
        this.releaseBlockTypeCode = "public";
        this.releaseBlockTypeId = 0;
    }
}
