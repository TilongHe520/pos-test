package com.alone.pojo.serviceCharge;

import lombok.Data;

/**
 * @Author: hetilong
 * @Date: 2021/12/11 17:49
 * 手续费类型
 */
@Data
public class PoundageTypeInfo {
    public int type;
    public String code;
    public int pricePerTicket;
}
