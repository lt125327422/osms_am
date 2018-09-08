package com.amazon.client;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.mws.MarketplaceWebServiceClient;
import com.amazonaws.mws.MarketplaceWebServiceConfig;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.GetReportListRequest;
import com.amazonaws.mws.model.GetReportListResponse;
import com.amazonaws.mws.model.GetReportListResult;
import com.amazonaws.mws.model.GetReportRequest;
import com.amazonaws.mws.model.GetReportRequestListByNextTokenRequest;
import com.amazonaws.mws.model.GetReportRequestListByNextTokenResponse;
import com.amazonaws.mws.model.GetReportRequestListByNextTokenResult;
import com.amazonaws.mws.model.GetReportRequestListRequest;
import com.amazonaws.mws.model.GetReportRequestListResponse;
import com.amazonaws.mws.model.GetReportRequestListResult;
import com.amazonaws.mws.model.IdList;
import com.amazonaws.mws.model.ReportRequestInfo;
import com.amazonaws.mws.model.RequestReportRequest;
import com.amazonaws.mws.model.RequestReportResponse;
import com.itecheasy.common.util.DateUtils;
import com.itecheasy.common.util.DeployProperties;
import com.itecheasy.core.amazon.AmazonConfigInfo;
import com.itecheasy.core.amazon.vo.ReportConfigVO;

/**
 * @author taozihao
 * @date 2018年9月5日 下午2:28:44
 * @description	亚马逊报告，为自动任务
 */
public class AmazonReportNewClient {
	
	public static MarketplaceWebServiceClient getClient(AmazonConfigInfo api) {
        MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
        config.setServiceURL(api.getServiceURL());
        return new MarketplaceWebServiceClient(api.getAccessKeyId(), api.getSecretAccessKey(),
                DeployProperties.getInstance().getAppName(),DeployProperties.getInstance().getAppVersion(),config);
    }
	
	/**
	 * 1 请求报告
	 * 
	 * @param api
	 * @param reportConfigVO
	 * @return
	 * @throws MarketplaceWebServiceException
	 */
	public static ReportRequestInfo requestReport(MarketplaceWebServiceClient client,AmazonConfigInfo api,ReportConfigVO reportConfigVO) throws MarketplaceWebServiceException{
		RequestReportRequest request = new RequestReportRequest();
		request.setMerchant(api.getSellerID());
		if(reportConfigVO.getStartDate()!=null){
			request.setStartDate(DateUtils.getXMLGregorianCalendar(reportConfigVO.getStartDate()));
		}
		if(reportConfigVO.getEndDate()!=null){
			request.setEndDate(DateUtils.getXMLGregorianCalendar(reportConfigVO.getEndDate()));
		}
		IdList idList = new IdList();
		ArrayList<String> marketplaceIdList = new ArrayList<String>();
		marketplaceIdList.add(api.getMarketplaceID());
		idList.setId(marketplaceIdList);
		request.setMarketplaceIdList(idList);
		request.setReportOptions(reportConfigVO.getReportOptions());
		request.setReportType(reportConfigVO.getReportType());
		RequestReportResponse requestReport = client.requestReport(request);
		return requestReport.getRequestReportResult().getReportRequestInfo();
	}
	
	/**
	 * 2 获取报告请求的信息
	 * 
	 * @param client
	 * @param sellerId
	 * @param requestReportIds
	 * @return
	 * @throws MarketplaceWebServiceException
	 */
	public static GetReportRequestListResult getReportRequestList(MarketplaceWebServiceClient client,String sellerId,List<String> requestReportIds) throws MarketplaceWebServiceException{
		GetReportRequestListRequest request = new GetReportRequestListRequest();
		request.setMerchant(sellerId);
		IdList idList = new IdList();
		idList.setId(requestReportIds);
		request.setReportRequestIdList(idList);
		GetReportRequestListResponse response = client.getReportRequestList(request);
		return response.getGetReportRequestListResult();
	}
	
	/**
	 * 2.1获取报告请求的信息nextToken 情况
	 * 
	 * @param client
	 * @param sellerId
	 * @param nextToken
	 * @return
	 * @throws MarketplaceWebServiceException
	 */
	public static GetReportRequestListByNextTokenResult getReportRequestListByNextToken(MarketplaceWebServiceClient client,String sellerId,String nextToken) throws MarketplaceWebServiceException{
		GetReportRequestListByNextTokenRequest request = new GetReportRequestListByNextTokenRequest();
		request.setNextToken(nextToken);
		request.setMerchant(sellerId);
		GetReportRequestListByNextTokenResponse response = client.getReportRequestListByNextToken(request);
		return response.getGetReportRequestListByNextTokenResult();
	}
	
	/**
	 * 3 获取报告，这个方法最多一分钟调用一次
	 * 
	 * @param client
	 * @param sellerId
	 * @param reportId
	 * @return
	 * @throws MarketplaceWebServiceException
	 * @throws InterruptedException 
	 */
	public static byte[] getReport(MarketplaceWebServiceClient client,String sellerId,String reportId) throws MarketplaceWebServiceException{
		GetReportRequest request = new GetReportRequest();
		request.setMerchant(sellerId);
		request.setReportId(reportId);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		request.setReportOutputStream(baos);
		client.getReport(request);
		return baos.toByteArray();
	}
	
	/**
	 * 如果没有GeneratedReportId
	 * 
	 * @param client
	 * @param sellerId
	 * @param requestReportIds
	 * @return 
	 * @throws MarketplaceWebServiceException 
	 */
	public static GetReportListResult getReportList(MarketplaceWebServiceClient client,String sellerId,List<String> requestReportIds) throws MarketplaceWebServiceException{
		GetReportListRequest request = new GetReportListRequest();
		IdList idList = new IdList();
		idList.setId(requestReportIds);
		request.setReportRequestIdList(idList);
		request.setMerchant(sellerId);
		GetReportListResponse response = client.getReportList(request);
		return response.getGetReportListResult();
	}
}
