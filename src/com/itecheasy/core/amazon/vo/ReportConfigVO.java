package com.itecheasy.core.amazon.vo;

import java.util.Date;

/**
 * @author taozihao
 * @date 2018年9月5日 下午2:17:45
 * @description	报告信息配置
 */
public class ReportConfigVO {
	private String reportType;
	private Date startDate;
	private Date endDate;
	private String reportOptions;
	private int maxWaitingSeconds;

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

	public int getMaxWaitingSeconds() {
		return maxWaitingSeconds;
	}

	public void setMaxWaitingSeconds(int maxWaitingSeconds) {
		this.maxWaitingSeconds = maxWaitingSeconds;
	}
}
