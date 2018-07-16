package com.amazon.test;

import com.amazon.test.testVO.AmazonStockReportVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Auther: liteng
 * @Date: 2018/7/9 13:42
 * @Description:
 */
public class testCase005 {


    public static void main(String[] args) throws IOException {

        AmazonStockReportMain.init();

        String path = "C:\\Users\\Administrator\\Desktop\\AMAZON_REPORT.txt";

        //读取temp文件
        List<AmazonStockReportVO> amazonStockReportVOS = AmazonStockReportMain.fileOutputOriginalVersion(Collections.singletonList(path));

        //把vo写入result.txt文件中
        AmazonStockReportMain.fileOutput003(amazonStockReportVOS);
    }


    
}
