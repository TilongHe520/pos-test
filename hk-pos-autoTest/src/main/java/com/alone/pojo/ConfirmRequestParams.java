package com.alone.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tilongHe
 */
@Data
@AllArgsConstructor
public class ConfirmRequestParams {

    public String eventId;
    public int performanceId;
    public ConfirmStockInfo confirmStockInfo;
    public int menuType;

}
