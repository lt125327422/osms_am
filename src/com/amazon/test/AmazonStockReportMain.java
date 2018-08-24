package com.amazon.test;

import com.amazon.test.testVO.*;
import com.amazonaws.mws.MarketplaceWebServiceClient;
import com.amazonaws.mws.MarketplaceWebServiceConfig;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.*;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Auther: liteng
 * @Date: 2018/7/5 18:07
 * @Description: Amazon_stock_report
 */
public class AmazonStockReportMain {


    private static int MOCK = 0;
    private static int REAL_INVOKE = 1;
    private static int IS_REAL_INVOKE = -1; //修改这个来用于判断是否真实调用亚马逊
    private static List<String> AMAZON_MARKETPLSCEID_LIST = null;
    private static String AMAZON_SELLER_ID = null;
    private static MarketplaceWebServiceConfig config = null;
    private static MarketplaceWebServiceClient client = null;
    private static Properties properties = new Properties();
    private static File resultFile = null;
    private static RequestReportVO step1VO = new RequestReportVO();
    private static AmazonConfigInfo api = new AmazonConfigInfo();  //在main中无用
    private static String startDate = null;
    private static String endDate = null;
    public AmazonStockReportMain() {
    }

    //E:\osms_am\src\com\amazon\testResource
    //初始化
    public static void init() throws IOException {
//        FileInputStream inStream = new FileInputStream(new File("src\\com\\amazon\\testResource\\deploy_config.properties"));

//        FileInputStream inStream = new FileInputStream(new File(properties.getProperty("amazon.result.filePath")));
        FileInputStream inStream = new FileInputStream(new File("deploy_config.properties"));

        properties.load(inStream);
        resultFile = new File(properties.getProperty("amazon.result.filePath") + properties.getProperty("amazon.reportType") + DateUtils.convertDate(new Date(), "yyyyMMddHHmmss") + ".txt");

        startDate = properties.getProperty("amazon.report.startDate", "333");
        endDate = properties.getProperty("amazon.report.endDate", "333");

        FileUtils.touch(resultFile);

        AMAZON_MARKETPLSCEID_LIST = Arrays.asList(properties.getProperty("amazon.MarketplaceID").split(","));

        AMAZON_SELLER_ID = properties.getProperty("amazon.SellerID");
        config = new MarketplaceWebServiceConfig();
        config.setServiceURL(properties.getProperty("amazon.serviceURL"));

        client = new MarketplaceWebServiceClient(properties.getProperty("amazon.accessKeyId"), properties.getProperty("amazon.secretAccessKey"),
                properties.getProperty("amazon.appName"), properties.getProperty("amazon.appVersion"), config);
        config.setConnectionTimeout(1000 * 60 * 2);
        config.setSoTimeout(1000 * 60 * 2);

        IS_REAL_INVOKE = Integer.parseInt(properties.getProperty("amazon.is.mock"));
    }

