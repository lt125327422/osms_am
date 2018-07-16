package com.amazon.test;

import com.amazon.test.testVO.AmazonStockReportVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: liteng
 * @Date: 2018/7/9 13:42
 * @Description:
 */
public class testCase004 {


    public static void main(String[] args) throws IOException {
        AmazonStockReportMain.init();
        String s1 = "sku\tfnsku\tasin\tproduct-name\tcondition\tyour-price\tmfn-listing-exists\tmfn-fulfillable-quantity\tafn-listing-exists\tafn-warehouse-quantity\tafn-fulfillable-quantity\tafn-unsellable-quantity\tafn-reserved-quantity\tafn-total-quantity\tper-unit-volume\tafn-inbound-working-quantity\tafn-inbound-shipped-quantity\tafn-inbound-receiving-quantity\n" +
                "ABAG-D006-02-EU6-PAN\tX000UXX1YH\tB07CGF3PWH\tNBEADS 10pcs Jewellery Jewelry Silk Pouches Gift Bag Bags, Mixed Color, 5.9\"x 4.7\"\tNew\t10.39\tNo\t\tYes\t0\t0\t0\t0\t15\t192.06\t7\t8\t0\n" +
                "ABAG-N002-B-02-EU6-PAN\tX000V1NKLH\tB07CPG414Z\tNBEADS 50Pcs Vintage Cloth Drawstring Gift Bags Sacks for Weddings Party Favor, Old Lace, 6.1 x 4.9 inch\tNew\t19.29\tNo\t\tYes\t0\t0\t0\t0\t2\t159.94\t0\t2\t0\n" +
                "ABAG-N002-C-02-EU6-PAN\tX000UY3F4R\tB07CNTCPNR\tNBEADS 100Pcs Vintage Cloth Drawstring Gift Bags Sacks for Weddings Party Favor, Ivory, 4.7 x 4.1 inch\tNew\t31.59\tNo\t\tYes\t0\t0\t0\t0\t3\t192.06\t0\t3\t0\n";
        List<String> sss = new ArrayList<String>();
        sss.add(s1);
        sss.add(s1);
        sss.add(s1);

        //把多个报告写入到多个temp文件中
        List<String> backupFileOutput = AmazonStockReportMain.newFileOutput(sss);

        //读取temp文件
        List<AmazonStockReportVO> amazonStockReportVOS = AmazonStockReportMain.fileOutputOriginalVersion(backupFileOutput);

        //把vo写入result.txt文件中
        AmazonStockReportMain.fileOutput003(amazonStockReportVOS);
    }



}
