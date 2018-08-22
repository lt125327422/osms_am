package com.itecheasy.core.amazon.isRealIvokeAmazon;

import com.amazon.client.AmazonReportClient;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.itecheasy.common.util.CollectionUtils;
import com.itecheasy.common.util.DateUtils;
import com.itecheasy.common.util.DeployProperties;
import com.itecheasy.core.amazon.ALLReportUltimateVO;
import com.itecheasy.core.amazon.AmazonConfigInfo;
import com.itecheasy.core.amazon.isRealIvokeAmazon.resolveAmazonReport.ResolutionReportFile;
import com.itecheasy.core.amazon.vo.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Auther: liteng
 * @Date: 2018/7/14 16:34
 * @Description:
 */
public class RealGetStockReportFromAmazonImpl implements IsRealGetStockReportFromAmazon {
    private static final Logger LOGGER = Logger.getLogger(RealGetStockReportFromAmazonImpl.class);
    private Map<String, ResolutionReportFile> resolutionReportFileMap = new HashMap<String, ResolutionReportFile>();
    private ResolutionReportFile resolutionReportFile;

    //读取配置文件
    private static final String CACHEDIR = DeployProperties.getInstance().getProperty("amazon.temp.filePath");


    public void setResolutionReportFileMap(Map<String, ResolutionReportFile> resolutionReportFileMap) {
        this.resolutionReportFileMap = resolutionReportFileMap;
    }

    /**
     * 初始化方法
     */
    public void initResolutionReportFile() {
    }

    /**
     * 切换bean,多线程下风险很大，最好改为工厂
     *
     * @param str 报告的枚举类型来动态切换bean
     */
    private void getResolutionReportFileBean(String str) {
        if (GetReportType.获取亚马逊库龄报告.enumType.equals(str)) {
            LOGGER.error("bean cast to resolutionInventoryAgedItem");
            this.resolutionReportFile = resolutionReportFileMap.get("resolutionInventoryAgedItem");
        } else if (GetReportType.获取亚马逊商品库存报告.enumType.equals(str)) {
            LOGGER.error("bean cast to amazonStockItemReport");
            this.resolutionReportFile = resolutionReportFileMap.get("amazonStockItemReport");
        } else {
            LOGGER.error("ResolutionReportFileBean inject false please check ");
        }
    }





    /**
     * 第一版的旧版，用来兼容老版本报告
     *
     * @param step1VO
     * @param api
     * @return
     * @throws MarketplaceWebServiceException
     * @throws InterruptedException
     * @throws IOException
     */
    @Override
    public List<String> getReportAllResult(RequestReportVO step1VO, AmazonConfigInfo api) throws MarketplaceWebServiceException, InterruptedException, IOException {
        //step first   返回一个ReportRequestId
        RequestReportResultVO step1Result = AmazonReportClient.requestReport(step1VO, api);
        List<String> ids = new ArrayList<String>();
        ids.add(step1Result.getReportRequestId());  //一个枚举对应一个id

        //step 2
        GetReportRequestListVO step2VO = new GetReportRequestListVO();
        step2VO.setIds(ids);
        GetReportRequestListResultVO reportRequest = AmazonReportClient.getReportRequestList(step2VO, api, 5);

        //收集step2.0
        List<GetReportRequestListResultVO> allStep2Result = new ArrayList<GetReportRequestListResultVO>();
        allStep2Result.add(reportRequest);

        //step 2.1   考虑到返回nextToken的情况
        boolean hasNext1 = reportRequest.isHasNext();
        String token1 = reportRequest.getNextToken();
        while (hasNext1) {
            GetReportRequestListResultVO step2_1Result = AmazonReportClient.getReportRequestListByNextToken(token1, api);
            token1 = step2_1Result.getNextToken();
            hasNext1 = step2_1Result.isHasNext();
            allStep2Result.add(step2_1Result); //收集step3next
        }

        List<String> step3IdList = new ArrayList<String>();
        List<String> genIdList = new ArrayList<String>();
        for (GetReportRequestListResultVO step2Result : allStep2Result) {
            if (step2Result != null && step2Result.getGeneratedReportIdList() != null &&
                    step2Result.getGeneratedReportIdList().size() > 0) {
                genIdList.addAll(step2Result.getGeneratedReportIdList());
            } else if (step2Result != null && step2Result.getReportRequestIdList() != null &&
                    step2Result.getReportRequestIdList().size() > 0) {
                step3IdList.addAll(step2Result.getReportRequestIdList());
            }
        }

        //step 3   调用这个api是因为有些订单没有GeneratedReportId
        //集合所有第二部的参数
        List<String> allstep3_result = new ArrayList<String>();
        if (step3IdList.size() > 0) {
            List<String> step3Param = new ArrayList<String>(step3IdList);
            GetReportListVO vo = new GetReportListVO();  //封装给step3调用
            vo.setReportRequestIdList(step3Param);
            LOGGER.error("step3Param:" + step3Param);
            GetReportListResultVO step3Result = AmazonReportClient.getReportList(vo, api);//调用amazon step3

            //收集step3
            allstep3_result.addAll(step3Result.getReportIdList());

            //step 3.1   考虑到返回nextToken的情况
            boolean hasNext = step3Result.isHasNext();
            String token = step3Result.getNextToken();
            while (hasNext) {
                GetReportListResultVO step3_1Result = AmazonReportClient.getReportListByNextToken(token, api);
                token = step3_1Result.getNextToken();
                hasNext = step3_1Result.isHasNext();
                //收集step3next
                allstep3_result.addAll(step3_1Result.getReportIdList());
            }
        }
        allstep3_result.addAll(genIdList);


        //生成报告
        //step 2 和2.1的GeneratedReportId当为参数，或者step 3中生成的ReportId作为参数
        //step 4   如果有GeneratedReportId就可以直接生成报告了
        List<String> lastReportPathList = new ArrayList<String>();
        LOGGER.error("lastReportPathList：" + allstep3_result);

        for (String lastParam : allstep3_result) {
            String report = AmazonReportClient.getReport(lastParam, api);
            lastReportPathList.add(report);
        }

        return lastReportPathList;
    }


