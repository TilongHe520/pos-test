package com.alone.pojo.event;

import lombok.Data;


/**
 * @author tilongHe
 */
@Data
public class PerformanceInfo {
    public int eventId;
    public String performanceId;
    public String performanceNameEn;
    public String performanceNameSc;
    public String PerformanceNameTc;
    public long saleEndDate;
}