    //controller
    public static void main(String[] args) {
        try {
//            String str = "2016-09-08 09:15:12";
            init(); //初始化client         file与src同级
            step1VO.setReportType("_GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_"); //要获取的报告类型
            //step first vo setting
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (!"333".equals(startDate)) {
                step1VO.setStartDate(format.parse(startDate));
            }
            if (!"333".equals(endDate)) {
                step1VO.setEndDate(format.parse(endDate));
            }

//            DateUtils.convertDate(new Date(), "yyyyMMddHHmmss");


            if (IS_REAL_INVOKE == REAL_INVOKE) {
                List<String> lastReportList = serviceMain(step1VO, api);    //调用亚马逊1234,得到多个report

//                //把多个报告写入到多个temp文件中
//                List<String> backupFileOutput = newFileOutput(lastReportList);
//
//                //读取temp文件
//                List<AmazonStockReportVO> amazonStockReportVOS = fileOutputOriginalVersion(backupFileOutput);
//
//                //把vo写入result.txt文件中
//                fileOutput003(amazonStockReportVOS);
            } else if (IS_REAL_INVOKE == MOCK) {
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

                //读取temp文件 并写入cache
                List<AmazonStockReportVO> amazonStockReportVOS = AmazonStockReportMain.fileOutputOriginalVersion(backupFileOutput);

                //把vo写入result.txt文件中
                AmazonStockReportMain.fileOutput003(amazonStockReportVOS);
            }
        } catch (MarketplaceWebServiceException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 调用亚马逊
     *
     * @param step1VO
     * @param api
     * @return
     * @throws MarketplaceWebServiceException
     */
    public static List<String> serviceMain(RequestReportVO step1VO, AmazonConfigInfo api) throws MarketplaceWebServiceException, InterruptedException, IOException {
        //step first   返回一个ReportRequestId
        RequestReportResultVO step1Result = requestReport(step1VO, api);
        System.out.println("requestReport:" + step1Result.getReportRequestId());
        List<String> ids = new ArrayList<String>();
        ids.add(step1Result.getReportRequestId());  //一个枚举对应一个id

//        Thread.sleep(3000);

        //step 2
        GetReportRequestListVO step2VO = new GetReportRequestListVO();
        step2VO.setIds(ids);
        GetReportRequestListResultVO reportRequest = getReportRequestList(step2VO, api);
        System.out.println("getReportRequestList:" + reportRequest.getGeneratedReportIdList().size());

        //直接生成报告
//        for (String generatedReportId : reportRequest.getGeneratedReportIdList()) {
//            String report = AmazonReportClient.getReport(generatedReportId, api);
//        }

        //收集step2.0
        List<GetReportRequestListResultVO> allStep2Result = new ArrayList<GetReportRequestListResultVO>();
        allStep2Result.add(reportRequest);

        //step 2.1   考虑到返回nextToken的情况
        boolean hasNext1 = reportRequest.isHasNext();
        String token1 = reportRequest.getNextToken();
        while (hasNext1) {
            GetReportRequestListResultVO step2_1Result = getReportRequestListByNextToken(token1, api);
            System.out.println("getReportRequestListByNextToken:" + step2_1Result.getGeneratedReportIdList().size());
            token1 = step2_1Result.getNextToken();
            hasNext1 = step2_1Result.isHasNext();
            allStep2Result.add(step2_1Result); //收集step3next
        }

//        List<String> hasGenIdIndex =  new ArrayList<String>();
//        List<String>  assortGenId = new ArrayList<String>();    //所有的有genId的
//        List<String>  normalStep3 = new ArrayList<String>();
//        //generateId 分类出有generateId的和没有的
//        for (GetReportRequestListResultVO vo003 : allStep2Result) {
//            for (int i = 0; i <vo003.getGeneratedReportIdList().size() ; i++) {
//                if (vo003.getGeneratedReportIdList().get(i)==null || "".equals(vo003.getGeneratedReportIdList().get(i).trim())){
//                    assortGenId.add(vo003.getGeneratedReportIdList().get(i));
//                    hasGenIdIndex.add(vo003.getGeneratedReportIdList().get(i));
//                }
//            }
//            for (int i = 0; i <vo003.getReportRequestIdList().size() ; i++) {
//                if (vo003.getReportRequestIdList().get(i) == null || "".equals(vo003.getReportRequestIdList().get(i).trim())) {
//                    normalStep3.add(vo003.getReportRequestIdList().get(i));
//                }
//            }
//        }

        List<String> assortGenId = new ArrayList<String>();
        List<String> step3IdList = new ArrayList<String>();
        for (GetReportRequestListResultVO step2Result : allStep2Result) {
            if (step2Result != null && step2Result.getReportRequestIdList().size() > 0) {
                step3IdList.addAll(step2Result.getReportRequestIdList());   //reportrequestId
            } else if (step2Result != null && step2Result.getGeneratedReportIdList().size() > 0) {
                assortGenId.addAll(step2Result.getGeneratedReportIdList()); //gemId
            }
        }

        System.out.println("genID---" + assortGenId.size());
        System.out.println("reportRequestId---" + step3IdList.size());

        List<String> allstep3_result = new ArrayList<String>();
        if (step3IdList.size() > 0) {
            //step 3   调用这个api是因为有些订单没有GeneratedReportId
            //集合所有第二部的参数
            List<String> step3Param = new ArrayList<String>(step3IdList);
            GetReportListVO vo = new GetReportListVO();  //封装给step3调用
            vo.setReportRequestIdList(step3Param);

            GetReportListResultVO step3Result = getReportList(vo, api);//调用amazon step3
            System.out.println("getReportList:" + step3Result.getReportIdList().size());
            //10285737386017723
            //收集step3
            allstep3_result = new ArrayList<String>(step3Result.getReportIdList());

            //step 3.1   考虑到返回nextToken的情况
            boolean hasNext = step3Result.isHasNext();
            String token = step3Result.getNextToken();
            while (hasNext) {
                GetReportListResultVO step3_1Result = getReportListByNextToken(token, api);
                System.out.println("getReportListByNextToken:" + step3_1Result.getReportIdList().size());
                token = step3_1Result.getNextToken();
                hasNext = step3_1Result.isHasNext();
                //收集step3next
                allstep3_result.addAll(step3_1Result.getReportIdList());
            }
        }

        //生成报告
        //step 2 和2.1的GeneratedReportId当为参数，或者step 3中生成的ReportId作为参数   最后要多次调用report
        //step 4   如果有GeneratedReportId就可以直接生成报告了

        if (assortGenId.size() > 0) {
            allstep3_result.addAll(assortGenId);    //把genId也加进去
        }
        System.out.println("allstep3_resultParam---" + allstep3_result.size());

        List<String> lastReportList = new ArrayList<String>();
        for (String lastParam : allstep3_result) {
            lastReportList.add(getReport(lastParam, api));
            //md5校验，如果一直失败就发邮件
        }
        return lastReportList;
    }


    /**
     * 读取文本为字符串，之后转化为一个个的对象
     *
     * @param
     * @throws IOException
     */
    public static List<AmazonStockReportVO> fileOutputOriginalVersion(List<String> pathnameList) throws IOException {
        List<AmazonStockReportVO> reportFormList = new ArrayList<AmazonStockReportVO>(); //所有的报告
//            String pathname = "D:\\osms_am\\result_file\\_GET_FBA_MYI_UNSUPPRESSED_INVENTORY_DATA_20180709090422.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
//            String pathname = "D:\\osms_am\\result_file\\getreport.txt";
        //每读取一个temp
        for (String pathName : pathnameList) {
            File filename = new File(pathName);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();    //读取第一行
            Map<String, Integer> indexMap = getIndex(line);     //获取下标
            line = br.readLine();   //第二行开始才是数据
            while (line != null) {
                String[] splitSingle = line.split("\t"); //取得每一行的每一个
                List<String> single = Arrays.asList(splitSingle);
                AmazonStockReportVO amazonStockReportVO = new AmazonStockReportVO();   //把每一行数据封装为一个vo
                amazonStockReportVO.setSku(single.get(indexMap.get("sku")));
                Date date = new Date();
                amazonStockReportVO.setSyncLast(date);
                amazonStockReportVO.setSyncFirst(date);
                amazonStockReportVO.setAfnInboundWorkingQuantity(Integer.valueOf(single.get(indexMap.get("workingIndex"))));
                amazonStockReportVO.setAfnWarehouseQuantity(Integer.valueOf(single.get(indexMap.get("warehouseIndex"))));
                amazonStockReportVO.setAfnFulfillableQuantity(Integer.valueOf(single.get(indexMap.get("fulfillableIndex"))));
                amazonStockReportVO.setAfnUnsellableQuantity(Integer.valueOf(single.get(indexMap.get("unsellableIndex"))));
                amazonStockReportVO.setAfnInboundShippedQuantity(Integer.valueOf(single.get(indexMap.get("inboundShippedIndex"))));
                amazonStockReportVO.setAfnReservedQuantity(Integer.valueOf(single.get(indexMap.get("reservedIndex"))));
                amazonStockReportVO.setAfnInboundReceivingQuantity(Integer.valueOf(single.get(indexMap.get("receivingIndex"))));
                amazonStockReportVO.setAfnTotalQuantity(amazonStockReportVO.getAfnFulfillableQuantity() +
                        amazonStockReportVO.getAfnInboundReceivingQuantity() + amazonStockReportVO.getAfnInboundShippedQuantity()
                        + amazonStockReportVO.getAfnInboundWorkingQuantity() + amazonStockReportVO.getAfnReservedQuantity());

                reportFormList.add(amazonStockReportVO);
                line = br.readLine(); // 一次读入一行数据
            }
        }
        return reportFormList;
    }


    /**
     * 就是不知道用什么来分割，所以才写入了一行到文件中
     * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     *
     * @param reportList
     * @throws IOException
     */
    public static List<String> newFileOutput(List<String> reportList) throws IOException {
        int s3 = 0;
        List<String> absolutePathList = new ArrayList<String>();
        if (reportList != null && reportList.size() > 0) {
            for (String report : reportList) {
                File tempFile = new File(properties.getProperty("amazon.temp.filePath") + properties.getProperty("amazon.reportType") + DateUtils.convertDate(new Date(), "yyyyMMddHHmmss") + s3 + ".txt");

                FileUtils.touch(tempFile);
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
                bw.write(report);
                //                bw.newLine();   //写入一个行分隔符
                bw.flush();
                String absolutePath = tempFile.getAbsolutePath();
                absolutePathList.add(absolutePath);
                s3++;
            }
        }
        System.out.println("------------------------------------------" + resultFile.getAbsolutePath());
        return absolutePathList;
    }


    /**
     * 把所有的vo写入到txt文件中 pass  old edition
     *
     * @param vos
     * @throws IOException
     */
    public static void fileOutput(List<AmazonStockReportVO> vos) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BufferedWriter bw = new BufferedWriter(new FileWriter(resultFile));
        System.out.println("------------------------------------------" + resultFile.getAbsolutePath());
        for (AmazonStockReportVO amazonStockReportVO : vos) {
            bw.write("sku" + amazonStockReportVO.getSku() + "\t");
            bw.write("shopId" + amazonStockReportVO.getShopId() + "\t");
            bw.write("syncFirst" + format.format(amazonStockReportVO.getSyncFirst()) + "\t");
            bw.write("syncLast" + format.format(amazonStockReportVO.getSyncLast()) + "\t");
            bw.write("AfnReservedQuantity" + amazonStockReportVO.getAfnReservedQuantity() + "\t");
            bw.write("AfnInboundWorkingQuantity" + amazonStockReportVO.getAfnInboundWorkingQuantity() + "\t");
            bw.write("AfnInboundShippedQuantity" + amazonStockReportVO.getAfnInboundShippedQuantity() + "\t");
            bw.write("AfnInboundReceivingQuantity" + amazonStockReportVO.getAfnInboundReceivingQuantity() + "\t");
            bw.write("AfnFulfillableQuantity" + amazonStockReportVO.getAfnFulfillableQuantity() + "\t");
            bw.write("AfnWarehouseQuantity" + amazonStockReportVO.getAfnWarehouseQuantity() + "\t");
            bw.write("AfnUnsellableQuantity" + amazonStockReportVO.getAfnUnsellableQuantity() + "\t");
            bw.write("AfnTotalQuantity" + amazonStockReportVO.getAfnTotalQuantity() + "\t");
//            bw.write(""+amazonStockReportVO.getFbaSeaTransit() +"\t");
            bw.newLine();   //写入一个行分隔符
            bw.flush();
        }
        bw.close();
        System.out.println("------------------------------------------" + resultFile.getAbsolutePath());
    }
//"\r\n"

    public static void fileOutput003(List<AmazonStockReportVO> vos) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BufferedWriter bw = new BufferedWriter(new FileWriter(resultFile));
        System.out.println("------------------------------------------" + resultFile.getAbsolutePath());
        //first line
        bw.write("sku" + "\t");
        bw.write("shopId" + "\t");
        bw.write("syncFirst" + "\t");
        bw.write("syncLast" + "\t");
        bw.write("AfnReservedQuantity" + "\t");
        bw.write("AfnInboundWorkingQuantity" + "\t");
        bw.write("AfnInboundShippedQuantity" + "\t");
        bw.write("AfnInboundReceivingQuantity" + "\t");
        bw.write("AfnFulfillableQuantity" + "\t");
        bw.write("AfnWarehouseQuantity" + "\t");
        bw.write("AfnUnsellableQuantity" + "\t");
        bw.write("AfnTotalQuantity" + "\t");
        bw.newLine();

        for (AmazonStockReportVO amazonStockReportVO : vos) {
            bw.write(amazonStockReportVO.getSku() + "\t");
            bw.write(amazonStockReportVO.getShopId() + "\t");
            bw.write(format.format(amazonStockReportVO.getSyncFirst()) + "\t");
            bw.write(format.format(amazonStockReportVO.getSyncLast()) + "\t");
            bw.write(amazonStockReportVO.getAfnReservedQuantity() + "\t");
            bw.write(amazonStockReportVO.getAfnInboundWorkingQuantity() + "\t");
            bw.write(amazonStockReportVO.getAfnInboundShippedQuantity() + "\t");
            bw.write(amazonStockReportVO.getAfnInboundReceivingQuantity() + "\t");
            bw.write(amazonStockReportVO.getAfnFulfillableQuantity() + "\t");
            bw.write(amazonStockReportVO.getAfnWarehouseQuantity() + "\t");
            bw.write(amazonStockReportVO.getAfnUnsellableQuantity() + "\t");
            bw.write(amazonStockReportVO.getAfnTotalQuantity() + "\t");
//            bw.write(""+amazonStockReportVO.getFbaSeaTransit() +"\t");
            bw.newLine();   //写入一个行分隔符
            bw.flush();
        }
        bw.close();
        System.out.println("------------------------------------------" + resultFile.getAbsolutePath());
        System.out.println("the End of war");
    }

