package com.itecheasy.core.amazon.vo;

import java.util.Date;

/**
 * @Auther: liteng
 * @Date: 2018/7/2 08:27
 * @Description:
 */
public class RequestReportVO {

    private String reportType;

    private Date startDate;

    //MarketplaceIdList

    private Date endDate;

    private String reportOptions;

    private Integer shopId;



    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReportOptions() {
        return reportOptions;
    }

    public void setReportOptions(String reportOptions) {
        this.reportOptions = reportOptions;
    }
}
