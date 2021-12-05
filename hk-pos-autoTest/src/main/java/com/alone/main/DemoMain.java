package com.alone.main;

import com.alone.pojo.base.CurlParams;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.util.FileUtil;
import com.alone.util.ResolveCurl;
import org.junit.Test;

import java.io.*;

/**
 * @Author: hetilong
 * @Date: 2021/12/4 12:15
 */
public class DemoMain {
    public static void main(String[] args) throws IOException {

        String path = "/Users/maoyan/work/curl.txt";
        String curl = new FileUtil().getCurl(path);
        ResolveCurl rc = new ResolveCurl(curl);
        CurlParams cp = rc.getParams();

        System.out.println(cp.getUrl());
        System.out.println(cp.getData());

        EnvironmentInfo environmentInfo = new FileUtil().getCurlObject(path);

        System.out.println(environmentInfo);
    }

    @Test
    public void demo() throws IOException {
        String path = "/Users/maoyan/work/curl.txt";
        EnvironmentInfo environmentInfo = new FileUtil().getCurlObject(path);

        String s = environmentInfo.getCurlAddToCart();
        ResolveCurl rc = new ResolveCurl(s);
        CurlParams cp = rc.getParams();
        System.out.println(cp.getUrl());
    }
}