    /**
     * 根据报告来过去对应字段的下标
     *
     * @param report
     * @return
     */
    public static Map<String, Integer> getIndex(String report) {
        String[] splitRows = report.split("\r\n");  //根据换行符来分割，得到每行的数据
        String[] splitRowFirst = splitRows[0].split("\t"); //取得每一行的每一个
        List<String> singleFirst = Arrays.asList(splitRowFirst);
        int sku = singleFirst.indexOf("sku");
        int workingIndex = singleFirst.indexOf("afn-inbound-working-quantity");
        int warehouseIndex = singleFirst.indexOf("afn-warehouse-quantity");
        int fulfillableIndex = singleFirst.indexOf("afn-fulfillable-quantity");
        int unsellableIndex = singleFirst.indexOf("afn-unsellable-quantity");
        int inboundShippedIndex = singleFirst.indexOf("afn-inbound-shipped-quantity");
        int reservedIndex = singleFirst.indexOf("afn-reserved-quantity");
        int receivingIndex = singleFirst.indexOf("afn-inbound-receiving-quantity");

        Map<String, Integer> indexMap = new HashMap<String, Integer>();
        indexMap.put("sku", sku);
        indexMap.put("workingIndex", workingIndex);
        indexMap.put("warehouseIndex", warehouseIndex);
        indexMap.put("fulfillableIndex", fulfillableIndex);
        indexMap.put("unsellableIndex", unsellableIndex);
        indexMap.put("inboundShippedIndex", inboundShippedIndex);
        indexMap.put("reservedIndex", reservedIndex);
        indexMap.put("receivingIndex", receivingIndex);
        return indexMap;
    }


