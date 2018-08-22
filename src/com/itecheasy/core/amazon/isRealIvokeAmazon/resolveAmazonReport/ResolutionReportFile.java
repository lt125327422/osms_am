package com.itecheasy.core.amazon.isRealIvokeAmazon.resolveAmazonReport;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liteng
 * @Date: 2018/8/10 11:33
 * @Description:从亚马逊同步库存报告，不同的报告类型不同的实现
 */
public interface ResolutionReportFile {

     public abstract Map<String,Integer> getReportIndex(String filePath) throws IOException;

     public abstract String fileToJson(List<String> filePath, Integer shopId, Map<String, Integer> indexMap) throws IOException ;



}
