package com.alone.pojo.confirm;

import com.alone.pojo.confirm.ConfirmStockInfo;
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
