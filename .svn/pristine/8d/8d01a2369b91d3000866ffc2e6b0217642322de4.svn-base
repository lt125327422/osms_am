package com.itecheasy.core.amazon.isRealIvokeAmazon;

import com.amazon.client.AmazonReportClient;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.itecheasy.core.amazon.AmazonConfigInfo;
import com.itecheasy.core.amazon.getReportResult.GetReportListResultVO;
import com.itecheasy.core.amazon.getReportResult.GetReportRequestListResultVO;
import com.itecheasy.core.amazon.getReportResult.RequestReportResultVO;
import com.itecheasy.core.amazon.getReportvo.GetReportListVO;
import com.itecheasy.core.amazon.getReportvo.GetReportRequestListVO;
import com.itecheasy.core.amazon.getReportvo.RequestReportVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: liteng
 * @Date: 2018/7/14 16:34
 * @Description:
 */
public class RealGetStockReportFromAmazonImpl implements IsRealGetStockReportFromAmazon {

    @Override
    public List<String> getReportAllResult(RequestReportVO step1VO, AmazonConfigInfo api) throws MarketplaceWebServiceException, InterruptedException, IOException {
        //step first   返回一个ReportRequestId
        RequestReportResultVO step1Result = AmazonReportClient.requestReport(step1VO,api);

        List<String> ids = new ArrayList<String>();
        ids.add(step1Result.getReportRequestId());  //一个枚举对应一个id

        //step 2
        GetReportRequestListVO step2VO = new GetReportRequestListVO();
        step2VO.setIds(ids);
        GetReportRequestListResultVO reportRequest = AmazonReportClient.getReportRequestList(step2VO,api);

        //收集step2.0
        List<GetReportRequestListResultVO> allStep2Result = new ArrayList<GetReportRequestListResultVO>();
        allStep2Result.add(reportRequest);

        //step 2.1   考虑到返回nextToken的情况
        boolean hasNext1 = reportRequest.isHasNext();
        String token1 =reportRequest.getNextToken();
        while (hasNext1){
            GetReportRequestListResultVO step2_1Result = AmazonReportClient.getReportRequestListByNextToken(token1,api);
            token1 = step2_1Result.getNextToken();
            hasNext1 = step2_1Result.isHasNext();
            allStep2Result.add(step2_1Result); //收集step3next
        }

        List<String> step3IdList = new ArrayList<String>();
        List<String> genIdList = new ArrayList<String>();
        for (GetReportRequestListResultVO step2Result : allStep2Result) {
            if (step2Result!=null && step2Result.getReportRequestIdList().size()>0) {
                step3IdList.addAll(step2Result.getReportRequestIdList());
            }else if (step2Result!=null && step2Result.getGeneratedReportIdList().size()>0){
                genIdList.addAll(step2Result.getGeneratedReportIdList());
            }
        }

        //step 3   调用这个api是因为有些订单没有GeneratedReportId
        //集合所有第二部的参数
        List<String> step3Param = new ArrayList<String>(step3IdList);
        GetReportListVO vo = new GetReportListVO();  //封装给step3调用
        vo.setReportRequestIdList(step3Param);
        GetReportListResultVO step3Result = AmazonReportClient.getReportList(vo,api);//调用amazon step3

        //收集step3
        List<String> allstep3_result = new ArrayList<String>(step3Result.getReportIdList());

        //step 3.1   考虑到返回nextToken的情况
        boolean hasNext = step3Result.isHasNext();
        String token =step3Result.getNextToken();
        while (hasNext){
            GetReportListResultVO step3_1Result = AmazonReportClient.getReportListByNextToken(token,api);
            token = step3_1Result.getNextToken();
            hasNext = step3_1Result.isHasNext();
            //收集step3next
            allstep3_result.addAll(step3_1Result.getReportIdList());
        }

        allstep3_result.addAll(genIdList);

        //生成报告
        //step 2 和2.1的GeneratedReportId当为参数，或者step 3中生成的ReportId作为参数   最后要多次调用report
        //step 4   如果有GeneratedReportId就可以直接生成报告了
        List<String> lastReportPathList = new ArrayList<String>();

        for (String lastParam : allstep3_result) {
            lastReportPathList.add(AmazonReportClient.getReport(lastParam,api));
            //md5校验，如果一直失败就发邮件
        }

        return lastReportPathList;  //txt缓存文件的路径
    }
}
