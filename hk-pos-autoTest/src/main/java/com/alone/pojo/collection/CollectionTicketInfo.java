package com.alone.pojo.collection;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: hetilong
 * @Date: 2021/12/20 16:56
 * 领取已购参数
 */

@Data
@AllArgsConstructor
public class CollectionTicketInfo {
    public String claimId;
    public String contactNum;
    public String deliveryFlag;
    public String email;
    public String fetchCode;
    public String payAccount;
    public String tranNum;
}
