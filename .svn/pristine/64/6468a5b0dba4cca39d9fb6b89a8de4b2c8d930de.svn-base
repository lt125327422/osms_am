package com.itecheasy.core.amazon.isRealIvokeAmazon;

import com.amazon.client.AmazonReportClient;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.itecheasy.common.util.DeployProperties;
import com.itecheasy.core.amazon.AmazonConfigInfo;
import com.itecheasy.core.amazon.getReportResult.GetReportListResultVO;
import com.itecheasy.core.amazon.getReportResult.GetReportRequestListResultVO;
import com.itecheasy.core.amazon.getReportResult.RequestReportResultVO;
import com.itecheasy.core.amazon.getReportvo.GetReportListVO;
import com.itecheasy.core.amazon.getReportvo.GetReportRequestListVO;
import com.itecheasy.core.amazon.getReportvo.RequestReportVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Auther: liteng
 * @Date: 2018/7/14 16:34
 * @Description:
 */
public class MockIsRealGetStockReportFromAmazonImpl implements IsRealGetStockReportFromAmazon {

    public static String mockTxt ;

    public MockIsRealGetStockReportFromAmazonImpl(){
         this.mockTxt = DeployProperties.getInstance().getProperty("mock.inputStream.file");

    }

    @Override
    public List<String> getReportAllResult(RequestReportVO step1VO, AmazonConfigInfo api) throws MarketplaceWebServiceException, InterruptedException, IOException {

        return Collections.singletonList(mockTxt);
    }
}
