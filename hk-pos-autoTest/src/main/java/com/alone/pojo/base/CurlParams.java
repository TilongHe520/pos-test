package com.alone.pojo.base;

import lombok.Data;

import java.util.Map;

/**
 * @Author: hetilong
 * @Date: 2021/11/7 10:41
 */
@Data
public class CurlParams {
    public String url;
    public Map<String,String> header;
    private String data;
}