    /**
     * 解析报告         old edition
     * \r 换行 \n 回车  \b 空格   \t 水平制表符
     *
     * @param report
     * @return
     */
    public static List<AmazonStockReportVO> resolvingReport(String report) {
        List<AmazonStockReportVO> reportFormList = new ArrayList<AmazonStockReportVO>(); //所有的报告
        String[] splitRows = report.split("\r\n");  //根据换行符来分割，得到每行的数据
        if (splitRows.length >= 2) {
            String[] splitRowFirst = splitRows[0].split("\t"); //取得每一行的每一个
            List<String> singleFirst = Arrays.asList(splitRowFirst);
            int sku = singleFirst.indexOf("sku");
            int workingIndex = singleFirst.indexOf("afn-inbound-working-quantity");
            int warehouseIndex = singleFirst.indexOf("afn-warehouse-quantity");
            int fulfillableIndex = singleFirst.indexOf("afn-fulfillable-quantity");
            int unsellableIndex = singleFirst.indexOf("afn-unsellable-quantity");
            int inboundShippedIndex = singleFirst.indexOf("afn-inbound-shipped-quantity");
            int reservedIndex = singleFirst.indexOf("afn-reserved-quantity");
            int receivingIndex = singleFirst.indexOf("afn-inbound-receiving-quantity");
            for (int i = 1; i < splitRows.length; i++) {
                String[] splitSingle = splitRows[i].split("\t"); //取得每一行的每一个
                List<String> single = Arrays.asList(splitSingle);
                AmazonStockReportVO amazonStockReportVO = new AmazonStockReportVO();   //把每一行数据封装为一个vo
                amazonStockReportVO.setSku(single.get(0));
                Date date = new Date();
                amazonStockReportVO.setSyncLast(date);
                amazonStockReportVO.setSyncFirst(date);

                amazonStockReportVO.setAfnInboundWorkingQuantity(Integer.valueOf(single.get(workingIndex)));
                amazonStockReportVO.setAfnWarehouseQuantity(Integer.valueOf(single.get(warehouseIndex)));
                amazonStockReportVO.setAfnFulfillableQuantity(Integer.valueOf(single.get(fulfillableIndex)));
                amazonStockReportVO.setAfnUnsellableQuantity(Integer.valueOf(single.get(unsellableIndex)));
                amazonStockReportVO.setAfnInboundShippedQuantity(Integer.valueOf(single.get(inboundShippedIndex)));
                amazonStockReportVO.setAfnReservedQuantity(Integer.valueOf(single.get(reservedIndex)));
                amazonStockReportVO.setAfnInboundReceivingQuantity(Integer.valueOf(single.get(receivingIndex)));

                amazonStockReportVO.setAfnTotalQuantity(amazonStockReportVO.getAfnFulfillableQuantity() +
                        amazonStockReportVO.getAfnInboundReceivingQuantity() + amazonStockReportVO.getAfnInboundShippedQuantity()
                        + amazonStockReportVO.getAfnInboundWorkingQuantity() + amazonStockReportVO.getAfnReservedQuantity());
                reportFormList.add(amazonStockReportVO);
            }
        }
        return reportFormList;
    }

