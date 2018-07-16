package com.amazon.test;

import com.amazon.test.testVO.AmazonStockReportVO;

import java.io.IOException;
import java.util.*;

/**
 * @Auther: liteng
 * @Date: 2018/7/9 08:51
 * @Description:
 */
public class testCase003 {


    public static void main(String[] args) throws IOException {
        AmazonStockReportMain amazonStockReportMain = new AmazonStockReportMain();
        AmazonStockReportMain.init();

        List<AmazonStockReportVO> amazonStockReportVO = new ArrayList<AmazonStockReportVO>();
        for (int i = 0; i < 3; i++) {
            AmazonStockReportVO vo = new AmazonStockReportVO();
            vo.setSku("sss");
            vo.setSyncFirst(new Date());
            vo.setId(0014);
            vo.setSyncLast(new Date());
            vo.setAfnFulfillableQuantity(111);
            vo.setAfnInboundReceivingQuantity(222);
            vo.setAfnInboundWorkingQuantity(333);
            amazonStockReportVO.add(vo);

        }
        AmazonStockReportMain.fileOutput(amazonStockReportVO);

        System.out.println("slow start two ------------------------------------");
        String report = "sku:sss\tshopIdnull\tsyncFirst2018-07-09 09:04:23\tsyncLast2018-07-09 09:04:23\tAfnReservedQuantitynull\tAfnInboundWorkingQuantity333\tAfnInboundShippedQuantitynull\tAfnInboundReceivingQuantity222\tAfnFulfillableQuantity111\tAfnWarehouseQuantitynull\tAfnUnsellableQuantitynull\tAfnTotalQuantitynull\t\n" +
                "sku:sss\tshopIdnull\tsyncFirst2018-07-09 09:04:23\tsyncLast2018-07-09 09:04:23\tAfnReservedQuantitynull\tAfnInboundWorkingQuantity333\tAfnInboundShippedQuantitynull\tAfnInboundReceivingQuantity222\tAfnFulfillableQuantity111\tAfnWarehouseQuantitynull\tAfnUnsellableQuantitynull\tAfnTotalQuantitynull\t\n" +
                "sku:sss\tshopIdnull\tsyncFirst2018-07-09 09:04:23\tsyncLast2018-07-09 09:04:23\tAfnReservedQuantitynull\tAfnInboundWorkingQuantity333\tAfnInboundShippedQuantitynull\tAfnInboundReceivingQuantity222\tAfnFulfillableQuantity111\tAfnWarehouseQuantitynull\tAfnUnsellableQuantitynull\tAfnTotalQuantitynull\t\n";


        String report3 = "sku\tfnsku\tasin\tproduct-name\tcondition\tyour-price\tmfn-listing-exists\tmfn-fulfillable-quantity\tafn-listing-exists\tafn-warehouse-quantity\tafn-fulfillable-quantity\tafn-unsellable-quantity\tafn-reserved-quantity\tafn-total-quantity\tper-unit-volume\tafn-inbound-working-quantity\tafn-inbound-shipped-quantity\tafn-inbound-receiving-quantity\n" +
                "ABAG-D006-02-EU6-PAN\tX000UXX1YH\tB07CGF3PWH\tNBEADS 10pcs Jewellery Jewelry Silk Pouches Gift Bag Bags, Mixed Color, 5.9\"x 4.7\"\tNew\t10.39\tNo\t\tYes\t0\t0\t0\t0\t15\t192.06\t7\t8\t0\n" +
                "ABAG-N002-B-02-EU6-PAN\tX000V1NKLH\tB07CPG414Z\tNBEADS 50Pcs Vintage Cloth Drawstring Gift Bags Sacks for Weddings Party Favor, Old Lace, 6.1 x 4.9 inch\tNew\t19.29\tNo\t\tYes\t0\t0\t0\t0\t2\t159.94\t0\t2\t0\n" +
                "ABAG-N002-C-02-EU6-PAN\tX000UY3F4R\tB07CNTCPNR\tNBEADS 100Pcs Vintage Cloth Drawstring Gift Bags Sacks for Weddings Party Favor, Ivory, 4.7 x 4.1 inch\tNew\t31.59\tNo\t\tYes\t0\t0\t0\t0\t3\t192.06\t0\t3\t0\n";
        AmazonStockReportMain.resolvingReport(report);
        System.out.println("end two ------------------------------------");

        AmazonStockReportMain.newFileOutput(Arrays.asList(report3));
    }
}
