package com.alone.pojo.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: hetilong
 * @Date: 2021/12/25 22:56
 */
@Data
@AllArgsConstructor
public class ReservationInfoReq {

    public ReservationInfoReq(String claimId, int reserveType) {
        this.claimId = claimId;
        this.reserveType = reserveType;
    }

    public String claimId;
    public int menuType;
    public int reserveType;
}