    /**
     *
     * 初始化
     *
     * @param api
     */
//    public static void init(AmazonConfigInfo api) {
//        MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
//        config.setServiceURL(api.getServiceURL());
//        client = new MarketplaceWebServiceClient(api.getAccessKeyId(), api.getSecretAccessKey(),
//                DeployProperties.getInstance().getAppName(),DeployProperties.getInstance().getAppVersion(),config);
//
//    }

    /**
     * step 1
     *
     * @param requestReportVO
     * @return
     */
    public static RequestReportResultVO requestReport(RequestReportVO requestReportVO, AmazonConfigInfo api) throws MarketplaceWebServiceException {
//        init(api);

        RequestReportRequest request = new RequestReportRequest();
        request.setReportType(requestReportVO.getReportType());     //传入枚举类型
//        request.setStartDate(DateUtils.getXMLGregorianCalendar(requestReportVO.getStartDate()));
//        封装request
//        IdList ids = new IdList();
//        request.setMarketplaceIdList(ids);
        RequestReportResultVO resultVO = new RequestReportResultVO();    //封装result
        request.setMerchant(AMAZON_SELLER_ID);
//        final IdList marketplaces = new IdList(AMAZON_MARKETPLSCEID_LIST);
//        request.setMarketplaceIdList(marketplaces);
        request.setStartDate(DateUtils.getXMLGregorianCalendar(requestReportVO.getStartDate()));
        request.setEndDate(DateUtils.getXMLGregorianCalendar(requestReportVO.getEndDate()));
        //真实调用亚马逊
        if (IS_REAL_INVOKE == REAL_INVOKE) {
            RequestReportResponse response = client.requestReport(request);
            ReportRequestInfo info = response.getRequestReportResult().getReportRequestInfo();

            //ADD LOG
            resultVO.setReportRequestId(info.getReportRequestId());
//            resultVO.setEndDate(DateUtils.getDateByXMLGregorianCalendar(info.getEndDate()));
        } else if (IS_REAL_INVOKE == MOCK) {
            //ADD LOG

        }
        return resultVO;
    }

