package com.alone.pojo.event;

import lombok.Data;

/**
 * @Author: hetilong
 * @Date: 2021/12/19 20:25
 * 每个blockType对应的库存
 */
@Data
public class StockInfo {
    public int blockTypeId;
    public int remainNormalStock;
}
