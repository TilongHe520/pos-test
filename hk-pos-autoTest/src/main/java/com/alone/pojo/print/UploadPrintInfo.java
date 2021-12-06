package com.alone.pojo.print;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上报打印结果字段信息
 * @Author: hetilong
 * @Date: 2021/12/5 17:00
 * {
 *  "printTicketResultList":[
 *      {"taskId":"1638694580342TiLongHe","ticketId":122823,"printResult":0,"failReason":"出票失败"},
 *      {"taskId":"1638694580342TiLongHe","ticketId":122824,"printResult":0,"failReason":"出票失败"}],
 *  "deviceNum":"terminal-test",
 *  "operatorId":136,
 *  "printType":1,
 *  "menuType":1
 * }
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class UploadPrintInfo {
    /**
     * 打票任务id
     */
    public String taskId;
    /**
     * 票ID
     */
    public int ticketId;
    /**
     * 打印结果
     */
    public int printResult;
    /**
     * 失败原因
     */
    public String failReason;
}