    /**
     * step 2
     *
     * @param getReportRequestListVO
     * @return
     */
    public static GetReportRequestListResultVO getReportRequestList(GetReportRequestListVO getReportRequestListVO, AmazonConfigInfo api) throws MarketplaceWebServiceException, InterruptedException {
//        init(api);
        GetReportRequestListRequest request = new GetReportRequestListRequest();
        IdList ids = new IdList();
        ids.setId(getReportRequestListVO.getIds());

        request.setReportRequestIdList(ids);
        request.setMerchant(AMAZON_SELLER_ID);
        GetReportRequestListResultVO vo = new GetReportRequestListResultVO();
        //是否要过滤请求

        Thread.sleep(20000);
        GetReportRequestListResponse reportRequestList = client.getReportRequestList(request);
        System.out.println("getReportRequestList:" + reportRequestList.toXML());

        List<ReportRequestInfo> infoList = reportRequestList.getGetReportRequestListResult().getReportRequestInfoList();


        vo = new GetReportRequestListResultVO();
        vo.setHasNext(reportRequestList.getGetReportRequestListResult().isHasNext());
        vo.setNextToken(reportRequestList.getGetReportRequestListResult().getNextToken());

        List<String> reportRequestIdList = new ArrayList<String>();
        List<String> generatedReportIdList = new ArrayList<String>();

        for (ReportRequestInfo info : infoList) {
            if (!info.getReportProcessingStatus().equals("_DONE_")) {
                Thread.sleep(20000);
                return getReportRequestList(getReportRequestListVO, api);
            }
            System.out.println("getGeneratedReportId:" + info.getGeneratedReportId());
            //对象封装回result
            if (info.getGeneratedReportId() != null) {
                generatedReportIdList.add(info.getGeneratedReportId());
            } else {
                reportRequestIdList.add(info.getReportRequestId());
            }
        }
        vo.setReportRequestIdList(reportRequestIdList);
        vo.setGeneratedReportIdList(generatedReportIdList);


        return vo;

    }


