package com.alone.util;

import com.alone.pojo.print.UploadPrintInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: hetilong
 * @Date: 2021/12/6 17:41
 */
public class UploadPrintUtil {

    public List<UploadPrintInfo> getUploadPrintList(String taskId,List<String> ticketList){
        List<UploadPrintInfo> uploadPrintInfoList = new ArrayList<>();
        for (String t: ticketList) {
            UploadPrintInfo uploadPrintInfo = new UploadPrintInfo(taskId,Integer.valueOf(t),0,"打票失败");
            uploadPrintInfoList.add(uploadPrintInfo);
        }
        return uploadPrintInfoList;
    }
}
