package com.alone.pojo.terminal;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * terminalInfo 终端信息
 * @Author: hetilong
 * @Date: 2021/12/18 21:02
 */
@Data
@AllArgsConstructor
public class TerminalInfo {
    public int id;
    public String terminalId;
    public String outletGroupCode;
    public String outletCode;
}