    /**
     * step 3 如果没有GeneratedReportId   才调用这个方法
     *
     * @param api
     */
    public static GetReportListResultVO getReportList(GetReportListVO vo, AmazonConfigInfo api) throws MarketplaceWebServiceException, InterruptedException {
//        init(api);
        GetReportListRequest request = new GetReportListRequest();
        request.setMerchant(AMAZON_SELLER_ID);

        IdList list = new IdList();
        list.setId(vo.getReportRequestIdList());
        request.setReportRequestIdList(list);
        GetReportListResultVO resultVO = new GetReportListResultVO();
        if (IS_REAL_INVOKE == REAL_INVOKE) {

            Thread.sleep(5000);
            GetReportListResponse reportList = client.getReportList(request);
            List<ReportInfo> reportInfoList = reportList.getGetReportListResult().getReportInfoList();
//10290142551017723
            List<String> _reportIdList = new ArrayList<String>();
            for (ReportInfo info : reportInfoList) {
//                _reportIdList.add(info.getReportRequestId());
                _reportIdList.add(info.getReportId());
            }
            resultVO.setReportIdList(_reportIdList);
        } else if (IS_REAL_INVOKE == MOCK) {
        }
        return resultVO;
    }


    /**
     * step 2.1
     *
     * @param
     * @param nextToken
     */
    public static GetReportRequestListResultVO getReportRequestListByNextToken(String nextToken, AmazonConfigInfo api) throws MarketplaceWebServiceException, InterruptedException {
//        init(api);
        GetReportRequestListByNextTokenRequest request = new GetReportRequestListByNextTokenRequest();
        request.setNextToken(nextToken);

        request.setMerchant(AMAZON_SELLER_ID);
        GetReportRequestListResultVO resultVO = new GetReportRequestListResultVO();
        if (IS_REAL_INVOKE == REAL_INVOKE) {

            Thread.sleep(5000);
            GetReportRequestListByNextTokenResponse response = client.getReportRequestListByNextToken(request);

            resultVO = new GetReportRequestListResultVO();
            List<ReportRequestInfo> reportRequestInfoList = response.getGetReportRequestListByNextTokenResult().getReportRequestInfoList();
            resultVO.setHasNext(response.getGetReportRequestListByNextTokenResult().isHasNext());
            resultVO.setNextToken(response.getGetReportRequestListByNextTokenResult().getNextToken());

            List<String> reportRequestIdList = new ArrayList<String>();
            for (ReportRequestInfo info : reportRequestInfoList) {
                reportRequestIdList.add(info.getReportRequestId());
            }
            resultVO.setReportRequestIdList(reportRequestIdList);

            //add log
        } else if (IS_REAL_INVOKE == MOCK) {
            //add log
        }
        return resultVO;
    }