    /**
     * @param step1VO
     * @param api
     * @return
     * @throws MarketplaceWebServiceException
     * @throws InterruptedException
     * @throws IOException
     */
    @Override
    public String getReportAllResultUltimate(RequestReportVO step1VO, AmazonConfigInfo api) throws MarketplaceWebServiceException, InterruptedException, IOException {
        LOGGER.info("getReportStock step 1------init success");


        //call amazon return 文件路径
        List<String> allAbsolutePath = callAmazonGetReport(step1VO, api);


        //需要用Spring工厂代替
        getResolutionReportFileBean(step1VO.getReportType());

        if (CollectionUtils.isNotEmpty(allAbsolutePath)) {

            //获取文件对应的下标
            Map<String, Integer> reportIndex = this.resolutionReportFile.getReportIndex(allAbsolutePath.get(0));

            //把文件路径给转换为json
            String toJson = this.resolutionReportFile.fileToJson(allAbsolutePath, step1VO.getShopId(), reportIndex);

            LOGGER.error("getAgedReport step3 transfer object success and returning to OSMS");
            return toJson;
        }

        LOGGER.error("has no read json data ");
        return null;
    }


    /**
     * 获取一个商店下所有的报告
     * decide determine edition
     * 决定版，返回json
     * RTX
     * <p>
     * 这个就相当于最外层的controller
     *
     * @param step1VO
     * @param api
     * @return
     * @throws MarketplaceWebServiceException
     * @throws InterruptedException
     * @throws IOException
     */
    @Override
    public ALLReportUltimateVO getReportAllResultUltimateRTX(RequestReportVO step1VO, AmazonConfigInfo api) throws MarketplaceWebServiceException, InterruptedException, IOException {
        //调用亚马逊，获取报告信息
        List<ByteArrayOutputStream> byteArrayOutputStreams = AmazonReportClient.callAmazonSyncReportUltimateRTX(step1VO, api);

        LOGGER.info("getReportAllResultUltimateRTX callAmazonSyncReportUltimateRTX method has success done step first "+step1VO.getReportType());

        //切换bean，不同的报告文件不同的bean解析方式
        ResolutionReportFile resolutionReportFileBeanRTX = getResolutionReportFileBeanRTX(step1VO.getReportType());

        //基本路径
        String baseDir = CACHEDIR + step1VO.getReportType() + "\\" + api.getSellerID() + "\\";

        //一个字节数组对应一份亚马逊的报告
        List<String> fileAbsolutionPathList = AmazonReportClient.readByteArray(byteArrayOutputStreams, baseDir);

        //把临时文件cache给转换为对象
        if (CollectionUtils.isNotEmpty(fileAbsolutionPathList) && resolutionReportFileBeanRTX!=null) {
            Map<String, Integer> reportIndex = resolutionReportFileBeanRTX.getReportIndex(fileAbsolutionPathList.get(0));
            String json = resolutionReportFileBeanRTX.fileToJson(fileAbsolutionPathList, step1VO.getShopId(), reportIndex);

            LOGGER.info("getReportAllResultUltimateRTX  getReportIndex and fileToJson methods has success done step final "+step1VO.getReportType());

            return AmazonReportClient.convertBeanVO(step1VO, api, json, byteArrayOutputStreams);
        }

        LOGGER.info("getReportAllResultUltimateRTX  transfer json fail, methods has fail done step final enum"+step1VO.getReportType());
        return null;
    }


