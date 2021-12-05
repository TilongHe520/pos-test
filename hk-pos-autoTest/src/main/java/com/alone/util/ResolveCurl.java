package com.alone.util;



import com.alone.pojo.base.CurlParams;

import java.util.HashMap;

/**
 * @Author: hetilong
 * @Date: 2021/11/6 17:30
 * 解析curl
 */

public class ResolveCurl {
    public String curl;
    public ResolveCurl(String curl){
        this.curl = curl;
    }


    public CurlParams getParams(){
        String[] a = curl.split("\\n");
        CurlParams cp = new CurlParams();
        HashMap<String,String> h = new HashMap<>();
        for(int j = 0; j<a.length;j++){
            if(a[j].contains("curl")){
                String url = a[j].replace("curl"," ").replace('\'',' ').replace('\\',' ').trim();
                cp.setUrl(url);
            }else if(a[j].contains("-H")){
                String s = a[j].replace("-H"," ").replace('\'',' ').replace('\\',' ').trim();
                h.put(s.split(": ")[0],s.split(": ")[1].trim());
            }else if(a[j].contains("--data-raw")){
                String data = a[j].replace("--data-raw"," ").replace('\'',' ').replace('\\',' ').trim();
                cp.setData(data);
            }
        }
        cp.setHeader(h);
        return cp;
    }

}
