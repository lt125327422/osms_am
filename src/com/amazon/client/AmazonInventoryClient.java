package com.amazon.client;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonservices.mws.FulfillmentInventory._2010_10_01.FBAInventoryServiceMWSClient;
import com.amazonservices.mws.FulfillmentInventory._2010_10_01.FBAInventoryServiceMWSConfig;
import com.amazonservices.mws.FulfillmentInventory._2010_10_01.model.InventorySupplyList;
import com.amazonservices.mws.FulfillmentInventory._2010_10_01.model.ListInventorySupplyRequest;
import com.amazonservices.mws.FulfillmentInventory._2010_10_01.model.ListInventorySupplyResponse;
import com.amazonservices.mws.FulfillmentInventory._2010_10_01.model.ResponseHeaderMetadata;
import com.itecheasy.common.util.DateUtils;
import com.itecheasy.common.util.DeployProperties;
import com.itecheasy.core.amazon.AmazonConfigInfo;

/**
 * @author wanghw
 * @date 2016-12-12
 * @description TODO
 * @version 1.2.2
 */
public class AmazonInventoryClient {

	public static synchronized FBAInventoryServiceMWSClient getAsyncClient(AmazonConfigInfo api) {
		FBAInventoryServiceMWSConfig config = new FBAInventoryServiceMWSConfig();
		config.setServiceURL(api.getServiceURL());
		FBAInventoryServiceMWSClient client = new FBAInventoryServiceMWSClient(api.getAccessKeyId(), api.getSecretAccessKey(), 
				DeployProperties
				.getInstance().getAppName(), DeployProperties.getInstance().getAppVersion(), config);
		return client;
	}
	
	
	public static void main(String[] args) {
		FBAInventoryServiceMWSClient client=getAsyncClient(APIUtils.getUS());
		ListInventorySupplyRequest request = new ListInventorySupplyRequest();
		request.setMarketplaceId(APIUtils.getUS().getMarketplaceID());
		request.setSellerId(APIUtils.getUS().getSellerID());
		
		// 为您想知道库存供应情况的商品指定的卖家 SKU 列表。
//		SellerSkuList sellerSkus = new SellerSkuList();
//		List<String> skus=new ArrayList<String>();
//		skus.add("X-EW-0.8D-1-FBA");
//		skus.add("NJEW-JL178-FBA");
//		skus.add("BJEW-F166-01-FBA");
//		skus.add("IFIN-X0014-AB-FBA");
//		sellerSkus.setMember(skus);
//		request.setSellerSkus(sellerSkus);
//		此日期用于选择您在某个指定日期后（或当时）已更改库存供应情况的商品，日期格式为 ISO 8601。
		XMLGregorianCalendar queryStartDateTime = DateUtils.getXMLGregorianCalendar(new Date());
		request.setQueryStartDateTime(queryStartDateTime);
		// ResponseGroup 值：
		// Basic - 不包括响应中的 SupplyDetail 元素
		// Detailed - 在响应中包含 SupplyDetail 元素
		// 默认值：Basic
		// String responseGroup = "example";
		// request.setResponseGroup(responseGroup);
		ListInventorySupplyResponse response = client.listInventorySupply(request);
		ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
		InventorySupplyList  list=response.getListInventorySupplyResult().getInventorySupplyList();
		System.out.println(list.getMember().size());
	}
}