    /**
     * 不同的报告类型转换为不同的bean
     * 工厂的代替
     *
     * @param str
     * @return
     */
    private ResolutionReportFile getResolutionReportFileBeanRTX(String str) {
        if (IsRealGetStockReportFromAmazon.GetReportType.获取亚马逊库龄报告.enumType.equals(str)) {
            LOGGER.error("bean cast to resolutionInventoryAgedItem");

            return resolutionReportFileMap.get("resolutionInventoryAgedItem");
        } else if (IsRealGetStockReportFromAmazon.GetReportType.获取亚马逊商品库存报告.enumType.equals(str)) {
            LOGGER.error("bean cast to amazonStockItemReport");

            return resolutionReportFileMap.get("amazonStockItemReport");
        } else if (IsRealGetStockReportFromAmazon.GetReportType.亚马逊物流预计费用报告.enumType.equals(str)) {
            LOGGER.error("bean cast to logisticsForecastCostReport");

            return resolutionReportFileMap.get("logisticsForecastCostReport");

        }

        LOGGER.error("ResolutionReportFileBean inject false please check ");
        return null;

    }



        /**
         * 第二版的旧版，用来兼容老版本报告
         *
         * @param step1VO
         * @param api
         * @return return list txt file path
         * @throws MarketplaceWebServiceException
         * @throws InterruptedException
         * @throws IOException
         */
    private List<String> callAmazonGetReport(RequestReportVO step1VO, AmazonConfigInfo api) throws MarketplaceWebServiceException, InterruptedException, IOException {
        //step first   返回一个ReportRequestId
        RequestReportResultVO step1Result = AmazonReportClient.requestReport(step1VO, api);
        List<String> ids = new ArrayList<String>();
        ids.add(step1Result.getReportRequestId());  //一个枚举对应一个id

        //step 2
        GetReportRequestListVO step2VO = new GetReportRequestListVO();
        step2VO.setIds(ids);
        GetReportRequestListResultVO reportRequest = AmazonReportClient.getReportRequestList(step2VO, api, 5);

        //收集step2.0
        List<GetReportRequestListResultVO> allStep2Result = new ArrayList<GetReportRequestListResultVO>();
        allStep2Result.add(reportRequest);

        //step 2.1   考虑到返回nextToken的情况
        boolean hasNext1 = reportRequest.isHasNext();
        String token1 = reportRequest.getNextToken();
        while (hasNext1) {
            GetReportRequestListResultVO step2_1Result = AmazonReportClient.getReportRequestListByNextToken(token1, api);
            token1 = step2_1Result.getNextToken();
            hasNext1 = step2_1Result.isHasNext();
            allStep2Result.add(step2_1Result); //收集step3next
        }

        List<String> step3IdList = new ArrayList<String>();
        List<String> genIdList = new ArrayList<String>();
        for (GetReportRequestListResultVO step2Result : allStep2Result) {
            if (step2Result != null && step2Result.getGeneratedReportIdList() != null &&
                    step2Result.getGeneratedReportIdList().size() > 0) {
                genIdList.addAll(step2Result.getGeneratedReportIdList());
            } else if (step2Result != null && step2Result.getReportRequestIdList() != null &&
                    step2Result.getReportRequestIdList().size() > 0) {
                step3IdList.addAll(step2Result.getReportRequestIdList());
            }
        }

        //step 3   调用这个api是因为有些订单没有GeneratedReportId
        //集合所有第二部的参数
        List<String> allstep3_result = new ArrayList<String>();
        if (step3IdList.size() > 0) {
            List<String> step3Param = new ArrayList<String>(step3IdList);
            GetReportListVO vo = new GetReportListVO();  //封装给step3调用
            vo.setReportRequestIdList(step3Param);
            LOGGER.error("step3Param:" + step3Param);
            GetReportListResultVO step3Result = AmazonReportClient.getReportList(vo, api);//调用amazon step3

            //收集step3
            allstep3_result.addAll(step3Result.getReportIdList());

            LOGGER.error("step3Result.getReportIdList" + step3Result.getReportIdList());

            //step 3.1   考虑到返回nextToken的情况
            boolean hasNext = step3Result.isHasNext();
            String token = step3Result.getNextToken();
            while (hasNext) {
                GetReportListResultVO step3_1Result = AmazonReportClient.getReportListByNextToken(token, api);
                token = step3_1Result.getNextToken();
                hasNext = step3_1Result.isHasNext();
                //收集step3next
                allstep3_result.addAll(step3_1Result.getReportIdList());
            }
        }
        allstep3_result.addAll(genIdList);

        LOGGER.error("allstep3_result_all_genIdList" + genIdList);


        //生成报告
        //  step 2 和2.1的GeneratedReportId当为参数，或者step 3中生成的ReportId作为参数
        //step 4   如果有GeneratedReportId就可以直接生成报告了
        List<String> allAbsolutePath = new ArrayList<String>();
        LOGGER.error("allstep3_result_contains_genId：" + allstep3_result);

        //			String resultDerict = DeployProperties.getInstance().getProperty("amazon.result.filePath");
//			File file = new File(resultDerict + step1VO.getReportType() +"\\" +api.getSellerID()+"\\"+ DateUtils.convertDate(new Date(), "yyyyMMddHHmmss") + ".txt");


        for (String lastParam : allstep3_result) {
            String report = AmazonReportClient.getReport(lastParam, api);
            allAbsolutePath.add(report);
        }

        LOGGER.info("step2，system has read from amazon download report and transfer to backup file txt success . file absolute path" + allAbsolutePath);

        return allAbsolutePath;
    }


}