    /**
     * step 3.1
     *
     * @param api
     * @param nextToken
     */
    public static GetReportListResultVO getReportListByNextToken(String nextToken, AmazonConfigInfo api) throws MarketplaceWebServiceException {
//        init(api);
        GetReportListByNextTokenRequest request = new GetReportListByNextTokenRequest();
        request.setNextToken(nextToken);

        request.setMerchant(AMAZON_SELLER_ID);
        GetReportListResultVO resultVO = new GetReportListResultVO();
        if (IS_REAL_INVOKE == REAL_INVOKE) {
            GetReportListByNextTokenResponse response = client.getReportListByNextToken(request);
            List<ReportInfo> infoList = response.getGetReportListByNextTokenResult().getReportInfoList();
            resultVO.setHasNext(response.getGetReportListByNextTokenResult().isHasNext());
            resultVO.setNextToken(response.getGetReportListByNextTokenResult().getNextToken());

            List<String> reportIdList = new ArrayList<String>();
            for (ReportInfo info : infoList) {
                reportIdList.add(info.getReportId());
            }
            resultVO.setReportIdList(reportIdList);
        } else if (IS_REAL_INVOKE == MOCK) {

        }
        return resultVO;
    }


    /**
     * step 4 the end
     * 返回一个字符串
     *
     * @return
     * @throws MarketplaceWebServiceException
     */

    public static String getReport(String reportId, AmazonConfigInfo api) throws IOException {
//        init(api);
        GetReportRequest request = new GetReportRequest();
        request.setReportId(reportId);
        request.setMerchant(AMAZON_SELLER_ID);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        request.setReportOutputStream(baos);

        GetReportResponse response = null;
        try {
            Thread.sleep(23000);
            response = client.getReport(request);

            byte[] bytes = baos.toByteArray();
            FileUtils.writeByteArrayToFile(new File(resultFile.getParent() + "/aa.xls"), bytes);
//            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//            Reader fileReader = new InputStreamReader(bais, "UTF-8");

            System.out.println("getReport:" + response.toXML());
            System.out.println("getReport:" + response.toJSON());
//            String responseContext = response.getResponseHeaderMetadata().getResponseContext();
            String md5Checksum = response.getGetReportResult().getMD5Checksum();
            return null;
        } catch (MarketplaceWebServiceException e) {
            System.out.println("exception inputDateError");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Calculate content MD5 header values for feeds stored on disk.
     */
    public static String computeContentMD5HeaderValue(FileInputStream fis)
            throws IOException, NoSuchAlgorithmException {

        DigestInputStream dis = new DigestInputStream(fis,
                MessageDigest.getInstance("MD5"));

        byte[] buffer = new byte[8192];
        while (dis.read(buffer) > 0) ;

        String md5Content = new String(
                org.apache.commons.codec.binary.Base64.encodeBase64(
                        dis.getMessageDigest().digest()));

        // Effectively resets the stream to be beginning of the file
        // via a FileChannel.
        fis.getChannel().position(0);

        return md5Content;
    }


    /**
     * md5效验    工具
     * Consume the stream and return its Base-64 encoded MD5 checksum.
     */
    public static String computeContentMD5Header(InputStream inputStream) {
        // Consume the stream to compute the MD5 as a side effect.
        DigestInputStream s;
        try {
            s = new DigestInputStream(inputStream,
                    MessageDigest.getInstance("MD5"));
            // drain the buffer, as the digest is computed as a side-effect
            byte[] buffer = new byte[8192];
            while (s.read(buffer) > 0) ;
            return new String(
                    org.apache.commons.codec.binary.Base64.encodeBase64(
                            s.getMessageDigest().digest()),
                    "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
