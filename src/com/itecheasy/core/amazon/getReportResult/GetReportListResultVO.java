package com.itecheasy.core.amazon.getReportResult;

import java.util.List;

/**
 * @Auther: liteng
 * @Date: 2018/7/3 14:57
 * @Description:
 */
public class GetReportListResultVO {

    private List<String> reportIdList;

    private String nextToken;

    private boolean hasNext;

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<String> getReportIdList() {
        return reportIdList;
    }

    public void setReportIdList(List<String> reportIdList) {
        this.reportIdList = reportIdList;
    }
}
